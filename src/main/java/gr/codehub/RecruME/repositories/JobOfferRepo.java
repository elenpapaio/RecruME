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

}
