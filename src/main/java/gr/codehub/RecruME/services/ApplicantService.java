package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.models.EducationLevel;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.models.SkillLevel;
import gr.codehub.RecruME.repositories.ApplicantRepo;
import gr.codehub.RecruME.repositories.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Applicant save(ApplicantDto applicantDto){
        Applicant applicant = new Applicant();
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setEducationLevel(EducationLevel.getEnumFromString(applicantDto.getEducationLevel()));
        applicant.setDob(new Date(applicantDto.getYearBirth(), applicantDto.getMonthBirth()-1, applicantDto.getDayBirth()+1));

        for (Skill s: applicantDto.getSkills()){
            Skill skillWithId = this.findSkillByNameAndLevel(s.getSkillName(), s.getSkillLevel());
            applicant.getApplicantSkillSet().add(skillWithId);
        }
        return applicantRepo.save(applicant);
    }

    public Skill findSkillByNameAndLevel(String name, SkillLevel level){
        return StreamSupport
                .stream(skillRepo.findAll().spliterator(),false)
                .filter(skill -> skill.getSkillName().equals(name) && skill.getSkillLevel() == level)
                .findFirst()
                .get();
    }

    public List<Applicant> getApplicantsByName(String lastname, String firstname) {

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

    public List<Applicant> getApplicantsBySkill(int skill_id) {
        StreamSupport
                .stream(skillRepo.findAll().spliterator(),false)
                .filter()
    }

    public List<Applicant> getApplicantsByDate(int yearFrom, int yearTo) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(),false)
                .filter(applicant -> applicant.getDob().getYear()>=yearFrom)
                .filter(applicant -> applicant.getDob().getYear()<=yearTo).
                        collect(Collectors.toList());
    }
}
