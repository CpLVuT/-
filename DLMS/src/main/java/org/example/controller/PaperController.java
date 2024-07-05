package org.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.Paper;
import org.example.service.PaperService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/papers")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Paper> getAllPapers() {
        return paperService.list();
    }

    @GetMapping("/approved")
    public List<Paper> getApprovedProjects() {
        return paperService.getApprovedProjects();
    }

    @GetMapping("/{id}")
    public Paper getPaperById(@PathVariable Long id) {
        return paperService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Paper> getPapersByUserId(@PathVariable Long userId) {
        return paperService.getPapersByUserId(userId);
    }

    @PostMapping
    public Paper createPaper(@RequestBody Paper paper) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        paper.setUserId(userService.getUserIdByEmail(userDetails.getUsername()));
        paperService.savePaper(paper);
        return paper;
    }

    @PutMapping("/{id}")
    public Paper updatePaper(@PathVariable Long id, @RequestBody Paper paper) {
        paper.setId(id);
        paperService.updatePaper(paper);
        return paper;
    }

    @DeleteMapping("/{id}")
    public String deletePaper(@PathVariable Long id) {
        paperService.deletePaper(id);
        return "Paper deleted successfully";
    }

    @PostMapping("/review/{id}")
    public String reviewPaper(@PathVariable Long id, @RequestParam String reviewStatus) {
        paperService.reviewPaper(id, reviewStatus);
        return "Paper reviewed successfully";
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        List<Paper> papers = paperService.list();
        ByteArrayInputStream in = exportPapersToExcel(papers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=papers.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    @PostMapping("/import")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        paperService.importFromExcel(file);
        return "Import successful";
    }

    private ByteArrayInputStream exportPapersToExcel(List<Paper> papers) throws IOException {
        String[] COLUMNs = {"Id", "Paper Title", "Journal Name", "Issue Number", "Volume Number", "Page Number", "Indexing Level", "Author List", "First Author", "Corresponding Author", "Publication Date", "Impact Factor", "Paper Link", "Submission Date", "User Id", "Review Status", "Created At", "Updated At"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Papers");

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            int rowIdx = 1;
            for (Paper paper : papers) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(paper.getId());
                row.createCell(1).setCellValue(paper.getPaperTitle());
                row.createCell(2).setCellValue(paper.getJournalName());
                row.createCell(3).setCellValue(paper.getIssueNumber());
                row.createCell(4).setCellValue(paper.getVolumeNumber());
                row.createCell(5).setCellValue(paper.getPageNumber());
                row.createCell(6).setCellValue(paper.getIndexingLevel());
                row.createCell(7).setCellValue(paper.getAuthorList());
                row.createCell(8).setCellValue(paper.getFirstAuthor());
                row.createCell(9).setCellValue(paper.getCorrespondingAuthor());
                row.createCell(10).setCellValue(String.valueOf(paper.getPublicationDate()));
                row.createCell(11).setCellValue(paper.getImpactFactor());
                row.createCell(12).setCellValue(paper.getPaperLink());
                row.createCell(13).setCellValue(String.valueOf(paper.getSubmissionDate()));
                row.createCell(14).setCellValue(paper.getUserId());
                row.createCell(15).setCellValue(paper.getReviewStatus());
                row.createCell(16).setCellValue(String.valueOf(paper.getCreatedAt()));
                row.createCell(17).setCellValue(String.valueOf(paper.getUpdatedAt()));
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
