-- =========================================
-- 通知模块增强功能
-- =========================================

-- 1. 为notification表添加priority字段
ALTER TABLE `notification` 
ADD COLUMN `priority` TINYINT NOT NULL DEFAULT 1 COMMENT '优先级：0-低，1-普通，2-高，3-紧急' AFTER `related_id`;

-- 2. 创建用户通知偏好设置表
CREATE TABLE IF NOT EXISTS `user_notification_setting` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '设置ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `email_enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用邮件通知：0-禁用，1-启用',
    `in_app_enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用站内消息：0-禁用，1-启用',
    `audit_notification` TINYINT NOT NULL DEFAULT 1 COMMENT '审核消息通知：0-禁用，1-启用',
    `activity_notification` TINYINT NOT NULL DEFAULT 1 COMMENT '活动提醒通知：0-禁用，1-启用',
    `club_notification` TINYINT NOT NULL DEFAULT 1 COMMENT '社团通知：0-禁用，1-启用',
    `system_notification` TINYINT NOT NULL DEFAULT 1 COMMENT '系统通知：0-禁用，1-启用',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标记：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    CONSTRAINT `fk_setting_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户通知偏好设置表';

-- 3. 为现有通知记录设置默认优先级
UPDATE `notification` SET `priority` = 1 WHERE `priority` IS NULL;


