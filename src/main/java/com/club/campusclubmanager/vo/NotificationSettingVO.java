package com.club.campusclubmanager.vo;

import lombok.Data;

/**
 * 通知设置视图对象
 */
@Data
public class NotificationSettingVO {
    /**
     * 是否启用邮件通知
     */
    private Boolean emailEnabled;

    /**
     * 是否启用站内消息
     */
    private Boolean inAppEnabled;

    /**
     * 审核消息通知
     */
    private Boolean auditNotification;

    /**
     * 活动提醒通知
     */
    private Boolean activityNotification;

    /**
     * 社团通知
     */
    private Boolean clubNotification;

    /**
     * 系统通知
     */
    private Boolean systemNotification;
}


