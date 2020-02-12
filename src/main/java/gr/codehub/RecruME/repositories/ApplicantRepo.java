package gr.codehub.RecruME.repositories;

import gr.codehub.RecruME.models.Applicant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepo extends CrudRepository<Applicant, Integer> {
}
