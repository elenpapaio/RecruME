package gr.codehub.RecruME.controllers;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import gr.codehub.RecruME.dtos.MatchingDto;
import gr.codehub.RecruME.exceptions.ApplicantNotFoundException;
import gr.codehub.RecruME.exceptions.JobOfferNotFoundException;
import gr.codehub.RecruME.exceptions.MatchingNotFoundException;
import gr.codehub.RecruME.models.Matching;
import gr.codehub.RecruME.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    @PostMapping("matching")
    public Matching newMatching(@RequestBody MatchingDto matchingDto) throws ApplicantNotFoundException, JobOfferNotFoundException {
        return matchingService.save(matchingDto);
    }

    @PutMapping("matching/{id}")
    public Matching updateOne(@PathVariable int id,@RequestBody MatchingDto matchingDto)
            throws JobOfferNotFoundException, ApplicantNotFoundException, MatchingNotFoundException {
        return matchingService.updateOneMatching(id, matchingDto);
    }

    @PutMapping("matching/finalize/{id}")
    public Matching finalizeOne(@PathVariable int id) throws MatchingNotFoundException {
        return matchingService.finalizeMatching(id);
    }

    @DeleteMapping("matching/{id}")
    public String deleteMatchingById(@PathVariable int id) throws MatchingNotFoundException {
        return matchingService.deleteMatchingById(id);
    }

    @GetMapping("matchings/manual")
    public List<Matching> getMatchingsManual(){
        return null;
    }

    @GetMapping("matchings/automatic")
    public List<Matching> getMatchingsAutomatic(){
        return null;
    }

    @GetMapping("matchings/Finalized/mostRecent/20")
    public List<Matching> getMatchingsFinalized20(){
        return null;
    }

    @GetMapping("matchings/notFinalized")
    public List<Matching> getMatchingsNotFinalized(){
        return null;
    }
}
