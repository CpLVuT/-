package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("Paper")
public class Paper {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String paperTitle;
    private String journalName;
    private String issueNumber;
    private String volumeNumber;
    private String pageNumber;
    private String indexingLevel;
    private String authorList;
    private String firstAuthor;
    private String correspondingAuthor;
    private LocalDate publicationDate;
    private Double impactFactor;
    private String paperLink;
    private LocalDate submissionDate;
    private Long userId;
    private String reviewStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
