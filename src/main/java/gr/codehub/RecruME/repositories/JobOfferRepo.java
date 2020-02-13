package gr.codehub.RecruME.repositories;

import gr.codehub.RecruME.models.JobOffer;
import gr.codehub.RecruME.models.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepo extends CrudRepository<JobOffer, Integer> {
//    @Query(value="SELECT skill_id FROM job_skill",nativeQuery = true);
//    List<Integer> getMostRequestedSkills();

//    @Query(value="SELECT TOP 5 skill_id," +
//            "COUNT (skill_id) AS 'most_requested' FROM job_skill GROUP BY (skill_id) ORDER BY 'most_requested' DESC")
//    List<Skill> getMostRequestedSkills();


}
