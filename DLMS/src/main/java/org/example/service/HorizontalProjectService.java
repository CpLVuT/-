package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.HorizontalProject;
import org.example.mapper.HorizontalProjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

@Service
public class HorizontalProjectService extends ServiceImpl<HorizontalProjectMapper, HorizontalProject> {

    public boolean saveHorizontalProject(HorizontalProject project) {
        // 校验项目名称唯一性
        QueryWrapper<HorizontalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_name", project.getProjectName());
        if (count(queryWrapper) > 0) {
            throw new RuntimeException("项目名称已存在，不可重复");
        }
        return save(project);
    }

    public List<HorizontalProject> getProjectsByUserId(Long userId) {
        QueryWrapper<HorizontalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }

    public boolean updateHorizontalProject(HorizontalProject project) {
        // 审核通过后不可修改
        HorizontalProject existingProject = getById(project.getId());
        if ("APPROVED".equals(existingProject.getReviewStatus())) {
            throw new RuntimeException("审核通过的项目不可修改");
        }
        return updateById(project);
    }

    public boolean deleteHorizontalProject(Long id) {
        // 审核通过后不可删除
        HorizontalProject existingProject = getById(id);
        if ("APPROVED".equals(existingProject.getReviewStatus())) {
            throw new RuntimeException("审核通过的项目不可删除");
        }
        return removeById(id);
    }

    public boolean reviewProject(Long projectId, String reviewStatus) {
        HorizontalProject project = getById(projectId);
        project.setReviewStatus(reviewStatus);
        return updateById(project);
    }

    public List<HorizontalProject> getApprovedProjects() {
        QueryWrapper<HorizontalProject> queryWrapper = new QueryWrapper<>();
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

                HorizontalProject project = new HorizontalProject();
                project.setProjectName(row.getCell(1).getStringCellValue());
                project.setContractCode(row.getCell(2).getStringCellValue());
                project.setTotalAmount(row.getCell(3).getNumericCellValue());
                project.setPartyAName(row.getCell(4).getStringCellValue());
                project.setProjectLeader(row.getCell(5).getStringCellValue());
                project.setExecutiveLeader(row.getCell(6).getStringCellValue());
                project.setDuration(row.getCell(7).getStringCellValue());
                project.setFundingRecord(row.getCell(8).getStringCellValue());
                project.setParticipants(row.getCell(9).getStringCellValue());
                project.setFinancialAccount(row.getCell(10).getStringCellValue());
                project.setFundingVoucher(row.getCell(11).getStringCellValue());
                project.setCompletionDate(row.getCell(12).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                project.setCompletionCertificate(row.getCell(13).getStringCellValue());
                project.setCompletionReport(row.getCell(14).getStringCellValue());
                project.setSubmissionDate(row.getCell(15).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                project.setUserId((long) row.getCell(16).getNumericCellValue());
                project.setReviewStatus(row.getCell(17).getStringCellValue());

                // 只保存审核通过的项目
                if ("APPROVED".equals(project.getReviewStatus())) {
                    saveHorizontalProject(project);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
