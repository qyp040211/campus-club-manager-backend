package com.club.campusclubmanager.enums;

import lombok.Getter;

/**
 * 通知渠道枚举
 */
@Getter
public enum NotificationChannel {
    /**
     * 站内消息
     */
    IN_APP("in_app", "站内消息"),

    /**
     * 邮件通知
     */
    EMAIL("email", "邮件通知");

    private final String code;
    private final String description;

    NotificationChannel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code获取枚举
     */
    public static NotificationChannel fromCode(String code) {
        for (NotificationChannel channel : values()) {
            if (channel.code.equals(code)) {
                return channel;
            }
        }
        throw new IllegalArgumentException("未知的通知渠道: " + code);
    }
}


