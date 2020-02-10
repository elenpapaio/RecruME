package gr.codehub.RecruME.dtos;

import gr.codehub.RecruME.models.EducationLevel;
import gr.codehub.RecruME.models.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferDto {
    private String titleOfPosition;
    private String region;
    private String educationLevel;
    private int postYear;
    private int postMonth;
    private int postDay;
    private List<Skill> skills;

}
