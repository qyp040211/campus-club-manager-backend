package com.club.campusclubmanager.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.club.campusclubmanager.dto.UploadImageResponse;
import com.club.campusclubmanager.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    
    private final OSS ossClient;
    
    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;
    
    @Override
    public UploadImageResponse uploadImage(MultipartFile file, String category, Long clubId) {
        try {
            // 1. 基础校验
            if (file.isEmpty()) {
                return UploadImageResponse.builder()
                        .success(false)
                        .message("文件不能为空")
                        .build();
            }
            
            // 2. 文件大小限制（建议10MB）
            if (file.getSize() > 10 * 1024 * 1024) {
                return UploadImageResponse.builder()
                        .success(false)
                        .message("文件大小不能超过10MB")
                        .build();
            }
            
            // 3. 文件类型校验
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return UploadImageResponse.builder()
                        .success(false)
                        .message("只能上传图片文件")
                        .build();
            }
            
            // 4. 生成文件名
            String fileName = generateFileName(file.getOriginalFilename());
            
            // 5. 上传到OSS（公共读Bucket）
            try (InputStream inputStream = file.getInputStream()) {
                PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileName, inputStream);
                // ⭐️ 重要：设置公共读属性
                putRequest.setProcess("public-read-write");
                ossClient.putObject(putRequest);
            }
            
            // 6. 生成永久有效的URL
            String url = generatePermanentUrl(fileName);
            
            log.info("图片上传成功: {}, 大小: {} bytes", url, file.getSize());
            
            return UploadImageResponse.builder()
                    .success(true)
                    .message("上传成功")
                    .url(url)  // ⭐️ 永久有效的URL
                    .originalName(file.getOriginalFilename())
                    .fileSize(file.getSize())
                    .build();
            
        } catch (Exception e) {
            log.error("上传图片失败", e);
            return UploadImageResponse.builder()
                    .success(false)
                    .message("上传失败: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * 生成文件名（保持你的方案A）
     */
    private String generateFileName(String originalFilename) {
        String extension = ".jpg";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return "images/" + UUID.randomUUID() + extension;
    }
    
    /**
     * ⭐️ 生成永久有效的URL（核心）
     */
    private String generatePermanentUrl(String fileName) {
        // Bucket必须设置为公共读
        // 格式：https://{bucket}.{region}.aliyuncs.com/{fileName}
        return String.format("https://%s.oss-cn-guangzhou.aliyuncs.com/%s", 
                bucketName, fileName);
    }
    
    @Override
    public String quickUpload(MultipartFile file) {
        try {
            String fileName = generateFileName(file.getOriginalFilename());
            
            try (InputStream inputStream = file.getInputStream()) {
                PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileName, inputStream);
                putRequest.setProcess("public-read-write");
                ossClient.putObject(putRequest);
            }
            
            return generatePermanentUrl(fileName);
        } catch (Exception e) {
            throw new RuntimeException("上传失败: " + e.getMessage(), e);
        }
    }
}