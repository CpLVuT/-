package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ReviewRecord")
public class ReviewRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "research_result_id")
    private Long researchResultId;

    @TableField(value = "reviewer_id")
    private Long reviewerId;

    @TableField(value = "review_status")
    private String reviewStatus;

    @TableField(value = "review_reason")
    private String reviewReason;

    @TableField(value = "created_at")
    private LocalDateTime createdAt;
}