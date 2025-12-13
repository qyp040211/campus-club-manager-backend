package com.club.campusclubmanager.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户通知偏好设置实体
 */
@Data
@TableName("user_notification_setting")
public class UserNotificationSetting {
    /**
     * 设置ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是否启用邮件通知（0-禁用，1-启用）
     */
    private Integer emailEnabled;

    /**
     * 是否启用站内消息（0-禁用，1-启用）
     */
    private Integer inAppEnabled;

    /**
     * 审核消息通知（0-禁用，1-启用）
     */
    private Integer auditNotification;

    /**
     * 活动提醒通知（0-禁用，1-启用）
     */
    private Integer activityNotification;

    /**
     * 社团通知（0-禁用，1-启用）
     */
    private Integer clubNotification;

    /**
     * 系统通知（0-禁用，1-启用）
     */
    private Integer systemNotification;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记
     */
    @TableLogic
    private Integer isDeleted;
}


