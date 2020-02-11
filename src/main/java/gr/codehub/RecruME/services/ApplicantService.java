package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.models.*;
import gr.codehub.RecruME.repositories.ApplicantRepo;
import gr.codehub.RecruME.repositories.JobOfferRepo;
import gr.codehub.RecruME.repositories.MatchingRepo;
import gr.codehub.RecruME.repositories.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicantService {
    @Autowired
    private ApplicantRepo applicantRepo;
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private JobOfferRepo jobOfferRepo;
    @Autowired
    private MatchingRepo matchingRepo;

    /**
     * save an applicant to the database
     * checks for automatic matching using the method checkForAutomaticMatching
     * @param applicantDto
     * @return Applicant with given id
     */
    public Applicant save(ApplicantDto applicantDto){
        Applicant applicant = new Applicant();
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setEducationLevel(EducationLevel.getEnumFromString(applicantDto.getEducationLevel()));
        applicant.setSkillLevel(SkillLevel.getEnumFromString(applicantDto.getSkillLevel()));
        applicant.setDob(new Date(applicantDto.getYearBirth(), applicantDto.getMonthBirth()-1, applicantDto.getDayBirth()+1));

        // find the ids of the skills of the new applicant
        for (Skill s: applicantDto.getSkills()){
            Skill skillWithId = this.findSkillByName(s.getSkillName());
            applicant.getApplicantSkillSet().add(skillWithId);
        }
        Applicant applicantWithId = applicantRepo.save(applicant);
        // new applicant entered the system
        // checking for possible automatic matching
        checkForAutomaticMatching(applicantWithId);
        return applicantWithId;
    }

    /**
     * find a skill by name in the database
     * @param name
     * @return skill with the particular name
     */
    public Skill findSkillByName(String name){
        return StreamSupport
                .stream(skillRepo.findAll().spliterator(),false)
                .filter(skill -> skill.getSkillName().equals(name))
                .findFirst()
                .get();
    }

    /**
     * Checks for automatic matching by comparing the skillSet of all existing jobOffers
     * with a particular applicant. If automatic matching is found, then it is saved in the database.
     * @param applicant
     * @return true if such a matching is found, false if no matching exists
     */
    public boolean checkForAutomaticMatching(Applicant applicant){
        JobOffer jobOffer = StreamSupport
                .stream(jobOfferRepo.findAll().spliterator(), false)
                .filter(joboffer -> joboffer.getJobSkillSet().equals(applicant.getApplicantSkillSet()))
                .findFirst()
                .orElse(null);

        if (jobOffer == null) return false;

        // save automatic matching that was found
        Matching matching = new Matching();
        matching.setApplicant(applicant);
        matching.setJobOffer(jobOffer);
        matching.setMatchStatus(MatchStatus.AUTOMATIC);
        matching.setFinalizedMatching(FinalizedMatching.NO);
        matchingRepo.save(matching);
        return true;
    }

    public List<Applicant> getApplicantsByName(String lastname, String firstname) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(),false)
                .filter(applicant -> applicant.getFirstName().equals(firstname) && applicant.getLastName().equals(lastname))
                .collect(Collectors.toList());
    }

    public Optional<Applicant> getApplicantById(int id) {
        return applicantRepo.findById(id);
    }

    public List<Applicant> getApplicantsByRegion(String region) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(),false)
                .filter(applicant -> applicant.getRegion().equals(region))
                .collect(Collectors.toList());
    }


    public List<Applicant> getApplicantsByDate(int yearFrom, int yearTo) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(),false)
                .filter(applicant -> applicant.getDob().getYear()>=yearFrom)
                .filter(applicant -> applicant.getDob().getYear()<=yearTo).
                        collect(Collectors.toList());
    }

    public List<Applicant> getApplicantsBySkill(int skill_id) {
        List<Applicant> applicants = new ArrayList<>();
        for(Applicant a:applicantRepo.findAll()){
            for(Skill s:a.getApplicantSkillSet()){
                if(s.getId()==skill_id)
                    applicants.add(a);
            }
        }
        return applicants;
    }
}
