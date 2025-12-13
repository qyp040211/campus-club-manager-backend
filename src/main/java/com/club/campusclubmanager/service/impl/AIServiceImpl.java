package com.club.campusclubmanager.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIServiceImpl implements com.club.campusclubmanager.service.AIService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public AIServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getAIResponse(String message) {
        try {
            // 构建请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + apiKey);
            headers.put("Content-Type", "application/json");

            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 1000);
            requestBody.put("stream", false);
            
            // 设置消息
            List<Map<String, String>> messages = List.of(
                Map.of(
                    "role", "system",
                    "content", "你是一个专业的社团管理助手，专注于提供高质量的社团管理建议、活动推荐和管理技巧。请用友好、专业的语言回答用户的问题，并尽可能提供详细和实用的信息。"
                ),
                Map.of(
                    "role", "user",
                    "content", message
                )
            );
            requestBody.put("messages", messages);

            // 创建HttpEntity封装请求头和请求体
            org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
            httpHeaders.setAll(headers);
            org.springframework.http.HttpEntity<Map<String, Object>> httpEntity = new org.springframework.http.HttpEntity<>(requestBody, httpHeaders);
            
            // 发送请求并获取响应
            String response = restTemplate.postForObject(apiUrl, httpEntity, String.class);
            
            // 解析响应JSON，提取AI回复内容
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode rootNode = mapper.readTree(response);
            String aiReply = rootNode.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
            
            return aiReply;
        } catch (Exception e) {
            // 处理异常
            return "抱歉，AI服务暂时不可用";
        }
    }
}