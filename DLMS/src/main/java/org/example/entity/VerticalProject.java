package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("VerticalProject")
public class VerticalProject {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "project_name")
    private String projectName;

    @TableField(value = "project_code")
    private String projectCode;

    @TableField(value = "project_number")
    private String projectNumber;

    @TableField(value = "project_level")
    private String projectLevel;

    @TableField(value = "total_amount")
    private BigDecimal totalAmount;

    @TableField(value = "approval_department")
    private String approvalDepartment;

    @TableField(value = "project_type")
    private String projectType;

    @TableField(value = "duration")
    private String duration;

    @TableField(value = "funding_record")
    private String fundingRecord;

    @TableField(value = "participants")
    private String participants;

    @TableField(value = "project_leader")
    private String projectLeader;

    @TableField(value = "financial_account")
    private String financialAccount;

    @TableField(value = "completion_date")
    private LocalDate completionDate;

    @TableField(value = "approval_document")
    private String approvalDocument;

    @TableField(value = "completion_certificate")
    private String completionCertificate;

    @TableField(value = "completion_report")
    private String completionReport;

    @TableField(value = "submission_date")
    private LocalDate submissionDate;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "review_status")
    private String reviewStatus;

    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
}