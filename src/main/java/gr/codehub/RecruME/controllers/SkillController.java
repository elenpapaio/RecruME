package gr.codehub.RecruME.controllers;

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

    }

    @PostMapping("skill")
    public Skill newSkill(@RequestBody SkillDto skillDto){

    }

    @PutMapping("skill/{id}")
    public  Skill updateSkill(@PathVariable int id, @RequestBody SkillDto skillDto){

    }

    @DeleteMapping("skill/{id}")
    public String deleteSkillById(@PathVariable int id){

    }

    @GetMapping("skills/mostRequired/20")
    public List<Skill> getMostRequiredSkills20(){

    }

    @GetMapping("skills/mostOffered/20")
    public List<Skill> getMostOfferedSkills20(){

    }

    @GetMapping("skills/requested/unmatched")
    public List<Skill> getUnmatchedSkills(){

    }

    @GetMapping("skills/report/week")
    public List<Skill> saveSkills(){

    }
}
