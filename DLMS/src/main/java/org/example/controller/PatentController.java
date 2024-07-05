package org.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.Paper;
import org.example.entity.Patent;
import org.example.service.PatentService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/patents")
public class PatentController {

    @Autowired
    private PatentService patentService;

    @Autowired
    private UserService userService;
    @GetMapping("/approved")
    public List<Patent> getApprovedPatent() {
        return patentService.getApprovedProjects();
    }

    @GetMapping
    public List<Patent> getAllPatents() {
        return patentService.list();
    }

    @GetMapping("/{id}")
    public Patent getPatentById(@PathVariable Long id) {
        return patentService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Patent> getPatentsByUserId(@PathVariable Long userId) {
        return patentService.getPatentsByUserId(userId);
    }

    @PostMapping
    public Patent createPatent(@RequestBody Patent patent) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        patent.setUserId(userService.getUserIdByEmail(userDetails.getUsername()));
        patentService.savePatent(patent);
        return patent;
    }

    @PutMapping("/{id}")
    public Patent updatePatent(@PathVariable Long id, @RequestBody Patent patent) {
        patent.setId(id);
        patentService.updatePatent(patent);
        return patent;
    }

    @DeleteMapping("/{id}")
    public String deletePatent(@PathVariable Long id) {
        patentService.deletePatent(id);
        return "Patent deleted successfully";
    }

    @PostMapping("/review/{id}")
    public String reviewPatent(@PathVariable Long id, @RequestParam String reviewStatus) {
        patentService.reviewPatent(id, reviewStatus);
        return "Patent reviewed successfully";
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        List<Patent> patents = patentService.list();
        ByteArrayInputStream in = exportPatentsToExcel(patents);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=patents.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    @PostMapping("/import")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        patentService.importFromExcel(file);
        return "Import successful";
    }

    private ByteArrayInputStream exportPatentsToExcel(List<Patent> patents) throws IOException {
        String[] COLUMNs = {"Id", "Patent Name", "Patent Attribute", "Patent Type", "Authorization Status", "Application Number", "Publication Number", "Authorization Number", "Inventor", "Patentee", "Application Date", "Authorization Date", "Patent Certificate", "Submission Date", "User Id", "Review Status", "Created At", "Updated At"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Patents");

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            int rowIdx = 1;
            for (Patent patent : patents) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(patent.getId());
                row.createCell(1).setCellValue(patent.getPatentName());
                row.createCell(2).setCellValue(patent.getPatentAttribute());
                row.createCell(3).setCellValue(patent.getPatentType());
                row.createCell(4).setCellValue(patent.getAuthorizationStatus());
                row.createCell(5).setCellValue(patent.getApplicationNumber());
                row.createCell(6).setCellValue(patent.getPublicationNumber());
                row.createCell(7).setCellValue(patent.getAuthorizationNumber());
                row.createCell(8).setCellValue(patent.getInventor());
                row.createCell(9).setCellValue(patent.getPatentee());
                row.createCell(10).setCellValue(String.valueOf(patent.getApplicationDate()));
                row.createCell(11).setCellValue(String.valueOf(patent.getAuthorizationDate()));
                row.createCell(12).setCellValue(patent.getPatentCertificate());
                row.createCell(13).setCellValue(String.valueOf(patent.getSubmissionDate()));
                row.createCell(14).setCellValue(patent.getUserId());
                row.createCell(15).setCellValue(patent.getReviewStatus());
                row.createCell(16).setCellValue(String.valueOf(patent.getCreatedAt()));
                row.createCell(17).setCellValue(String.valueOf(patent.getUpdatedAt()));
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
