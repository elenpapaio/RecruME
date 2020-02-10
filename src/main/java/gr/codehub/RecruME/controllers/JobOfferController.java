package gr.codehub.RecruME.controllers;

import gr.codehub.RecruME.dtos.JobOfferDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.models.JobOffer;
import gr.codehub.RecruME.services.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("recrume/")
public class JobOfferController {
    @Autowired
    private JobOfferService jobOfferService;

    @GetMapping("jobOffer/import")
    public List<JobOffer> loadJobOffers(){
        return null;
    }

    @PostMapping("jobOffer")
    public JobOffer newJobOffer(@RequestBody JobOfferDto jobOfferDto){
        return jobOfferService.save(jobOfferDto);

    }

    @GetMapping("jobOffers/year/{year}/month/{month}/day/{day}")
    public List<JobOffer> getJobOffersByDate(@PathVariable int year,@PathVariable int month,@PathVariable int day){
        return jobOfferService.getJobOffersByDate(year, month, day);
    }

    @GetMapping("jobOffers/region/{region}")
    public List<JobOffer> getJobOffersByRegion(@PathVariable String region){
        return jobOfferService.getJobOfferByRegion(region);
    }

    @GetMapping("jobOffers/title/{title}")
    public List<JobOffer> getJobOffersTitle(@PathVariable String title){
        return jobOfferService.getJobOffersByTitle(title);
    }

    @GetMapping("jobOffers/skill/{skill_id}")
    public List<JobOffer> getJobOffersBySkill(@PathVariable int skill_id){
        return jobOfferService.getJobOffersBySkill(skill_id);
    }

    @GetMapping("jobOffers/report/week")
    public List<JobOffer> saveJobOffers(){
        return null;
    }
}
