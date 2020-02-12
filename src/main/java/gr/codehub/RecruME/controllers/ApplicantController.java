package gr.codehub.RecruME.controllers;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("recrume/")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;

    @GetMapping("applicants/import")
    public List<Applicant> loadApplicants(){
        return null;
    }

    @GetMapping("applicants")
    public List<Applicant> getAllApplicants(){
        return applicantService.getAllApplicants();
    }

    /**
     * creates an applicant and save in the database
     * @param applicantDto
     * @return saved applicant
     */
    @PostMapping("applicant")
    public Applicant newApplicant(@RequestBody ApplicantDto applicantDto){
        return applicantService.save(applicantDto);
    }

    @GetMapping("applicant/lastname/{lastname}/firstname/{firstname}")
    public List<Applicant> getApplicantsByName(@PathVariable String lastname,@PathVariable String firstname){
        return applicantService.getApplicantsByName(lastname,firstname);
    }

    @GetMapping("applicant/{id}")
    public Optional<Applicant> getApplicantById(@PathVariable int id){
        return applicantService.getApplicantById(id);
    }

    @GetMapping("applicants/region/{region}")
    public List<Applicant> getApplicantsByRegion(@PathVariable String region){
        return applicantService.getApplicantsByRegion(region);
    }

    @GetMapping("applicants/DateFrom/{yearFrom}/DateTo/{yearTo}")
    public List<Applicant> getApplicantsByDate(@PathVariable int yearFrom, @PathVariable int yearTo){
        return applicantService.getApplicantsByDate(yearFrom,yearTo);
    }

    @GetMapping("applicants/skill/{skill_Id}")
    public List<Applicant> getApplicantsBySkill(@PathVariable int skill_id){
        return applicantService.getApplicantsBySkill(skill_id);
    }

    @GetMapping("applicants/report/week")
    public List<Applicant> saveApplicants(){
        return  null;
    }
}
