package gr.codehub.RecruME.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Applicant applicant;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private JobOffer jobOffer;
    private MatchStatus matchStatus;
    private FinalizedMatching finalizedMatching;
    private Date finalizedDate;
}
