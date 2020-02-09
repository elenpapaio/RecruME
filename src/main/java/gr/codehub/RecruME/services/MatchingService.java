package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.MatchingDto;
import gr.codehub.RecruME.exceptions.ApplicantNotFoundException;
import gr.codehub.RecruME.exceptions.JobOfferNotFoundException;
import gr.codehub.RecruME.exceptions.MatchingNotFoundException;
import gr.codehub.RecruME.models.*;
import gr.codehub.RecruME.repositories.ApplicantRepo;
import gr.codehub.RecruME.repositories.JobOfferRepo;
import gr.codehub.RecruME.repositories.MatchingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MatchingService {
    @Autowired
    MatchingRepo matchingRepo;
    @Autowired
    ApplicantRepo applicantRepo;
    @Autowired
    JobOfferRepo jobOfferRepo;

    public Matching save(MatchingDto matchingDto) throws ApplicantNotFoundException, JobOfferNotFoundException {
        Matching matching = new Matching();
        Applicant applicant = applicantRepo.findById(matchingDto.getApplicantId()).get();
        if (applicant == null) throw new ApplicantNotFoundException("Applicant id = "+matchingDto.getApplicantId());

        JobOffer jobOffer = jobOfferRepo.findById(matchingDto.getJobOfferId()).get();
        if (jobOffer == null) throw new JobOfferNotFoundException("JobOffer id = "+matchingDto.getJobOfferId());

        matching.setApplicant(applicant);
        matching.setJobOffer(jobOffer);
        matching.setMatchStatus(MatchStatus.MANUAL);
        matching.setFinalizedMatching(FinalizedMatching.NO);
        return matchingRepo.save(matching);
    }

    public Matching updateOneMatching(int id, MatchingDto matchingDto)
            throws MatchingNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException{
        Matching matching = matchingRepo.findById(id).get();
        if (matching == null) throw new MatchingNotFoundException("Matching id = "+id);
        Applicant applicant = applicantRepo.findById(matchingDto.getApplicantId()).get();
        if (applicant == null) throw new ApplicantNotFoundException("Applicant id = "+matchingDto.getApplicantId());

        JobOffer jobOffer = jobOfferRepo.findById(matchingDto.getJobOfferId()).get();
        if (jobOffer == null) throw new JobOfferNotFoundException("JobOffer id = "+matchingDto.getJobOfferId());

        matching.setApplicant(applicant);
        matching.setJobOffer(jobOffer);
        matching.setMatchStatus(MatchStatus.MANUAL);
        //matching.setFinalizedMatching(FinalizedMatching.getEnumFromString(matchingDto.getFinalizedMatching()));
        //matching.setFinalizedDate(matchingDto.getFinalizedDate());
        return matchingRepo.save(matching);
    }

    public Matching finalizeManualMatching(int id) throws MatchingNotFoundException {
        Matching matching = matchingRepo.findById(id).get();
        if (matching == null) throw new MatchingNotFoundException("Matching id = "+id);
        matching.setFinalizedMatching(FinalizedMatching.YES);
        matching.setFinalizedDate(new Date());
        return matchingRepo.save(matching);
    }

    public String deleteMatchingById(int id) throws MatchingNotFoundException {
        matchingRepo.deleteById(id);
        return "deleted";
    }

}
