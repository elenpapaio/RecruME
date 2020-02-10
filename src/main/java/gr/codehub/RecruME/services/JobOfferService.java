package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.JobOfferDto;
import gr.codehub.RecruME.dtos.SkillDto;
import gr.codehub.RecruME.models.EducationLevel;
import gr.codehub.RecruME.models.JobOffer;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.models.SkillLevel;
import gr.codehub.RecruME.repositories.JobOfferRepo;
import gr.codehub.RecruME.repositories.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class JobOfferService {
    @Autowired
    private JobOfferRepo jobOfferRepo;
    @Autowired
    private SkillRepo skillRepo;

    public JobOffer save(JobOfferDto jobOfferDto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitleOfPosition(jobOfferDto.getTitleOfPosition());
        jobOffer.setEducationLevel(EducationLevel.getEnumFromString(jobOfferDto.getEducationLevel()));
        jobOffer.setRegion(jobOfferDto.getRegion());
        jobOffer.setPostDate(new Date(jobOfferDto.getPostYear(),jobOfferDto.getPostMonth()-1,jobOfferDto.getPostDay()+1));

        for(Skill s:jobOfferDto.getSkills()){
            Skill skillWithId = this.findSkillByNameAndLevel(s.getSkillName(), s.getSkillLevel());
                jobOffer.getJobSkillSet().add(skillWithId);
        }
        return jobOffer;
    }

    public Skill findSkillByNameAndLevel(String name, SkillLevel level){
        return StreamSupport
                .stream(skillRepo.findAll().spliterator(),false)
                .filter(skill -> skill.getSkillName().equals(name) && skill.getSkillLevel() == level)
                .findFirst()
                .get();
    }

    public List<JobOffer> getJobOffersByDate(int year,int month,int day){
            return StreamSupport
                    .stream(jobOfferRepo.findAll().spliterator(), false)
                    .filter(jobOffer -> jobOffer.getPostDate().getYear() == year && jobOffer.getPostDate().getMonth() == month && jobOffer.getPostDate().getDay() == day)
                    .collect(Collectors.toList());
    }

    public List<JobOffer> getJobOfferByRegion(String region) {
        return StreamSupport
                .stream(jobOfferRepo.findAll().spliterator(),false)
                .filter(jobOffer->jobOffer.getRegion().equals(region))
                .collect(Collectors.toList());
    }

    public List<JobOffer> getJobOffersByTitle(String title) {
        return StreamSupport
                .stream(jobOfferRepo.findAll().spliterator(),false)
                .filter(jobOffer->jobOffer.getTitleOfPosition().equals(title))
                .collect(Collectors.toList());
    }

    public List<JobOffer> getJobOfferBySkill(int skill_id) {
        Optional<Skill> skill = skillRepo.findById(skill_id);
        Set<JobOffer> jobOffers =skill.getJobOffers();

    }
}
