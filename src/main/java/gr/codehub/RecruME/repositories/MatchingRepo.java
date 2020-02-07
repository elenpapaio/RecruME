package gr.codehub.RecruME.repositories;

import gr.codehub.RecruME.models.Matching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepo extends CrudRepository<Matching, Integer> {
}
