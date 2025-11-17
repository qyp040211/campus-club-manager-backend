-- ------------------------------------------------------------
-- School Club Management Platform - Initial MySQL Schema
-- ------------------------------------------------------------
-- Character set and collation recommendations
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `username`     VARCHAR(64)     NOT NULL COMMENT '账号',
                        `password`     VARCHAR(255)    NOT NULL COMMENT '密码（加密存储）',
                        `avatar`       VARCHAR(255)    DEFAULT NULL COMMENT '头像',
                        `gender`       TINYINT         DEFAULT NULL COMMENT '性别（0 未知 1 男 2 女）',
                        `phone`        VARCHAR(32)     NOT NULL COMMENT '手机号',
                        `email`        VARCHAR(128)    DEFAULT NULL COMMENT '邮箱',
                        `role`         VARCHAR(32)     NOT NULL DEFAULT 'user' COMMENT '角色 user/club_admin/admin',
                        `status`       TINYINT         NOT NULL DEFAULT 0 COMMENT '状态 0 正常 1 禁用',
                        `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `is_deleted`   TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 否 1 是',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_user_username` (`username`),
                        UNIQUE KEY `uk_user_phone` (`phone`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

-- ----------------------------
-- Table structure for `club`
-- ----------------------------
DROP TABLE IF EXISTS `club`;
CREATE TABLE `club` (
                        `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `name`         VARCHAR(128)    NOT NULL COMMENT '社团名称',
                        `description`  TEXT            DEFAULT NULL COMMENT '简介',
                        `logo`         VARCHAR(255)    DEFAULT NULL COMMENT '社团图标',
                        `status`       TINYINT         NOT NULL DEFAULT 0 COMMENT '状态 0 审核中 1 正常 2 禁用',
                        `create_user`  BIGINT UNSIGNED NOT NULL COMMENT '创建者 user_id',
                        `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `is_deleted`   TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 否 1 是',
                        PRIMARY KEY (`id`),
                        KEY `idx_club_create_user` (`create_user`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '社团表';

-- ----------------------------
-- Table structure for `club_member`
-- ----------------------------
DROP TABLE IF EXISTS `club_member`;
CREATE TABLE `club_member` (
                               `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `club_id`      BIGINT UNSIGNED NOT NULL COMMENT '社团ID',
                               `user_id`      BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
                               `role`         VARCHAR(32)     NOT NULL DEFAULT 'member' COMMENT 'member/leader',
                               `status`       TINYINT         NOT NULL DEFAULT 0 COMMENT '0 待审核 1 已通过 2 已拒绝',
                               `join_time`    DATETIME        DEFAULT NULL COMMENT '加入时间',
                               `memo`         VARCHAR(255)    DEFAULT NULL COMMENT '备注',
                               `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `is_deleted`   TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 否 1 是',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_member_club_user` (`club_id`, `user_id`),
                               KEY `idx_member_user` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '社团成员表';

-- ----------------------------
-- Table structure for `activity`
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
                            `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `club_id`      BIGINT UNSIGNED NOT NULL COMMENT '社团ID',
                            `title`        VARCHAR(128)    NOT NULL COMMENT '标题',
                            `content`      TEXT            DEFAULT NULL COMMENT '活动内容',
                            `cover`        VARCHAR(255)    DEFAULT NULL COMMENT '封面图',
                            `location`     VARCHAR(255)    DEFAULT NULL COMMENT '地点',
                            `start_time`   DATETIME        NOT NULL COMMENT '开始时间',
                            `end_time`     DATETIME        NOT NULL COMMENT '结束时间',
                            `status`       TINYINT         NOT NULL DEFAULT 0 COMMENT '0 草稿 1 审核中 2 已发布 3 下架',
                            `max_members`  INT             DEFAULT NULL COMMENT '最大报名人数',
                            `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted`   TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 否 1 是',
                            PRIMARY KEY (`id`),
                            KEY `idx_activity_club` (`club_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '活动表';

-- ----------------------------
-- Table structure for `activity_signup`
-- ----------------------------
DROP TABLE IF EXISTS `activity_signup`;
CREATE TABLE `activity_signup` (
                                   `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `activity_id`  BIGINT UNSIGNED NOT NULL COMMENT '活动ID',
                                   `user_id`      BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
                                   `status`       TINYINT         NOT NULL DEFAULT 0 COMMENT '0 已报名 1 已取消 2 已签到',
                                   `signup_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
                                   `remark`       VARCHAR(255)    DEFAULT NULL COMMENT '备注',
                                   `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `is_deleted`   TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 否 1 是',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uk_signup_activity_user` (`activity_id`, `user_id`),
                                   KEY `idx_signup_user` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '活动报名表';

-- ----------------------------
-- Table structure for `announcement`
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
                                `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `title`        VARCHAR(128)    NOT NULL COMMENT '标题',
                                `content`      TEXT            NOT NULL COMMENT '公告内容',
                                `type`         VARCHAR(32)     NOT NULL DEFAULT 'system' COMMENT '公告类型',
                                `create_user`  BIGINT UNSIGNED NOT NULL COMMENT '创建人（管理员ID）',
                                `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `is_deleted`   TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 否 1 是',
                                PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '公告表';

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
                           `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `user_id`      BIGINT UNSIGNED NOT NULL COMMENT '接收用户ID',
                           `content`      TEXT            NOT NULL COMMENT '通知内容',
                           `type`         VARCHAR(32)     NOT NULL COMMENT '通知类型',
                           `read_flag`    TINYINT         NOT NULL DEFAULT 0 COMMENT '0 未读 1 已读',
                           `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `is_deleted`   TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 否 1 是',
                           PRIMARY KEY (`id`),
                           KEY `idx_message_user` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '通知表';

SET FOREIGN_KEY_CHECKS = 1;