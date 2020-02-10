package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.SkillDto;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.models.SkillLevel;
import gr.codehub.RecruME.repositories.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class SkillService {

    @Autowired
    SkillRepo skillRepo;

    public Skill saveSkill(SkillDto skillDto) {
        Skill skill = findSkillByName(skillDto.getSkillName());
        if(skill!=null)
            return null;
        else{
            skillRepo.save(skill);
            return skill;
        }


    }


    public Skill findSkillByName(String name){
        return StreamSupport
                .stream(skillRepo.findAll().spliterator(),false)
                .filter(skill -> skill.getSkillName().equals(name))
                .findFirst()
                .get();
    }

    public Skill update(int id, SkillDto skillDto) {
        return skillRepo.findById(id).get();
    }

    public String deleteSkill(int id) {
        skillRepo.deleteById(id);
        return "deleted";
    }

//    public Skill update(int id, SkillDto skillDto) {
//       Optional<Skill> skill = new Skill();
//       skill = skillRepo.findById(id);
//        skill.setSkillName(skillDto.getSkillName());
//
//    }
}
