package com.club.campusclubmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 标记已读请求
 */
@Data
public class MarkReadRequest {
    /**
     * 通知ID（标记单条时使用）
     */
    private Long notificationId;

    /**
     * 是否标记全部已读
     */
    @NotNull(message = "请指定操作类型")
    private Boolean markAll;
}


