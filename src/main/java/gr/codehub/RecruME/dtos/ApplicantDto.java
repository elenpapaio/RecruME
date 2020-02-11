package gr.codehub.RecruME.dtos;

import gr.codehub.RecruME.models.EducationLevel;
import gr.codehub.RecruME.models.Skill;
import gr.codehub.RecruME.models.SkillLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class ApplicantDto {
    private String firstName;
    private String lastName;
    private String address;
    private String region;
    private String educationLevel;
    private int yearBirth;
    private int monthBirth;
    private int dayBirth;
    private String skillLevel;
    private List<Skill> skills;

    public ApplicantDto(){
        skills = new ArrayList<>();
    }

}
