package gr.codehub.RecruME.repositories;

import gr.codehub.RecruME.models.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepo extends CrudRepository<Skill, Integer> {
    Skill findFirstBySkillName(String skillName);

    @Query(value="SELECT  skill_name, count(skill_name) cnt\n" +
            "  FROM job_skill inner join skill\n" +
            "  on skill.id = job_skill.skill_id\n" +
            "  group by skill_name \n" +
            "  order by count(skill_name) desc", nativeQuery= true )
    List<Object[]> getMostRequestedSkills();
}
