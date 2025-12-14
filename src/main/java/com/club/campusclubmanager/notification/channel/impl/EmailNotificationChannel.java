package com.club.campusclubmanager.notification.channel.impl;

import com.club.campusclubmanager.entity.User;
import com.club.campusclubmanager.enums.NotificationChannel;
import com.club.campusclubmanager.enums.NotificationType;
import com.club.campusclubmanager.mapper.UserMapper;
import com.club.campusclubmanager.notification.channel.NotificationChannelStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 邮件通知渠道实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationChannel implements NotificationChannelStrategy {

    private final JavaMailSender mailSender;
    private final UserMapper userMapper;

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public boolean send(Long userId, String title, String content, NotificationType type) {
        try {
            User user = userMapper.selectById(userId);
            if (user == null || user.getEmail() == null) {
                log.warn("用户不存在或未设置邮箱: userId={}", userId);
                return false;
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject(title);
            message.setText(content);
            message.setFrom("noreply@campus-club.com"); // 可以从配置读取

            mailSender.send(message);
            log.debug("发送邮件通知成功: userId={}, email={}, title={}", userId, user.getEmail(), title);
            return true;
        } catch (Exception e) {
            log.error("发送邮件通知失败: userId={}, title={}", userId, title, e);
            return false;
        }
    }

    @Override
    public int sendBatch(List<Long> userIds, String title, String content, NotificationType type) {
        int successCount = 0;
        for (Long userId : userIds) {
            if (send(userId, title, content, type)) {
                successCount++;
            }
        }
        return successCount;
    }

    @Override
    public boolean supports(NotificationType type) {
        // 邮件通知支持所有类型
        return true;
    }
}


