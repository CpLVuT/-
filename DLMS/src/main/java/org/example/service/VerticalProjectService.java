package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.HorizontalProject;
import org.example.entity.VerticalProject;
import org.example.mapper.VerticalProjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

@Service
public class VerticalProjectService extends ServiceImpl<VerticalProjectMapper, VerticalProject> {

    public boolean saveVerticalProject(VerticalProject project) {
        // 校验项目名称唯一性
        QueryWrapper<VerticalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_name", project.getProjectName());
        if (count(queryWrapper) > 0) {
            throw new RuntimeException("项目名称已存在，不可重复");
        }
        return save(project);
    }

    public List<VerticalProject> getProjectsByUserId(Long userId) {
        QueryWrapper<VerticalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }

    public boolean updateVerticalProject(VerticalProject project) {
        // 审核通过后不可修改
        VerticalProject existingProject = getById(project.getId());
        if ("APPROVED".equals(existingProject.getReviewStatus())) {
            throw new RuntimeException("审核通过的项目不可修改");
        }
        return updateById(project);
    }

    public boolean deleteVerticalProject(Long id) {
        // 审核通过后不可删除
        VerticalProject existingProject = getById(id);
        if ("APPROVED".equals(existingProject.getReviewStatus())) {
            throw new RuntimeException("审核通过的项目不可删除");
        }
        return removeById(id);
    }

    public boolean reviewProject(Long projectId, String reviewStatus) {
        VerticalProject project = getById(projectId);
        project.setReviewStatus(reviewStatus);
        return updateById(project);
    }

    public List<VerticalProject> getApprovedProjects() {
        QueryWrapper<VerticalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("review_status", "APPROVED");
        return list(queryWrapper);
    }

    public void importFromExcel(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // 跳过标题行
                }

                VerticalProject project = new VerticalProject();
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

                saveVerticalProject(project);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
