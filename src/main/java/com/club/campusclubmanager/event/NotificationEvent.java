package com.club.campusclubmanager.event;

import com.club.campusclubmanager.enums.NotificationPriority;
import com.club.campusclubmanager.enums.NotificationType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 通知事件（用于事件驱动架构）
 */
@Getter
public class NotificationEvent extends ApplicationEvent {
    /**
     * 接收用户ID列表
     */
    private final List<Long> userIds;

    /**
     * 通知标题
     */
    private final String title;

    /**
     * 通知内容
     */
    private final String content;

    /**
     * 通知类型
     */
    private final NotificationType type;

    /**
     * 关联类型
     */
    private final String relatedType;

    /**
     * 关联ID
     */
    private final Long relatedId;

    /**
     * 优先级
     */
    private final NotificationPriority priority;

    /**
     * 是否发送邮件（默认根据用户偏好）
     */
    private final Boolean sendEmail;

    /**
     * 是否发送站内消息（默认根据用户偏好）
     */
    private final Boolean sendInApp;

    public NotificationEvent(Object source, List<Long> userIds, String title, String content,
                            NotificationType type, String relatedType, Long relatedId,
                            NotificationPriority priority, Boolean sendEmail, Boolean sendInApp) {
        super(source);
        this.userIds = userIds;
        this.title = title;
        this.content = content;
        this.type = type;
        this.relatedType = relatedType;
        this.relatedId = relatedId;
        this.priority = priority;
        this.sendEmail = sendEmail;
        this.sendInApp = sendInApp;
    }

    /**
     * 便捷构造方法（单个用户）
     */
    public NotificationEvent(Object source, Long userId, String title, String content,
                            NotificationType type, String relatedType, Long relatedId,
                            NotificationPriority priority) {
        this(source, List.of(userId), title, content, type, relatedType, relatedId, priority, null, null);
    }

    /**
     * 便捷构造方法（使用默认优先级）
     */
    public NotificationEvent(Object source, List<Long> userIds, String title, String content,
                            NotificationType type, String relatedType, Long relatedId) {
        this(source, userIds, title, content, type, relatedType, relatedId,
                NotificationPriority.NORMAL, null, null);
    }
}


