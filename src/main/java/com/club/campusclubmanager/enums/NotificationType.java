package com.club.campusclubmanager.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 通知类型枚举
 */
@Getter
public enum NotificationType {
    /**
     * 系统通知
     */
    SYSTEM("system", "系统通知"),

    /**
     * 审核消息
     */
    AUDIT("audit", "审核消息"),

    /**
     * 活动提醒
     */
    ACTIVITY("activity", "活动提醒"),

    /**
     * 社团通知
     */
    CLUB("club", "社团通知");

    @EnumValue
    @JsonValue
    private final String code;

    private final String description;

    NotificationType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code获取枚举
     */
    public static NotificationType fromCode(String code) {
        for (NotificationType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的通知类型: " + code);
    }
}


