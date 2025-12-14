package com.club.campusclubmanager.controller;

import com.club.campusclubmanager.common.Result;
import com.club.campusclubmanager.dto.UploadImageResponse;
import com.club.campusclubmanager.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "文件上传", description = "图片上传相关接口")
@RestController
@RequestMapping("/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {
    
    private final FileUploadService fileUploadService;
    
    @Operation(summary = "上传单张图片", description = "支持JPG、PNG、GIF等格式，最大10MB")
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<UploadImageResponse> uploadImage(
            @Parameter(description = "图片文件", required = true)
            @RequestParam("file") MultipartFile file,
            
            @Parameter(description = "图片分类：avatar/cover/content")
            @RequestParam(value = "category", required = false, defaultValue = "general") String category,
            
            @Parameter(description = "关联社团ID")
            @RequestParam(value = "clubId", required = false) Long clubId) {
        
        log.info("收到图片上传请求: {}, 大小: {} bytes, 分类: {}", 
                file.getOriginalFilename(), file.getSize(), category);
        
        UploadImageResponse response = fileUploadService.uploadImage(file, category, clubId);
        
        if (response.isSuccess()) {
            return Result.success("上传成功", response);
        } else {
            return Result.fail(response.getMessage());
        }
    }
    
    @Operation(summary = "快速上传测试", description = "简化版上传，仅用于测试")
    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> testUpload(
            @Parameter(description = "图片文件", required = true)
            @RequestParam("file") MultipartFile file) {
        
        try {
            String url = fileUploadService.quickUpload(file);
            return Result.success("上传成功", url);
        } catch (Exception e) {
            return Result.fail("上传失败: " + e.getMessage());
        }
    }
    
    @Operation(summary = "健康检查", description = "检查上传服务是否正常")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("文件上传服务正常", null);
    }
}