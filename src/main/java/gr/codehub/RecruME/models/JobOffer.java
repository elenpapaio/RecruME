package gr.codehub.RecruME.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titleOfPosition;
    private String region;
    private EducationLevel educationLevel;
    private Date postDate;
    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "job_skill",
            joinColumns = {
                    @JoinColumn(name = "job_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "skill_id")})
    private Set<Skill> jobSkillSet = new HashSet<Skill>();
}
