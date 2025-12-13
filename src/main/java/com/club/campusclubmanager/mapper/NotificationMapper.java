package com.club.campusclubmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.club.campusclubmanager.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通知Mapper
 */
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
    /**
     * 批量插入通知
     */
    int batchInsert(@Param("notifications") java.util.List<Notification> notifications);

    /**
     * 统计用户未读数量
     */
    Long countUnread(@Param("userId") Long userId);

    /**
     * 标记用户所有通知为已读
     */
    int markAllAsRead(@Param("userId") Long userId);
}


