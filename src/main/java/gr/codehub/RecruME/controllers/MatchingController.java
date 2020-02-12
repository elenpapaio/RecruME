package gr.codehub.RecruME.controllers;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import gr.codehub.RecruME.dtos.MatchingDto;
import gr.codehub.RecruME.exceptions.ApplicantNotFoundException;
import gr.codehub.RecruME.exceptions.JobOfferNotFoundException;
import gr.codehub.RecruME.exceptions.MatchingNotFoundException;
import gr.codehub.RecruME.models.MatchStatus;
import gr.codehub.RecruME.models.Matching;
import gr.codehub.RecruME.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recrume/")
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    /**
     * creates a manual matching
     * @param matchingDto
     * @return saved manual matching
     * @throws ApplicantNotFoundException when the given applicant does not exist
     * @throws JobOfferNotFoundException when the given job offer does not exist
     */
    @PostMapping("matching")
    public Matching newMatching(@RequestBody MatchingDto matchingDto) throws ApplicantNotFoundException, JobOfferNotFoundException {
        return matchingService.save(matchingDto);
    }

    /**
     * edits an existing manual matching
     * @param id of the manual matching to be edited
     * @param matchingDto
     * @return the edited manual matching
     * @throws JobOfferNotFoundException when the given job offer does not exist
     * @throws ApplicantNotFoundException when the given applicant does not exist
     * @throws MatchingNotFoundException when the given manual matching does not exist
     */
    @PutMapping("matching/{id}")
    public Matching updateOne(@PathVariable int id,@RequestBody MatchingDto matchingDto)
            throws JobOfferNotFoundException, ApplicantNotFoundException, MatchingNotFoundException {
        return matchingService.updateOneMatching(id, matchingDto);
    }

    /**
     * puts matching in finalized state
     * @param id of the matching to be finalized
     * @return the finalized matching
     * @throws MatchingNotFoundException when the given manual matching does not exist
     */
    @PutMapping("matching/finalize/{id}")
    public Matching finalizeOne(@PathVariable int id) throws MatchingNotFoundException {
        return matchingService.finalizeMatching(id);
    }

    /**
     * deletes a specific matching
     * @param id of the matching to be deleted
     * @return message informing that delete was successful
     * @throws MatchingNotFoundException when the given manual matching does not exist
     */
    @DeleteMapping("matching/{id}")
    public String deleteMatchingById(@PathVariable int id) throws MatchingNotFoundException {
        return matchingService.deleteMatchingById(id);
    }

    /**
     * finds a particular matching by match status (MANUAL, AUTOMATIC)
     * @return a list of manual matchings with match status MANUAL
     */
    @GetMapping("matchings/manual")
    public List<Matching> getMatchingsManual(){
        return matchingService.getMatchingByMatchingStatus(MatchStatus.MANUAL);
    }

    /**
     * finds a particular matching by match status (MANUAL, AUTOMATIC)
     * @return a list of matchings with match status AUTOMATIC
     */
    @GetMapping("matchings/automatic")
    public List<Matching> getMatchingsAutomatic(){
        return matchingService.getMatchingByMatchingStatus(MatchStatus.AUTOMATIC);
    }

    /**
     * retrieves all the proposed matchings
     * @return a list of the proposed matchings (automatic, manual) - not finalized matchings
     */
    @GetMapping("matchings/notFinalized")
    public List<Matching> getMatchingsNotFinalized(){
        return matchingService.getMatchingsNotFinalized();
    }

    /**
     * retrieves the 20 most recent finalized matchings
     * @return a list of such matchings
     */
    @GetMapping("matchings/Finalized/mostRecent/20")
    public List<Matching> getMatchingsFinalized(){
        return matchingService.getMostRecentMatchingsFinalized(20);
    }

}
