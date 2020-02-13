package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.models.*;
import gr.codehub.RecruME.repositories.ApplicantRepo;
import gr.codehub.RecruME.repositories.JobOfferRepo;
import gr.codehub.RecruME.repositories.MatchingRepo;
import gr.codehub.RecruME.repositories.SkillRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicantService {
    @Autowired
    private ApplicantRepo applicantRepo;
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private JobOfferRepo jobOfferRepo;
    @Autowired
    private MatchingRepo matchingRepo;

    /**
     * saves an applicant to the database
     * checks for automatic matching using the method checkForAutomaticMatching
     * @param applicantDto
     * @return Applicant with given id
     */
    public Applicant save(ApplicantDto applicantDto) {
        Applicant applicant = new Applicant();
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setEducationLevel(EducationLevel.getEnumFromString(applicantDto.getEducationLevel()));
        applicant.setSkillLevel(SkillLevel.getEnumFromString(applicantDto.getSkillLevel()));
        applicant.setDob(new Date(applicantDto.getYearBirth(), applicantDto.getMonthBirth() - 1, applicantDto.getDayBirth() + 1));
        // find the ids of the skills of the new applicant
        for (Skill s : applicantDto.getSkills()) {
            Skill skillWithId = this.findSkillByName(s.getSkillName());
            applicant.getApplicantSkillSet().add(skillWithId);
        }
        Applicant applicantWithId = applicantRepo.save(applicant);          // new applicant entered the system
        checkForAutomaticMatching(applicantWithId);             // checking for possible automatic matching
        return applicantWithId;
    }

    /**
     * retrieves all applicants
     * @return a list of all applicants
     */
    public List<Applicant> getAllApplicants(){
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    /**
     * Checks for automatic matching by comparing the skillSet of all existing jobOffers
     * with a particular applicant. If automatic matching is found, then it is saved in the database.
     * @param applicant
     * @return true if such a matching is found, false if no matching exists
     */
    public boolean checkForAutomaticMatching(Applicant applicant){
        JobOffer jobOffer = StreamSupport
                .stream(jobOfferRepo.findAll().spliterator(), false)
                .filter(joboffer -> joboffer.getJobSkillSet().equals(applicant.getApplicantSkillSet()))
                .findFirst()
                .orElse(null);

        if (jobOffer == null) return false;

        // save automatic matching that was found
        Matching matching = new Matching();
        matching.setApplicant(applicant);
        matching.setJobOffer(jobOffer);
        matching.setMatchStatus(MatchStatus.AUTOMATIC);
        matching.setFinalizedMatching(FinalizedMatching.NO);
        matchingRepo.save(matching);
        return true;
    }

    /**
     * finds applicants by a particular first name and last name
     * @param lastname
     * @param firstname
     * @return the list of the applicants with the given first and last name
     */
    public List<Applicant> getApplicantsByName(String lastname, String firstname) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(), false)
                .filter(applicant -> applicant.getFirstName().equals(firstname) && applicant.getLastName().equals(lastname))
                .collect(Collectors.toList());
    }

    public Optional<Applicant> getApplicantById(int id) {
        return applicantRepo.findById(id);
    }

    /**
     * finds a list of applicants with a particular region
     * @param region
     * @return the list of such applicants
     */
    public List<Applicant> getApplicantsByRegion(String region) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(), false)
                .filter(applicant -> applicant.getRegion().equals(region))
                .collect(Collectors.toList());
    }

    /**
     * finds a list of applicants within a particular age range
     * @param yearFrom
     * @param yearTo
     * @return the list of these applicants
     */
    public List<Applicant> getApplicantsByDate(int yearFrom, int yearTo) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(), false)
                .filter(applicant -> applicant.getDob().getYear() >= yearFrom)
                .filter(applicant -> applicant.getDob().getYear() <= yearTo).
                        collect(Collectors.toList());
    }

    /**
     *
     * @param skill_id
     * @return a list of the applicants with a particular skill
     */
    public List<Applicant> getApplicantsBySkill(int skill_id) {
        List<Applicant> applicants = new ArrayList<>();
        for (Applicant a : applicantRepo.findAll()) {
            for (Skill s : a.getApplicantSkillSet()) {
                if (s.getId() == skill_id)
                    applicants.add(a);
            }
        }
        return applicants;
    }

    /**
     * loads and imports the applicants from the excel file
     * @return
     * @throws IOException
     */
    public List<Applicant> loadApplicants() throws IOException {
        File file = ResourceUtils.getFile("classpath:data for recrume.xlsx");
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> row = datatypeSheet.iterator();
        row.next();                                             //reads the headers
        List<Applicant> applicants = new ArrayList<>();
        while (row.hasNext()) {
            Applicant applicant = new Applicant();
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            applicant.setFirstName(cellIterator.next().getStringCellValue());
            applicant.setLastName(cellIterator.next().getStringCellValue());
            applicant.setAddress(cellIterator.next().getStringCellValue());
            applicant.setRegion(cellIterator.next().getStringCellValue());
            applicant.setEducationLevel(EducationLevel.getEnumFromString(cellIterator.next().getStringCellValue().toUpperCase()));
            applicant.setSkillLevel(SkillLevel.getEnumFromString(cellIterator.next().getStringCellValue().toUpperCase()));
            Set<Skill> skillSet = new HashSet<>();
            while (cellIterator.hasNext()) {
                String skillName = cellIterator.next().getStringCellValue();
                Skill skill= this.findSkillByName(skillName);
                skillSet.add(skill);
            }
            applicant.setApplicantSkillSet(skillSet);
            applicants.add(applicant);
        }

        List<Applicant> applicantsWithId =
                StreamSupport
                .stream(applicantRepo.saveAll(applicants).spliterator(), false)
                .collect(Collectors.toList());

        for( Applicant applicant: applicantsWithId){
            checkForAutomaticMatching(applicant);
        }
        return applicants;
    }

    public Skill findSkillByName(String name) {
        Skill skill = skillRepo.findFirstBySkillName(name);
        if(skill == null) {
            skill= new Skill();
            skill.setSkillName(name);

           Skill skill2 = skillRepo.save(skill);
           return skill2;
        }
        return skill;
    }

}