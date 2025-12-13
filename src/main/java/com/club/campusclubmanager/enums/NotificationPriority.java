package com.club.campusclubmanager.enums;

import lombok.Getter;

/**
 * 通知优先级枚举
 */
@Getter
public enum NotificationPriority {
    /**
     * 低优先级
     */
    LOW(0, "低"),

    /**
     * 普通优先级
     */
    NORMAL(1, "普通"),

    /**
     * 高优先级
     */
    HIGH(2, "高"),

    /**
     * 紧急
     */
    URGENT(3, "紧急");

    private final Integer level;
    private final String description;

    NotificationPriority(Integer level, String description) {
        this.level = level;
        this.description = description;
    }
}


