package gr.codehub.RecruME.services;

import gr.codehub.RecruME.dtos.MatchingDto;
import gr.codehub.RecruME.exceptions.ApplicantNotFoundException;
import gr.codehub.RecruME.exceptions.JobOfferNotFoundException;
import gr.codehub.RecruME.exceptions.MatchingNotFoundException;
import gr.codehub.RecruME.models.*;
import gr.codehub.RecruME.repositories.ApplicantRepo;
import gr.codehub.RecruME.repositories.JobOfferRepo;
import gr.codehub.RecruME.repositories.MatchingRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MatchingService {
    @Autowired
    MatchingRepo matchingRepo;
    @Autowired
    ApplicantRepo applicantRepo;
    @Autowired
    JobOfferRepo jobOfferRepo;

    /**
     * create a new manual matching in the database
     * @param matchingDto
     * @return the created manual matching
     * @throws ApplicantNotFoundException when the given applicant does not exist
     * @throws JobOfferNotFoundException when the given job offer does not exist
     */
    public Matching save(MatchingDto matchingDto) throws ApplicantNotFoundException, JobOfferNotFoundException {
        Matching matching = new Matching();
        Applicant applicant = applicantRepo.findById(matchingDto.getApplicantId()).get();
        if (applicant == null) throw new ApplicantNotFoundException("Applicant id = "+matchingDto.getApplicantId());

        JobOffer jobOffer = jobOfferRepo.findById(matchingDto.getJobOfferId()).get();
        if (jobOffer == null) throw new JobOfferNotFoundException("JobOffer id = "+matchingDto.getJobOfferId());

        matching.setApplicant(applicant);
        matching.setJobOffer(jobOffer);
        matching.setMatchStatus(MatchStatus.getEnumFromString(matchingDto.getMatchStatus()));
        matching.setFinalizedMatching(FinalizedMatching.NO);
        return matchingRepo.save(matching);
    }

    /**
     * edits an existing manual matching
     * @param id of the manual matching to be edited
.     * @param matchingDto
     * @return the edited manual matching
     * @throws MatchingNotFoundException when the given manual matching does not exist
     * @throws ApplicantNotFoundException when the given applicant does not exist
     * @throws JobOfferNotFoundException when the given job offer does not exist
     */
    public Matching updateOneMatching(int id, MatchingDto matchingDto)
            throws MatchingNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException{
        Matching matching = matchingRepo.findById(id).get();
        if (matching == null) throw new MatchingNotFoundException("Matching id = "+id);
        Applicant applicant = applicantRepo.findById(matchingDto.getApplicantId()).get();
        if (applicant == null) throw new ApplicantNotFoundException("Applicant id = "+matchingDto.getApplicantId());

        JobOffer jobOffer = jobOfferRepo.findById(matchingDto.getJobOfferId()).get();
        if (jobOffer == null) throw new JobOfferNotFoundException("JobOffer id = "+matchingDto.getJobOfferId());

        matching.setApplicant(applicant);
        matching.setJobOffer(jobOffer);
        matching.setMatchStatus(MatchStatus.MANUAL);
        //matching.setFinalizedMatching(FinalizedMatching.getEnumFromString(matchingDto.getFinalizedMatching()));
        //matching.setFinalizedDate(matchingDto.getFinalizedDate());
        return matchingRepo.save(matching);
    }

    /**
     * puts matching in finalized state
     * @param id of the matching to be finalized
     * @return the finalized matching
     * @throws MatchingNotFoundException when the given manual matching does not exist
     */
    public Matching finalizeMatching(int id) throws MatchingNotFoundException {
        Matching matching = matchingRepo.findById(id).get();
        if (matching == null) throw new MatchingNotFoundException("Matching id = "+id);
        matching.setFinalizedMatching(FinalizedMatching.YES);
        matching.setFinalizedDate(new Date());
        return matchingRepo.save(matching);
    }

    /**
     * deletes a specific matching
     * @param id of the matching to be deleted
     * @return message informing that delete was successful
     * @throws MatchingNotFoundException when the given manual matching does not exist
     */
    public String deleteMatchingById(int id) throws MatchingNotFoundException {
        if (matchingRepo.existsById(id)) {
            matchingRepo.deleteById(id);
            return "deleted";
        }
        throw new MatchingNotFoundException("Matching id = "+id);
    }

    /**
     * finds a particular matching by match status (MANUAL, AUTOMATIC)
     * @param status of the matching to be found
     * @return a list of matchings with the particular status
     */
    public List<Matching> getMatchingByMatchingStatus(MatchStatus status) {
        return StreamSupport
                .stream(matchingRepo.findAll().spliterator(), false)
                .filter(matching -> matching.getMatchStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * retrieves all the proposed matchings
     * @return a list of the proposed matchings (automatic, manual) - not finalized matchings
     */
    public List<Matching> getMatchingsNotFinalized(){
        return StreamSupport
                .stream(matchingRepo.findAll().spliterator(), false)
                .filter(matching -> matching.getFinalizedMatching()==FinalizedMatching.NO)
                .collect(Collectors.toList());
    }

    /**
     * retrieves the most recent finalized matchings
     * @param limit - sets a limit to the number of matchings to be retrieved
     * @return a list of such matchings
     */
    public List<Matching> getMostRecentMatchingsFinalized(int limit){
        return StreamSupport
                .stream(matchingRepo.findAll().spliterator(), false)
                .filter(matching -> matching.getFinalizedMatching()==FinalizedMatching.YES)
                .sorted((m1,m2)-> m2.getFinalizedDate().compareTo(m1.getFinalizedDate()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * retrieves all the finalized matchings
     * @return a list of the finalized matchings
     */
    public List<Matching> getMatchingsFinalized(){
        return StreamSupport
                .stream(matchingRepo.findAll().spliterator(), false)
                .filter(matching -> matching.getFinalizedMatching()==FinalizedMatching.YES)
                .collect(Collectors.toList());
    }

    public void saveFinalizedMatchingsExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("FinalizedMatchings");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        //headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Applicant name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Title of position");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        List<Matching> matchings = this.getMatchingsFinalized();

        int currentRowNumber = 1;
        for (Matching m: matchings){
            Row row = sheet.createRow(currentRowNumber);

            Cell cell = row.createCell(0);
            cell.setCellValue(m.getApplicant().getFirstName() + ' ' + m.getApplicant().getLastName());
            cell.setCellStyle(style);

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(m.getJobOffer().getTitleOfPosition());
            currentRowNumber++;
        }

        File newExcelFile = new File("matchingReports.xlsx");
//        String path = currDir.getAbsolutePath();
//        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(newExcelFile);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
