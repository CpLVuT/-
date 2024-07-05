package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("Patent")
public class Patent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String patentName;
    private String patentAttribute;
    private String patentType;
    private String authorizationStatus;
    private String applicationNumber;
    private String publicationNumber;
    private String authorizationNumber;
    private String inventor;
    private String patentee;
    private LocalDate applicationDate;
    private LocalDate authorizationDate;
    private String patentCertificate;
    private LocalDate submissionDate;
    private Long userId;
    private String reviewStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
