package com.club.campusclubmanager.notification.channel;

import com.club.campusclubmanager.enums.NotificationChannel;
import com.club.campusclubmanager.enums.NotificationType;

import java.util.List;

/**
 * 通知渠道策略接口
 */
public interface NotificationChannelStrategy {
    /**
     * 获取渠道类型
     */
    NotificationChannel getChannel();

    /**
     * 发送通知
     *
     * @param userId 用户ID
     * @param title 标题
     * @param content 内容
     * @param type 通知类型
     * @return 是否发送成功
     */
    boolean send(Long userId, String title, String content, NotificationType type);

    /**
     * 批量发送通知
     *
     * @param userIds 用户ID列表
     * @param title 标题
     * @param content 内容
     * @param type 通知类型
     * @return 成功发送的数量
     */
    int sendBatch(List<Long> userIds, String title, String content, NotificationType type);

    /**
     * 是否支持该通知类型
     */
    boolean supports(NotificationType type);
}


