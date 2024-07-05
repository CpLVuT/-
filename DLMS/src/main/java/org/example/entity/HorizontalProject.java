package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("HorizontalProject")
public class HorizontalProject {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "project_name")
    private String projectName;

    @TableField(value = "contract_code")
    private String contractCode;

    @TableField(value = "total_amount")
    private Double totalAmount;

    @TableField(value = "party_a_name")
    private String partyAName;

    @TableField(value = "project_leader")
    private String projectLeader;

    @TableField(value = "executive_leader")
    private String executiveLeader;

    @TableField(value = "duration")
    private String duration;

    @TableField(value = "funding_record")
    private String fundingRecord;

    @TableField(value = "participants")
    private String participants;

    @TableField(value = "financial_account")
    private String financialAccount;

    @TableField(value = "funding_voucher")
    private String fundingVoucher;

    @TableField(value = "completion_date")
    private LocalDate completionDate;

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