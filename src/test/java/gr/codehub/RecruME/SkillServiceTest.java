package gr.codehub.RecruME;

import gr.codehub.RecruME.exceptions.SkillNotFoundException;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.repositories.SkillRepo;
import gr.codehub.RecruME.services.SkillService;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SkillServiceTest {
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private SkillService skillService;

    private Skill skillWithId;

    @BeforeEach
    void setUp(){
        Skill skill = new Skill();
        skill.setSkillName("java");
        skillWithId = skillRepo.save(skill);
    }

    @Test
    void deleteSkill(){
        try {
            skillService.deleteSkill(skillWithId.getId());
            assertEquals(0, Lists.newArrayList(skillRepo.findAll()).size(),"Error while deleting skill");

        } catch (SkillNotFoundException e) {
            e.printStackTrace();
        }
    }
}
