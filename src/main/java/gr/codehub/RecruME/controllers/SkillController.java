package gr.codehub.RecruME.controllers;

import gr.codehub.RecruME.dtos.SkillDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("recrume/")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping("skills/import")
    public Set<Skill> loadSkills() throws IOException {
        return skillService.loadSkills();
    }

    @PostMapping("skill")
    public Skill newSkill(@RequestBody SkillDto skillDto){
        return skillService.saveSkill(skillDto);
    }

    @PutMapping("skill/{id}")
    public  Skill updateSkill(@PathVariable int id, @RequestBody SkillDto skillDto){
        return skillService.updateSkill(id,skillDto);
    }

    @DeleteMapping("skill/{id}")
    public String deleteSkillById(@PathVariable int id){
        return skillService.deleteSkill(id);
    }

    @GetMapping("skills/mostRequested/20")
    public List<String> getMostRequestedSkills20(){
        return skillService.getMostRequestedSkills();
    }

    @GetMapping("skills/mostOffered/20")
    public List<String> getMostOfferedSkills20(){
        return skillService.getMostOfferedSkills();
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
