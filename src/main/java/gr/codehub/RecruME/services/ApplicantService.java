package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.ApplicantDto;
import gr.codehub.RecruME.exceptions.SkillNotFoundException;
import gr.codehub.RecruME.models.Applicant;
import gr.codehub.RecruME.models.EducationLevel;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.models.SkillLevel;
import gr.codehub.RecruME.repositories.ApplicantRepo;
import gr.codehub.RecruME.repositories.SkillRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;

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


    /**
     * save an applicant to the database
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

        for (Skill s : applicantDto.getSkills()) {
            Skill skillWithId = this.findSkillByName(s.getSkillName());
            applicant.getApplicantSkillSet().add(skillWithId);
        }
        return applicantRepo.save(applicant);
    }


    public List<Applicant> getAllApplicants(){
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    public List<Applicant> getApplicantsByName(String lastname, String firstname) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(), false)
                .filter(applicant -> applicant.getFirstName().equals(firstname) && applicant.getLastName().equals(lastname))
                .collect(Collectors.toList());
    }

    public Optional<Applicant> getApplicantById(int id) {
        return applicantRepo.findById(id);
    }

    public List<Applicant> getApplicantsByRegion(String region) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(), false)
                .filter(applicant -> applicant.getRegion().equals(region))
                .collect(Collectors.toList());
    }

    public List<Applicant> getApplicantsByDate(int yearFrom, int yearTo) {
        return StreamSupport
                .stream(applicantRepo.findAll().spliterator(), false)
                .filter(applicant -> applicant.getDob().getYear() >= yearFrom)
                .filter(applicant -> applicant.getDob().getYear() <= yearTo).
                        collect(Collectors.toList());
    }

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
        applicantRepo.saveAll(applicants);
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