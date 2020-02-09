package gr.codehub.RecruME.controllers;

import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.models.JobOffer;
import gr.codehub.RecruME.services.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recrume/")
public class JobOfferController {
    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping("jobOffer/import")
    public List<JobOffer> loadJobOffers(){

    }

    @PostMapping("jobOffer")
    public JobOffer newJobOffer(@RequestBody JobOfferDto jobOfferDto){

    }

    @GetMapping("jobOffers/year/{year}/month/{month}/day/{day}")
    public List<JobOffer> getJobOffersByDate(@PathVariable String year,@PathVariable String month,@PathVariable String day){

    }

    @GetMapping("jobOffers/region/{region}")
    public List<JobOffer> getJobOffersByRegion(@PathVariable String region){

    }

    @GetMapping("jobOffers/title/{title}")
    public List<JobOffer> getJobOffersTitle(@PathVariable String title){

    }

    @GetMapping("jobOffers/report/week")
    public List<JobOffer> saveJobOffers(){

    }
}
