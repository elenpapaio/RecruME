package gr.codehub.RecruME.repositories;

import gr.codehub.RecruME.models.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepo extends CrudRepository<Skill, Integer> {
    Skill findFirstBySkillName(String skillName);

    @Query(value="SELECT TOP 20 skill_name, count(skill_name) cnt\n" +
            "  FROM job_skill inner join skill\n" +
            "  on skill.id = job_skill.skill_id\n" +
            "  group by skill_name \n" +
            "  order by count(skill_name) desc", nativeQuery= true)
    List<Object[]> getMostRequestedSkills();

    @Query(value="SELECT TOP 20 skill_name, count(skill_name) cnt\n" +
            "  FROM applicant_skill inner join skill\n" +
            "  on skill.id = applicant_skill.skill_id\n" +
            "  group by skill_name \n" +
            "  order by count(skill_name) desc", nativeQuery= true)
    List<Object[]> getMostOfferedSkills();

    @Query(value="select skill_name\n"+
            "from skill left outer join  applicant_skill\n" +
            "on applicant_skill.skill_id= skill.id\n" +
            "where applicant_id is null",nativeQuery = true)
    List<Object[]> getUnmatchedSkills();

    @Query(value="select skill_name\n" +
            "from job_skill\n" +
            "inner join skill on\n" +
            "job_skill.skill_id= skill.id\n" +
            "left outer join  applicant_skill\n" +
            "on job_skill.skill_id= applicant_skill. skill_id\n" +
            "where applicant_id is null",nativeQuery = true)
    List<Object[]> getRequestedUnmatchedSkills();
}
