package com.club.campusclubmanager.listener;

import com.club.campusclubmanager.event.NotificationEvent;
import com.club.campusclubmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 通知事件监听器（事件驱动架构）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    /**
     * 监听通知事件并处理
     */
    @Async("notificationExecutor")
    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        try {
            if (event.getUserIds().size() == 1) {
                // 单个用户
                notificationService.sendNotification(
                        event.getUserIds().get(0),
                        event.getTitle(),
                        event.getContent(),
                        event.getType(),
                        event.getRelatedType(),
                        event.getRelatedId(),
                        event.getPriority()
                );
            } else {
                // 批量用户
                notificationService.sendBatchNotification(
                        event.getUserIds(),
                        event.getTitle(),
                        event.getContent(),
                        event.getType(),
                        event.getRelatedType(),
                        event.getRelatedId(),
                        event.getPriority()
                );
            }
        } catch (Exception e) {
            log.error("处理通知事件失败: title={}", event.getTitle(), e);
        }
    }
}


