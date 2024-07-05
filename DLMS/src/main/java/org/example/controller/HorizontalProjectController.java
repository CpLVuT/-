package org.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.HorizontalProject;
import org.example.service.HorizontalProjectService;
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
@RequestMapping("/api/horizontal-projects")
public class HorizontalProjectController {

    @Autowired
    private HorizontalProjectService horizontalProjectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<HorizontalProject> getAllHorizontalProjects() {
        return horizontalProjectService.list();
    }

    @GetMapping("/{id}")
    public HorizontalProject getHorizontalProjectById(@PathVariable Long id) {
        return horizontalProjectService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<HorizontalProject> getProjectsByUserId(@PathVariable Long userId) {
        return horizontalProjectService.getProjectsByUserId(userId);
    }

    @GetMapping("/approved")
    public List<HorizontalProject> getApprovedProjects() {
        return horizontalProjectService.getApprovedProjects();
    }

    @PostMapping
    public HorizontalProject createHorizontalProject(@RequestBody HorizontalProject project) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        project.setUserId(userService.getUserIdByEmail(userDetails.getUsername()));
        horizontalProjectService.saveHorizontalProject(project);
        return project;
    }

    @PutMapping("/{id}")
    public HorizontalProject updateHorizontalProject(@PathVariable Long id, @RequestBody HorizontalProject project) {
        project.setId(id);
        horizontalProjectService.updateHorizontalProject(project);
        return project;
    }

    @DeleteMapping("/{id}")
    public String deleteHorizontalProject(@PathVariable Long id) {
        horizontalProjectService.deleteHorizontalProject(id);
        return "Project deleted successfully";
    }

    @PostMapping("/review/{id}")
    public String reviewProject(@PathVariable Long id, @RequestParam String reviewStatus) {
        horizontalProjectService.reviewProject(id, reviewStatus);
        return "Project reviewed successfully";
    }

    @PostMapping("/batch")
    public String batchUpload(@RequestBody List<HorizontalProject> projects) {
        try {
            horizontalProjectService.saveBatch(projects);
        } catch(Exception e) {
            e.printStackTrace();  // optional
            return "Error occurred: " + e.getMessage();
        }
        return "Batch upload successful";
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        List<HorizontalProject> projects = horizontalProjectService.getApprovedProjects();
        ByteArrayInputStream in = exportProjectsToExcel(projects);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=horizontal-projects.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    @PostMapping("/import")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        horizontalProjectService.importFromExcel(file);
        return "Import successful";
    }

    private ByteArrayInputStream exportProjectsToExcel(List<HorizontalProject> projects) throws IOException {
        String[] COLUMNs = {"Id", "Project Name", "Contract Code", "Total Amount", "Party A Name", "Project Leader", "Executive Leader", "Duration", "Funding Record", "Participants", "Financial Account", "Funding Voucher", "Completion Date", "Completion Certificate", "Completion Report", "Submission Date", "User Id", "Review Status", "Created At", "Updated At"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("HorizontalProjects");

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            int rowIdx = 1;
            for (HorizontalProject project : projects) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(project.getId());
                row.createCell(1).setCellValue(project.getProjectName());
                row.createCell(2).setCellValue(project.getContractCode());
                row.createCell(3).setCellValue(project.getTotalAmount());
                row.createCell(4).setCellValue(project.getPartyAName());
                row.createCell(5).setCellValue(project.getProjectLeader());
                row.createCell(6).setCellValue(project.getExecutiveLeader());
                row.createCell(7).setCellValue(project.getDuration());
                row.createCell(8).setCellValue(project.getFundingRecord());
                row.createCell(9).setCellValue(project.getParticipants());
                row.createCell(10).setCellValue(project.getFinancialAccount());
                row.createCell(11).setCellValue(project.getFundingVoucher());
                row.createCell(12).setCellValue(String.valueOf(project.getCompletionDate()));
                row.createCell(13).setCellValue(project.getCompletionCertificate());
                row.createCell(14).setCellValue(project.getCompletionReport());
                row.createCell(15).setCellValue(String.valueOf(project.getSubmissionDate()));
                row.createCell(16).setCellValue(project.getUserId());
                row.createCell(17).setCellValue(project.getReviewStatus());
                row.createCell(18).setCellValue(String.valueOf(project.getCreatedAt()));
                row.createCell(19).setCellValue(String.valueOf(project.getUpdatedAt()));
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
