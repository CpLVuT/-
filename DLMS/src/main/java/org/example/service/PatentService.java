package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.HorizontalProject;
import org.example.entity.Patent;
import org.example.mapper.PatentMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

@Service
public class PatentService extends ServiceImpl<PatentMapper, Patent> {

    public boolean savePatent(Patent patent) {
        return save(patent);
    }

    public List<Patent> getPatentsByUserId(Long userId) {
        QueryWrapper<Patent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }

    public boolean updatePatent(Patent patent) {
        return updateById(patent);
    }

    public boolean deletePatent(Long id) {
        return removeById(id);
    }

    public boolean reviewPatent(Long patentId, String reviewStatus) {
        Patent patent = getById(patentId);
        patent.setReviewStatus(reviewStatus);
        return updateById(patent);
    }

    public List<Patent> getApprovedProjects() {
        QueryWrapper<Patent> queryWrapper = new QueryWrapper<>();
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

                Patent patent = new Patent();
                patent.setPatentName(row.getCell(1).getStringCellValue());
                patent.setPatentAttribute(row.getCell(2).getStringCellValue());
                patent.setPatentType(row.getCell(3).getStringCellValue());
                patent.setAuthorizationStatus(row.getCell(4).getStringCellValue());
                patent.setApplicationNumber(row.getCell(5).getStringCellValue());
                patent.setPublicationNumber(row.getCell(6).getStringCellValue());
                patent.setAuthorizationNumber(row.getCell(7).getStringCellValue());
                patent.setInventor(row.getCell(8).getStringCellValue());
                patent.setPatentee(row.getCell(9).getStringCellValue());
                patent.setApplicationDate(row.getCell(10).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                patent.setAuthorizationDate(row.getCell(11).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                patent.setPatentCertificate(row.getCell(12).getStringCellValue());
                patent.setSubmissionDate(row.getCell(13).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                patent.setUserId((long) row.getCell(14).getNumericCellValue());
                patent.setReviewStatus(row.getCell(15).getStringCellValue());

                savePatent(patent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
