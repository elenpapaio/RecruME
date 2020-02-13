package gr.codehub.RecruME.repositories;

import gr.codehub.RecruME.models.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepo extends CrudRepository<Skill, Integer> {
    Skill findFirstBySkillName(String skillName);
}
