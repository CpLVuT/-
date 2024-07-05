package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.HorizontalProject;
import org.example.entity.Paper;
import org.example.mapper.PaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

@Service
public class PaperService extends ServiceImpl<PaperMapper, Paper> {

    public boolean savePaper(Paper paper) {
        return save(paper);
    }

    public List<Paper> getPapersByUserId(Long userId) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }

    public boolean updatePaper(Paper paper) {
        return updateById(paper);
    }

    public boolean deletePaper(Long id) {
        return removeById(id);
    }

    public boolean reviewPaper(Long paperId, String reviewStatus) {
        Paper paper = getById(paperId);
        paper.setReviewStatus(reviewStatus);
        return updateById(paper);
    }

    public List<Paper> getApprovedProjects() {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
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

                Paper paper = new Paper();
                paper.setPaperTitle(row.getCell(1).getStringCellValue());
                paper.setJournalName(row.getCell(2).getStringCellValue());
                paper.setIssueNumber(row.getCell(3).getStringCellValue());
                paper.setVolumeNumber(row.getCell(4).getStringCellValue());
                paper.setPageNumber(row.getCell(5).getStringCellValue());
                paper.setIndexingLevel(row.getCell(6).getStringCellValue());
                paper.setAuthorList(row.getCell(7).getStringCellValue());
                paper.setFirstAuthor(row.getCell(8).getStringCellValue());
                paper.setCorrespondingAuthor(row.getCell(9).getStringCellValue());
                paper.setPublicationDate(row.getCell(10).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                paper.setImpactFactor(row.getCell(11).getNumericCellValue());
                paper.setPaperLink(row.getCell(12).getStringCellValue());
                paper.setSubmissionDate(row.getCell(13).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                paper.setUserId((long) row.getCell(14).getNumericCellValue());
                paper.setReviewStatus(row.getCell(15).getStringCellValue());

                savePaper(paper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
