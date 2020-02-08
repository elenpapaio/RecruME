package gr.codehub.RecruME.controllers;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("recrume")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;

    @PostMapping("applicant")
    public Applicant newApplicant(@RequestBody ApplicantDto applicantDto) {
        return applicantService.save(applicantDto);
    }
}
