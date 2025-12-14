package com.club.campusclubmanager.notification.channel.impl;

import com.club.campusclubmanager.entity.Notification;
import com.club.campusclubmanager.enums.NotificationChannel;
import com.club.campusclubmanager.enums.NotificationPriority;
import com.club.campusclubmanager.enums.NotificationType;
import com.club.campusclubmanager.mapper.NotificationMapper;
import com.club.campusclubmanager.notification.channel.NotificationChannelStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 站内消息通知渠道实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InAppNotificationChannel implements NotificationChannelStrategy {

    private final NotificationMapper notificationMapper;

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.IN_APP;
    }

    @Override
    public boolean send(Long userId, String title, String content, NotificationType type) {
        try {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setTitle(title);
            notification.setContent(content);
            notification.setType(type);
            notification.setPriority(NotificationPriority.NORMAL.getLevel());
            notification.setReadFlag(0);

            int result = notificationMapper.insert(notification);
            log.debug("发送站内消息通知成功: userId={}, title={}", userId, title);
            return result > 0;
        } catch (Exception e) {
            log.error("发送站内消息通知失败: userId={}, title={}", userId, title, e);
            return false;
        }
    }

    @Override
    public int sendBatch(List<Long> userIds, String title, String content, NotificationType type) {
        try {
            List<Notification> notifications = userIds.stream()
                    .map(userId -> {
                        Notification notification = new Notification();
                        notification.setUserId(userId);
                        notification.setTitle(title);
                        notification.setContent(content);
                        notification.setType(type);
                        notification.setPriority(NotificationPriority.NORMAL.getLevel());
                        notification.setReadFlag(0);
                        return notification;
                    })
                    .collect(Collectors.toList());

            int result = notificationMapper.batchInsert(notifications);
            log.debug("批量发送站内消息通知成功: count={}, title={}", result, title);
            return result;
        } catch (Exception e) {
            log.error("批量发送站内消息通知失败: title={}", title, e);
            return 0;
        }
    }

    @Override
    public boolean supports(NotificationType type) {
        return true;
    }
}