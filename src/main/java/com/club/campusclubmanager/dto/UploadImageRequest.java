package com.club.campusclubmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "上传图片请求")
public class UploadImageRequest {
    
    @Schema(description = "图片文件", required = true)
    @NotNull(message = "文件不能为空")
    private MultipartFile file;
    
    @Schema(description = "图片分类：avatar-头像, club-社团, activity-活动", defaultValue = "general")
    private String category = "general";
    
    @Schema(description = "关联社团ID")
    private Long clubId;
}