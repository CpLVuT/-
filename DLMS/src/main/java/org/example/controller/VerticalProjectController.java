package org.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.HorizontalProject;
import org.example.entity.VerticalProject;
import org.example.service.VerticalProjectService;
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
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/vertical-projects")
public class VerticalProjectController {

    @Autowired
    private VerticalProjectService verticalProjectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<VerticalProject> getAllVerticalProjects() {
        return verticalProjectService.list();
    }

    @GetMapping("/{id}")
    public VerticalProject getVerticalProjectById(@PathVariable Long id) {
        return verticalProjectService.getById(id);
    }
    @GetMapping("/approved")
    public List<VerticalProject> getApprovedProjects() {
        return verticalProjectService.getApprovedProjects();
    }

    @GetMapping("/user/{userId}")
    public List<VerticalProject> getProjectsByUserId(@PathVariable Long userId) {
        return verticalProjectService.getProjectsByUserId(userId);
    }

    @PostMapping
    public VerticalProject createVerticalProject(@RequestBody VerticalProject project) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        project.setUserId(getUserIdFromDetails(userDetails));
        verticalProjectService.saveVerticalProject(project);
        return project;
    }

    @PutMapping("/{id}")
    public VerticalProject updateVerticalProject(@PathVariable Long id, @RequestBody VerticalProject project) {
        project.setId(id);
        verticalProjectService.updateVerticalProject(project);
        return project;
    }

    @DeleteMapping("/{id}")
    public String deleteVerticalProject(@PathVariable Long id) {
        verticalProjectService.deleteVerticalProject(id);
        return "Project deleted successfully";
    }

    @PostMapping("/review/{id}")
    public String reviewProject(@PathVariable Long id, @RequestParam String reviewStatus) {
        verticalProjectService.reviewProject(id, reviewStatus);
        return "Project reviewed successfully";
    }

    @PostMapping("/batch")
    public String batchUpload(@RequestBody List<VerticalProject> projects) {
        verticalProjectService.saveBatch(projects);
        return "Batch upload successful";
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        List<VerticalProject> projects = verticalProjectService.list();
        ByteArrayInputStream in = exportProjectsToExcel(projects);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=vertical-projects.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }

    @PostMapping("/import")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        verticalProjectService.importFromExcel(file);
        return "Import successful";
    }

    private Long getUserIdFromDetails(UserDetails userDetails) {
        return userService.getUserIdByEmail(userDetails.getUsername());
    }

    private ByteArrayInputStream exportProjectsToExcel(List<VerticalProject> projects) throws IOException {
        String[] COLUMNs = {"Id", "Project Name", "Project Code", "Project Number", "Project Level", "Total Amount", "Approval Department", "Project Type", "Duration", "Funding Record", "Participants", "Project Leader", "Financial Account", "Completion Date", "Approval Document", "Completion Certificate", "Completion Report", "Submission Date", "User Id", "Review Status", "Created At", "Updated At"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("VerticalProjects");

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            int rowIdx = 1;
            for (VerticalProject project : projects) {
                Row row = sheet.createRow(rowIdx++);

                project.setProjectName(row.getCell(1).getStringCellValue());
                project.setProjectCode(row.getCell(2).getStringCellValue());
                project.setProjectNumber(row.getCell(3).getStringCellValue());
                project.setProjectLevel(row.getCell(4).getStringCellValue());
                project.setTotalAmount(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                project.setApprovalDepartment(row.getCell(6).getStringCellValue());
                project.setProjectType(row.getCell(7).getStringCellValue());
                project.setDuration(row.getCell(8).getStringCellValue());
                project.setFundingRecord(row.getCell(9).getStringCellValue());
                project.setParticipants(row.getCell(10).getStringCellValue());
                project.setProjectLeader(row.getCell(11).getStringCellValue());
                project.setFinancialAccount(row.getCell(12).getStringCellValue());
                project.setCompletionDate(row.getCell(13).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                project.setApprovalDocument(row.getCell(14).getStringCellValue());
                project.setCompletionCertificate(row.getCell(15).getStringCellValue());
                project.setCompletionReport(row.getCell(16).getStringCellValue());
                project.setSubmissionDate(row.getCell(17).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                project.setUserId((long) row.getCell(18).getNumericCellValue());
                project.setReviewStatus(row.getCell(19).getStringCellValue());


            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
