package gr.codehub.RecruME.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingDto {
    private int applicantId;
    private int jobOfferId;
    private String matchStatus;
    private String finalizedMatching;
    private Date finalizedDate;
}