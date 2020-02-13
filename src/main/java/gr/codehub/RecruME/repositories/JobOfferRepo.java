package gr.codehub.RecruME.repositories;

import gr.codehub.RecruME.models.JobOffer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepo extends CrudRepository<JobOffer, Integer> {
}
