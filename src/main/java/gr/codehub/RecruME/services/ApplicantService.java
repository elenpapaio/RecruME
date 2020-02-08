package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.models.SkillLevel;
import gr.codehub.RecruME.repositories.ApplicantRepo;
import gr.codehub.RecruME.repositories.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        applicant.setEducationLevel(applicantDto.getEducationLevel());
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
}
