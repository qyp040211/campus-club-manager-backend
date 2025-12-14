package com.club.campusclubmanager.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import com.club.campusclubmanager.service.AIService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;



@RestController
@Tag(name = "ai问答", description = "ai问答相关接口")
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    // AI聊天接口
    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        try {
            String message = request.get("message");
            
            if (message == null || message.trim().isEmpty()) {
                throw new RuntimeException("消息内容不能为空");
            }

            String response = aiService.getAIResponse(message);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("message", "AI回复成功");
            responseData.put("data", Map.of("response", response));

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", false);
            responseData.put("message", e.getMessage());

            return ResponseEntity.ok(responseData);
        }
    }
}