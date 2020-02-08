package gr.codehub.RecruME.dtos;

import gr.codehub.RecruME.models.EducationLevel;
import gr.codehub.RecruME.models.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class ApplicantDto {
    private String firstName;
    private String lastName;
    private String address;
    private String region;
    private EducationLevel educationLevel;
    private int yearBirth;
    private int monthBirth;
    private int dayBirth;
    private List<Skill> skills;

    public ApplicantDto(){
        skills = new ArrayList<>();
    }

}
