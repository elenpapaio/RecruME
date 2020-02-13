package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.SkillDto;
import gr.codehub.RecruME.exceptions.SkillNotFoundException;
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

    /**
     * creates a new skill in the database
     * @param skillDto
     * @return the created skill
     */
    public Skill saveSkill(SkillDto skillDto) {
        Skill skill = findSkillByName(skillDto.getSkillName());
        if(skill!=null)
            return null;
        else{
            skillRepo.save(skill);
            return skill;
        }
    }

    /**
     * edits an existing skill
     * @param id of the skill to be edited
     * @param skillDto
     * @return the edited skill
     * @throws SkillNotFoundException when the given skill does not exist
     */
    public Skill updateSkill(int id, SkillDto skillDto) throws SkillNotFoundException{
        Skill skill = skillRepo.findById(id).get();
        if (skill == null) throw new SkillNotFoundException("Skill id = "+id);
        skill.setSkillName(skillDto.getSkillName());
        return skillRepo.save(skill);
    }

    /**
     * deletes a specific skill
     * @param id of the skill to be deleted
     * @return a message informing that delete was successful
     * @throws SkillNotFoundException when the given skill does not exist
     */
    public String deleteSkill(int id) throws SkillNotFoundException {
        if (skillRepo.existsById(id)){
            skillRepo.deleteById(id);
            return "deleted";
        }
        throw new SkillNotFoundException("Skill id = "+id);
    }

    /**
     * loads and imports the skills from the excel file
     * @return a list of the imported skills
     * @throws IOException
     */
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

    /**
     * retrieves the most requested, from the job offers, skills
     * @return
     */
    public List<String> getMostRequestedSkills()
    {
        List<Object[]> skillList = skillRepo.getMostRequestedSkills();
        List<String> skills =  new ArrayList<>();
           for(Object[] o: skillList)
               skills.add((String)o[0]);
        return skills;
    }

    /**
     * retrieves a list of the most offered, from the applicants, skills
     * @return
     */
    public List<String> getMostOfferedSkills()
    {
        List<Object[]> skillList = skillRepo.getMostOfferedSkills();
        List<String> skills = new ArrayList<>();
        for(Object[] o: skillList)
            skills.add((String)o[0]);
        return skills;
    }

    /**
     *
     * @return
     */
    public List<String> getUnmatchedSkills() {
        List<Object[]> skillList = skillRepo.getUnmatchedSkills();
        List<String> skills = new ArrayList<>();
        for(Object[] o:skillList)
            skills.add((String)o[0]);
        return skills;
    }

    public List<String> getRequestedUnmatchedSkills() {
        List<Object[]> skillList = skillRepo.getRequestedUnmatchedSkills();
        List<String> skills = new ArrayList<>();
        for(Object[] o:skillList)
            skills.add((String)o[0]);
        return skills;
    }
}
