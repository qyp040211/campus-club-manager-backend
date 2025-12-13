package com.club.campusclubmanager.service;  // ✅ 正确的包名

import com.club.campusclubmanager.dto.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    
    UploadImageResponse uploadImage(MultipartFile file, String category, Long clubId);
    
    String quickUpload(MultipartFile file);
}