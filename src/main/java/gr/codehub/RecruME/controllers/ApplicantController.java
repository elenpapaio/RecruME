package gr.codehub.RecruME.controllers;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recrume/")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;

    @GetMapping("applicants/import")
    public List<Applicant> loadApplicants(){
        return null;
    }

    @PostMapping("applicant")
    public Applicant newApplicant(@RequestBody ApplicantDto applicantDto){
        return applicantService.save(applicantDto);
    }

    @GetMapping("applicant/lastname/{lastname}/firstname/{firstname}")
    public List<Applicant> getApplicantsByName(@PathVariable String lastname,@PathVariable String firstname){
        return null;
    }
    @GetMapping("applicant/{id}")
    public Applicant getApplicantById(@PathVariable int id){
        return null;
    }
    @GetMapping("applicants/region/{region}")
    public List<Applicant> getApplicantsByRegion(@PathVariable String region){
        return null;
    }

    @GetMapping("applicants/DateFrom/{yearFrom}/DateTo/{yearTo}")
    public List<Applicant> getApplicantsByDate(@PathVariable int yearFrom, @PathVariable int YearTo){
        return null;
    }

    @GetMapping("applicants/skill/{skill_Id}")
    public List<Applicant> getApplicantsBySkill(@PathVariable int skill_id){
        return null;
    }

    @GetMapping("applicants/report/week")
    public List<Applicant> saveApplicants(){
        return null;
    }
}
