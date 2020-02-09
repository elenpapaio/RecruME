package gr.codehub.RecruME.controllers;

import gr.codehub.RecruME.dtos.SkillDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recrume/")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping("skills/import")
    public List<Skill> loadSkills(){
        return null;
    }

    @PostMapping("skill")
    public Skill newSkill(@RequestBody SkillDto skillDto){
        return null;
    }

    @PutMapping("skill/{id}")
    public  Skill updateSkill(@PathVariable int id, @RequestBody SkillDto skillDto){
        return null;
    }

    @DeleteMapping("skill/{id}")
    public String deleteSkillById(@PathVariable int id){
        return null;
    }

    @GetMapping("skills/mostRequired/20")
    public List<Skill> getMostRequiredSkills20(){
        return null;
    }

    @GetMapping("skills/mostOffered/20")
    public List<Skill> getMostOfferedSkills20(){
        return null;
    }

    @GetMapping("skills/requested/unmatched")
    public List<Skill> getUnmatchedSkills(){
        return null;
    }

    @GetMapping("skills/report/week")
    public List<Skill> saveSkills(){
        return null;
    }
}
