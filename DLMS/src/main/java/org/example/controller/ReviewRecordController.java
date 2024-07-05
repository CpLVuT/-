package org.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.ReviewRecord;
import org.example.service.ReviewRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/review-records")
public class ReviewRecordController {

    @Autowired
    private ReviewRecordService reviewRecordService;

    @GetMapping
    public List<ReviewRecord> getAllReviewRecords() {
        return reviewRecordService.list();
    }

    @GetMapping("/{id}")
    public ReviewRecord getReviewRecordById(@PathVariable Long id) {
        return reviewRecordService.getById(id);
    }

    @PostMapping
    public ReviewRecord createReviewRecord(@RequestBody ReviewRecord reviewRecord) {
        reviewRecordService.saveReviewRecord(reviewRecord);
        return reviewRecord;
    }

    @PutMapping("/{id}")
    public ReviewRecord updateReviewRecord(@PathVariable Long id, @RequestBody ReviewRecord reviewRecord) {
        reviewRecord.setId(id);
        reviewRecordService.updateReviewRecord(reviewRecord);
        return reviewRecord;
    }

    @DeleteMapping("/{id}")
    public String deleteReviewRecord(@PathVariable Long id) {
        reviewRecordService.deleteReviewRecord(id);
        return "Review record deleted successfully";
    }

    @GetMapping("/research-result/{researchResultId}")
    public List<ReviewRecord> getReviewRecordsByResearchResultId(@PathVariable Long researchResultId) {
        return reviewRecordService.getReviewRecordsByResearchResultId(researchResultId);
    }

    @GetMapping("/reviewer/{reviewerId}")
    public List<ReviewRecord> getReviewRecordsByReviewerId(@PathVariable Long reviewerId) {
        return reviewRecordService.getReviewRecordsByReviewerId(reviewerId);
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        List<ReviewRecord> reviewRecords = reviewRecordService.list();
        ByteArrayInputStream in = exportReviewRecordsToExcel(reviewRecords);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=review_records.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    @PostMapping("/import")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        reviewRecordService.importFromExcel(file);
        return "Import successful";
    }

    private ByteArrayInputStream exportReviewRecordsToExcel(List<ReviewRecord> reviewRecords) throws IOException {
        String[] COLUMNs = {"Id", "Research Result Id", "Reviewer Id", "Review Status", "Review Reason", "Created At"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("ReviewRecords");

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            int rowIdx = 1;
            for (ReviewRecord reviewRecord : reviewRecords) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(reviewRecord.getId());
                row.createCell(1).setCellValue(reviewRecord.getResearchResultId());
                row.createCell(2).setCellValue(reviewRecord.getReviewerId());
                row.createCell(3).setCellValue(reviewRecord.getReviewStatus());
                row.createCell(4).setCellValue(reviewRecord.getReviewReason());
                row.createCell(5).setCellValue(String.valueOf(reviewRecord.getCreatedAt()));
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
