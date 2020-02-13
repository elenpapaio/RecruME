package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.JobOfferDto;
import gr.codehub.RecruME.models.*;
import gr.codehub.RecruME.repositories.JobOfferRepo;
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
public class JobOfferService {
    @Autowired
    private JobOfferRepo jobOfferRepo;
    @Autowired
    private SkillRepo skillRepo;

    /**
     * saves a job offer to the database
     * @param jobOfferDto
     * @return jobOffer with given id
     */
    public JobOffer save(JobOfferDto jobOfferDto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitleOfPosition(jobOfferDto.getTitleOfPosition());
        jobOffer.setCompanyName(jobOfferDto.getCompanyName());
        jobOffer.setEducationLevel(EducationLevel.getEnumFromString(jobOfferDto.getEducationLevel().toUpperCase()));
        jobOffer.setSkillLevel(SkillLevel.getEnumFromString(jobOfferDto.getSkillLevel().toUpperCase()));
        jobOffer.setRegion(jobOfferDto.getRegion());
        jobOffer.setPostDate(new Date(jobOfferDto.getPostYear(), jobOfferDto.getPostMonth() - 1, jobOfferDto.getPostDay() + 1));

        for (Skill s : jobOfferDto.getSkills()) {
            Skill skillWithId = this.findSkillByName(s.getSkillName());
            jobOffer.getJobSkillSet().add(skillWithId);
        }
        return jobOfferRepo.save(jobOffer);
    }

    private Skill findSkillByName(String name) {
        Skill skill = skillRepo.findFirstBySkillName(name);
        if(skill == null) {
            skill= new Skill();
            skill.setSkillName(name);

            Skill skill2 = skillRepo.save(skill);
            return skill2;
        }
        return skill;
    }

    public List<JobOffer> loadJobOffers() throws IOException {
        File file = ResourceUtils.getFile("classpath:data for recrume.xlsx");
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(1);
        Iterator<Row> row = datatypeSheet.iterator();
        row.next();                                             //reads the headers
        List<JobOffer> jobOffers = new ArrayList<>();
        while (row.hasNext()) {
            JobOffer jobOffer = new JobOffer();
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            jobOffer.setCompanyName(cellIterator.next().getStringCellValue());
            jobOffer.setTitleOfPosition(cellIterator.next().getStringCellValue());
            jobOffer.setRegion(cellIterator.next().getStringCellValue());
            jobOffer.setSkillLevel(SkillLevel.getEnumFromString(cellIterator.next().getStringCellValue().toUpperCase()));
            Set<Skill> skillSet = new HashSet<>();
            while (cellIterator.hasNext()) {
                String skillName = cellIterator.next().getStringCellValue();
                Skill skill= this.findSkillByName(skillName);
                skillSet.add(skill);
            }
            jobOffer.setJobSkillSet(skillSet);
            jobOffers.add(jobOffer);
        }
        jobOfferRepo.saveAll(jobOffers);
        return jobOffers;
    }

    public List<JobOffer> getJobOffersByDate(int year, int month, int day) {
        return StreamSupport
                .stream(jobOfferRepo.findAll().spliterator(), false)
                .filter(jobOffer -> jobOffer.getPostDate().getYear() == year && jobOffer.getPostDate().getMonth() == month && jobOffer.getPostDate().getDay() == day)
                .collect(Collectors.toList());
    }

    public List<JobOffer> getJobOfferByRegion(String region) {
        return StreamSupport
                .stream(jobOfferRepo.findAll().spliterator(), false)
                .filter(jobOffer -> jobOffer.getRegion().equals(region))
                .collect(Collectors.toList());
    }

    public List<JobOffer> getJobOffersByTitle(String title) {
        return StreamSupport
                .stream(jobOfferRepo.findAll().spliterator(), false)
                .filter(jobOffer -> jobOffer.getTitleOfPosition().equals(title))
                .collect(Collectors.toList());
    }

    public List<JobOffer> getJobOffersBySkill(int skill_id) {
        List<JobOffer> jobOffers = new ArrayList<>();
        for (JobOffer j : jobOfferRepo.findAll()) {
            for (Skill s : j.getJobSkillSet()) {
                if (s.getId() == skill_id)
                    jobOffers.add(j);
            }
        }
        return jobOffers;
    }


}



