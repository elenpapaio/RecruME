package gr.codehub.RecruME.controllers;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import gr.codehub.RecruME.models.Matching;
import gr.codehub.RecruME.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    @GetMapping("matchings/manual")
    public List<Matching> getMatchingsManual(){

    }

    @GetMapping("matchings/automatic")
    public List<Matching> getMatchingsAutomatic(){

    }

    @DeleteMapping("matching/{id}")
    public String deleteMatchingById(@PathVariable int id){

    }

    @GetMapping("matchings/Finalized/mostRecent/20")
    public List<Matching> getMatchingsFinalized20(){

    }

    @GetMapping("matchings/notFinalized")
    public List<Matching> getMatchingsNotFinalized(){

    }
}
