package gr.codehub.RecruME.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String address;
    private String region;
    private EducationLevel educationLevel;
    private Date dob;
    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "applicant_skill",
            joinColumns = {
                    @JoinColumn(name = "applicant_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "skill_id")})
    private Set<Skill> applicantSkillSet = new HashSet<Skill>();
}
