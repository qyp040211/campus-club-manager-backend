package com.club.campusclubmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "上传图片响应")
public class UploadImageResponse {
    
    @Schema(description = "上传是否成功")
    private boolean success;
    
    @Schema(description = "响应消息")
    private String message;
    
    @Schema(description = "图片URL")
    private String url;
    
    @Schema(description = "原始文件名")
    private String originalName;
    
    @Schema(description = "文件大小")
    private long fileSize;
}