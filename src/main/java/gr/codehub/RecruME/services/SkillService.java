package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.SkillDto;
import gr.codehub.RecruME.models.Skill;
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

@Service
public class SkillService {

    @Autowired
    private SkillRepo skillRepo;

    public Skill saveSkill(SkillDto skillDto) {
        Skill skill = findSkillByName(skillDto.getSkillName());
        if(skill!=null)
            return null;
        else{
            skillRepo.save(skill);
            return skill;
        }

    }

    public Skill updateSkill(int id, SkillDto skillDto) {
        Skill skill = skillRepo.findById(id).get();
        skill.setSkillName(skillDto.getSkillName());
        return skillRepo.save(skill);
    }

    public String deleteSkill(int id) {
        skillRepo.deleteById(id);
        return "deleted";
    }

    public Set<Skill> loadSkills() throws IOException {
        File file = ResourceUtils.getFile("classpath:data for recrume.xlsx");
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(2);
        Iterator<Row> row = datatypeSheet.iterator();
        Set<Skill> skillSet = new HashSet<>();
        row.next();
        while(row.hasNext()){
            Row currentRow = row.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            Cell nameCell = cellIterator.next();
            Skill skill = this.findSkillByName(nameCell.getStringCellValue());
            skillSet.add(skill);
        }
          return skillSet;

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
