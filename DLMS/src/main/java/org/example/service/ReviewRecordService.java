package org.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.ReviewRecord;
import org.example.mapper.ReviewRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

@Service
public class ReviewRecordService extends ServiceImpl<ReviewRecordMapper, ReviewRecord> {

    public boolean saveReviewRecord(ReviewRecord reviewRecord) {
        return save(reviewRecord);
    }

    public boolean updateReviewRecord(ReviewRecord reviewRecord) {
        return updateById(reviewRecord);
    }

    public boolean deleteReviewRecord(Long id) {
        return removeById(id);
    }

    public List<ReviewRecord> getReviewRecordsByResearchResultId(Long researchResultId) {
        return lambdaQuery().eq(ReviewRecord::getResearchResultId, researchResultId).list();
    }

    public List<ReviewRecord> getReviewRecordsByReviewerId(Long reviewerId) {
        return lambdaQuery().eq(ReviewRecord::getReviewerId, reviewerId).list();
    }

    public void importFromExcel(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // 跳过标题行
                }

                ReviewRecord reviewRecord = new ReviewRecord();
                reviewRecord.setResearchResultId((long) row.getCell(1).getNumericCellValue());
                reviewRecord.setReviewerId((long) row.getCell(2).getNumericCellValue());
                reviewRecord.setReviewStatus(row.getCell(3).getStringCellValue());
                reviewRecord.setReviewReason(row.getCell(4).getStringCellValue());

                saveReviewRecord(reviewRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
