package com.club.campusclubmanager.notification;

import com.club.campusclubmanager.enums.NotificationPriority;
import com.club.campusclubmanager.enums.NotificationType;
import com.club.campusclubmanager.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通知事件发布器（简化事件发布）
 */
@Component
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 发布通知事件（单个用户）
     */
    public void publishNotification(Long userId, String title, String content,
                                   NotificationType type, String relatedType, Long relatedId,
                                   NotificationPriority priority) {
        NotificationEvent event = new NotificationEvent(
                this,
                userId,
                title,
                content,
                type,
                relatedType,
                relatedId,
                priority
        );
        eventPublisher.publishEvent(event);
    }

    /**
     * 发布通知事件（批量用户）
     */
    public void publishNotification(List<Long> userIds, String title, String content,
                                   NotificationType type, String relatedType, Long relatedId,
                                   NotificationPriority priority) {
        NotificationEvent event = new NotificationEvent(
                this,
                userIds,
                title,
                content,
                type,
                relatedType,
                relatedId,
                priority,
                null,
                null
        );
        eventPublisher.publishEvent(event);
    }

    /**
     * 发布通知事件（使用默认优先级）
     */
    public void publishNotification(Long userId, String title, String content,
                                   NotificationType type, String relatedType, Long relatedId) {
        publishNotification(userId, title, content, type, relatedType, relatedId, NotificationPriority.NORMAL);
    }

    /**
     * 发布通知事件（批量用户，默认优先级）
     */
    public void publishNotification(List<Long> userIds, String title, String content,
                                   NotificationType type, String relatedType, Long relatedId) {
        publishNotification(userIds, title, content, type, relatedType, relatedId, NotificationPriority.NORMAL);
    }
}

