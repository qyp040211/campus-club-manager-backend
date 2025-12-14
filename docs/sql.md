-- MySQL dump 10.13  Distrib 9.3.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: campus_club
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `club_id` bigint NOT NULL COMMENT '所属社团ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动内容详情',
  `cover` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '活动封面图URL',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '活动地点',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `signup_start_time` datetime DEFAULT NULL COMMENT '报名开始时间',
  `signup_end_time` datetime DEFAULT NULL COMMENT '报名截止时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'draft' COMMENT '活动状态：draft-草稿, pending-审核中, published-已发布, cancelled-已取消, completed-已完成',
  `max_members` int DEFAULT NULL COMMENT '最大报名人数（NULL表示不限制）',
  `current_members` int NOT NULL DEFAULT '0' COMMENT '当前报名人数（冗余字段）',
  `create_user` bigint NOT NULL COMMENT '创建者用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_club_id` (`club_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_start_time` (`start_time`) USING BTREE,
  KEY `idx_end_time` (`end_time`) USING BTREE,
  KEY `idx_create_user_id` (`create_user`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  CONSTRAINT `fk_activity_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_activity_create_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chk_activity_time` CHECK ((`end_time` >= `start_time`))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='活动表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,1,'Spring Boot 3.4 新特性分享会','本次分享会将深入讲解 Spring Boot 3.4 的新特性，包括虚拟线程支持、性能优化等内容。欢迎对 Java 开发感兴趣的同学参加！','https://example.com/activity/springboot.png','教学楼A-301','2025-11-25 14:00:00','2025-11-25 16:00:00','2025-11-17 08:00:00','2025-11-24 23:59:59','published',50,12,3,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(2,1,'ACM算法竞赛月赛','每月一次的算法竞赛，题目涵盖数据结构、动态规划、图论等，欢迎挑战！','https://example.com/activity/acm.png','机房B-201','2025-11-30 19:00:00','2025-11-30 22:00:00','2025-11-17 08:00:00','2025-11-29 18:00:00','published',100,35,3,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(3,1,'Java后端项目实战工作坊','从零开始搭建一个完整的Spring Boot项目（草稿）','https://example.com/activity/basketball.png','实验楼C-101','2025-12-15 14:00:00','2025-12-15 17:00:00','2025-12-01 08:00:00','2025-12-14 18:00:00','draft',30,0,3,'2025-11-17 22:38:30','2025-11-17 23:14:24',0),(4,2,'新生杯篮球赛','面向全校新生的篮球比赛，组队参赛，展现你的篮球技巧！','https://example.com/activity/basketball.png','体育馆篮球场','2025-11-28 15:00:00','2025-11-28 18:00:00','2025-11-17 08:00:00','2025-11-27 18:00:00','published',200,78,3,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(5,2,'每周篮球训练','定期篮球训练，提升技术水平','https://example.com/activity/training.png','体育馆','2025-11-20 16:00:00','2025-11-20 18:00:00','2025-11-17 08:00:00','2025-11-19 18:00:00','published',30,15,3,'2025-11-17 22:38:30','2025-11-17 23:14:11',0),(6,3,'秋日校园摄影大赛','用镜头记录校园秋色，优秀作品将在校园展出','https://example.com/activity/photo-contest.png','全校园','2025-11-22 09:00:00','2025-11-22 17:00:00','2025-11-17 08:00:00','2025-11-21 18:00:00','pending',20,0,2,'2025-11-17 22:38:30','2025-11-17 23:14:14',0),(7,4,'迎新音乐会','音乐社年度盛典，精彩演出不容错过！','https://example.com/activity/concert.png','大礼堂','2025-12-05 19:00:00','2025-12-05 21:00:00','2025-11-17 08:00:00','2025-12-04 18:00:00','published',500,203,3,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(8,1,'2024年度编程马拉松','24小时编程挑战赛（已结束）','https://example.com/activity/hackathon.png','图书馆','2024-10-20 08:00:00','2024-10-21 08:00:00','2024-10-01 08:00:00','2024-10-19 18:00:00','completed',50,45,3,'2025-11-17 22:38:30','2025-11-17 22:38:30',0);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_signup`
--

DROP TABLE IF EXISTS `activity_signup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_signup` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '报名记录ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'registered' COMMENT '报名状态：registered-已报名, cancelled-已取消, checked_in-已签到, absent-缺席',
  `signup_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  `checkin_time` datetime DEFAULT NULL COMMENT '签到时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_activity_user` (`activity_id`,`user_id`,`is_deleted`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_signup_time` (`signup_time`) USING BTREE,
  CONSTRAINT `fk_activity_signup_activity` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_activity_signup_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='活动报名表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_signup`
--

LOCK TABLES `activity_signup` WRITE;
/*!40000 ALTER TABLE `activity_signup` DISABLE KEYS */;
INSERT INTO `activity_signup` VALUES (1,1,1,'registered','2025-11-17 09:30:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(2,1,2,'registered','2025-11-17 10:15:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(3,2,1,'registered','2025-11-17 09:45:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(4,2,2,'registered','2025-11-17 11:20:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(5,2,3,'cancelled','2025-11-17 08:30:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(6,4,1,'registered','2025-11-17 14:00:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(7,4,2,'registered','2025-11-17 15:30:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(8,5,1,'registered','2025-11-17 16:00:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(9,7,1,'registered','2025-11-17 10:00:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(10,7,2,'registered','2025-11-17 11:00:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(11,7,3,'registered','2025-11-17 12:00:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(12,8,1,'checked_in','2024-10-15 09:00:00','2024-10-20 08:30:00',NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(13,8,2,'checked_in','2024-10-16 10:00:00','2024-10-20 08:45:00',NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(14,8,3,'absent','2024-10-18 14:00:00',NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0);
/*!40000 ALTER TABLE `activity_signup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告内容',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'system' COMMENT '公告类型：system-系统公告, club-社团公告, activity-活动公告',
  `related_id` bigint DEFAULT NULL COMMENT '关联ID（社团ID或活动ID，根据type类型关联）',
  `priority` tinyint NOT NULL DEFAULT '0' COMMENT '优先级：0-普通, 1-重要, 2-紧急',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'published' COMMENT '状态：draft-草稿, published-已发布, archived-已归档',
  `create_user_id` bigint NOT NULL COMMENT '创建者用户ID',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_type` (`type`) USING BTREE,
  KEY `idx_related_id` (`related_id`) USING BTREE,
  KEY `idx_priority` (`priority`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_create_user_id` (`create_user_id`) USING BTREE,
  KEY `idx_publish_time` (`publish_time`) USING BTREE,
  CONSTRAINT `fk_announcement_create_user` FOREIGN KEY (`create_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (1,'平台上线通知','学校社团管理平台正式上线！欢迎各位同学注册使用，浏览社团、参与活动。','system',NULL,2,'published',1,'2024-09-01 08:00:00',NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(2,'系统维护公告','本平台将于2025年11月20日凌晨2:00-4:00进行系统维护，期间无法访问，请各位同学提前安排。','system',NULL,1,'published',1,'2025-11-18 10:00:00',NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(3,'编程协会招新啦！','编程协会现面向全校招新，欢迎热爱编程的同学加入我们！','club',1,1,'published',3,'2024-09-01 09:00:00',NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(4,'篮球社训练时间调整','由于场地原因，本周篮球训练时间调整为周六下午3点，请各位成员注意。','club',2,0,'published',3,'2025-11-18 14:00:00',NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(5,'Spring Boot分享会议程公布','本次分享会将分为三个部分：新特性介绍、实战演示、Q&A环节，欢迎大家踊跃提问。','activity',1,0,'published',3,'2025-11-18 16:00:00',NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(6,'年度总结大会筹备','年度总结大会筹备中，待进一步通知','system',NULL,0,'draft',1,NULL,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0);
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club`
--

DROP TABLE IF EXISTS `club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '社团ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社团名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '社团简介',
  `logo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '社团图标URL',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'normal' COMMENT '社团状态：pending-审核中, normal-正常, disabled-已禁用',
  `create_user` bigint NOT NULL COMMENT '创建者用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`,`is_deleted`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_create_user_id` (`create_user`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  CONSTRAINT `fk_club_create_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='社团表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club`
--

LOCK TABLES `club` WRITE;
/*!40000 ALTER TABLE `club` DISABLE KEYS */;
INSERT INTO `club` VALUES (1,'编程协会','专注于编程技术交流与学习，定期举办编程比赛和技术分享会','https://example.com/logo/programming.png','normal',3,'2025-11-17 22:38:30','2025-11-18 11:28:17',0),(2,'篮球社','热爱篮球的同学聚集地，每周组织篮球比赛和训练','https://example.com/logo/basketball.png','normal',3,'2025-11-17 22:38:30','2025-11-18 11:28:17',0),(3,'摄影社','记录校园美好瞬间，学习摄影技巧，定期外拍活动','https://example.com/logo/photo.png','pending',2,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(4,'音乐社','音乐爱好者的天堂，涵盖声乐、器乐等多个方向','https://example.com/logo/music.png','normal',3,'2025-11-17 22:38:30','2025-11-18 11:28:17',0),(5,'志愿者协会','组织公益活动，服务社会，传递温暖','https://example.com/logo/volunteer.png','disabled',3,'2025-11-17 22:38:30','2025-11-17 22:38:30',0),(6,'die π乐队','Die π（死亡圆周率）乐队是中国21世纪乐坛中最具代表性的独立乐队之一。 他们以冷冽的电子噪音与诗性歌词构筑起那个时代青年共同的精神废墟。','https://voyager0587.oss-cn-guangzhou.aliyuncs.com/%E7%AC%94%E8%AE%B0%E5%9B%BE%E7%89%87/Downloads/die.jpg','normal',1,'2025-11-18 19:15:31','2025-11-18 19:19:04',1);
/*!40000 ALTER TABLE `club` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club_application`
--

DROP TABLE IF EXISTS `club_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club_application` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `club_id` bigint NOT NULL COMMENT '社团ID',
  `user_id` bigint NOT NULL COMMENT '申请用户ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '申请状态：pending-待审核, approved-已通过, rejected-已拒绝',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '申请理由',
  `review_note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '审核备注（管理员填写）',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `reviewer_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_club_id` (`club_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_reviewer_id` (`reviewer_id`) USING BTREE,
  CONSTRAINT `fk_club_application_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_club_application_reviewer` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_club_application_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='社团申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club_application`
--

LOCK TABLES `club_application` WRITE;
/*!40000 ALTER TABLE `club_application` DISABLE KEYS */;
INSERT INTO `club_application` VALUES (1,1,2,'approved','我热爱编程，希望加入编程协会一起提升技术。','申请通过，欢迎加入编程协会。','2024-09-06 10:00:00',3,'2024-09-02 09:00:00','2024-09-06 10:00:00',0),(2,3,1,'pending','希望了解摄影知识并参与校园摄影活动。',NULL,NULL,NULL,'2024-10-02 09:00:00','2024-10-02 09:00:00',0),(3,2,4,'rejected','我非常喜欢打篮球，想加入篮球社一起训练。','当前成员较多，暂不再招新，可稍后再申请。','2025-11-10 15:30:00',3,'2025-11-09 14:00:00','2025-11-10 15:30:00',0),(4,2,2,'pending','我是练习时长两年半的练习生~',NULL,NULL,NULL,'2025-11-18 16:24:50','2025-11-18 16:24:50',0);
/*!40000 ALTER TABLE `club_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club_member`
--

DROP TABLE IF EXISTS `club_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '成员关系ID',
  `club_id` bigint NOT NULL COMMENT '社团ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'member' COMMENT '成员角色：member-普通成员, leader-社长/负责人',
  `join_time` datetime DEFAULT NULL COMMENT '加入时间',
  `memo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_club_user` (`club_id`,`user_id`,`is_deleted`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_role` (`role`) USING BTREE,
  KEY `idx_join_time` (`join_time`) USING BTREE,
  CONSTRAINT `fk_club_member_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_club_member_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='社团成员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club_member`
--

LOCK TABLES `club_member` WRITE;
/*!40000 ALTER TABLE `club_member` DISABLE KEYS */;
INSERT INTO `club_member` VALUES (1,1,3,'leader','2024-09-01 09:00:00','编程协会创建者兼社长','2024-09-01 09:00:00','2024-09-01 09:00:00',0),(2,1,2,'member','2024-09-06 10:00:00','通过申请加入编程协会','2024-09-06 10:00:00','2024-09-06 10:00:00',0),(3,2,3,'leader','2024-09-10 10:00:00','篮球社创建者兼社长','2024-09-10 10:00:00','2024-09-10 10:00:00',0),(4,2,5,'member','2025-11-01 16:00:00','热爱篮球的成员','2025-11-01 16:00:00','2025-11-01 16:00:00',0),(5,4,3,'leader','2024-09-15 15:00:00','音乐社创建者兼负责人','2024-09-15 15:00:00','2024-09-15 15:00:00',0),(6,3,2,'leader','2024-10-01 10:00:00','摄影社创建者','2024-10-01 10:00:00','2024-10-01 10:00:00',0),(7,6,1,'leader','2025-11-18 19:15:31',NULL,'2025-11-18 19:15:31','2025-11-18 19:19:03',1),(8,2,4,'member','2025-11-18 19:24:40',NULL,'2025-11-18 19:24:40','2025-11-18 19:24:40',0);
/*!40000 ALTER TABLE `club_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `user_id` bigint NOT NULL COMMENT '接收用户ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知内容',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知类型：system-系统通知, audit-审核消息, activity-活动提醒, club-社团通知',
  `related_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联类型：club, activity, announcement等',
  `related_id` bigint DEFAULT NULL COMMENT '关联ID',
  `read_flag` tinyint NOT NULL DEFAULT '0' COMMENT '阅读标记：0-未读, 1-已读',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  `priority` int DEFAULT '0' COMMENT '优先级（0-普通，1-重要，2-紧急）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_type` (`type`) USING BTREE,
  KEY `idx_read_flag` (`read_flag`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_related` (`related_type`,`related_id`) USING BTREE,
  CONSTRAINT `fk_notification_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='通知表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,2,'欢迎加入平台','欢迎你注册学校社团管理平台！快去浏览感兴趣的社团和活动吧。','system',NULL,NULL,1,'2024-09-02 09:00:00','2025-11-17 22:38:30','2025-11-17 22:38:30',0,0),(2,2,'加入编程协会申请已通过','恭喜！你加入编程协会的申请已通过，现在你可以参与社团的各项活动了。','audit','club',1,1,'2024-09-06 10:00:00','2025-11-17 22:38:30','2025-11-17 22:38:30',0,0),(3,2,'Spring Boot分享会报名成功','你已成功报名\"Spring Boot 3.4 新特性分享会\"，活动时间：2025-11-25 14:00，地点：教学楼A-301','activity','activity',1,1,'2025-12-12 16:23:11','2025-11-17 22:38:30','2025-12-12 16:26:18',1,2),(4,2,'ACM算法竞赛即将开始','你报名的\"ACM算法竞赛月赛\"将于2025-11-30 19:00开始，请提前做好准备。','activity','activity',2,0,NULL,'2025-11-17 22:38:30','2025-12-12 15:50:12',0,2),(5,1,'欢迎加入平台','欢迎你注册学校社团管理平台！快去浏览感兴趣的社团和活动吧。','system',NULL,NULL,1,'2024-09-01 10:00:00','2025-11-17 22:38:30','2025-11-17 22:38:30',0,0),(6,1,'加入摄影社申请待审核','你加入摄影社的申请正在审核中，请耐心等待。','audit','club',3,1,'2024-10-02 09:00:00','2025-11-17 22:38:30','2025-11-17 22:38:30',0,0),(7,1,'编程协会有新公告','编程协会发布了新公告，快去查看吧！','club','announcement',3,0,NULL,'2025-11-17 22:38:30','2025-11-17 22:38:30',0,0),(8,1,'迎新音乐会报名成功','你已成功报名\"迎新音乐会\"，活动时间：2025-12-05 19:00，地点：大礼堂','activity','activity',7,0,NULL,'2025-11-17 22:38:30','2025-12-12 15:50:12',0,1),(9,3,'你创建的社团已通过审核','你创建的\"编程协会\"已通过审核，现在可以正常运营了。','audit','club',1,1,'2024-09-02 11:00:00','2025-11-17 22:38:30','2025-11-17 22:38:30',0,0),(10,3,'有新的社团加入申请','用户\"test_user\"申请加入你管理的\"编程协会\"，请及时处理。','club','club_member',3,0,NULL,'2025-11-17 22:38:30','2025-12-12 15:50:12',0,2),(11,1,'新的系统通知','这是一条测试系统通知','system',NULL,NULL,0,NULL,'2025-12-12 17:38:52','2025-12-12 17:38:52',0,1),(12,2,'新的系统通知','这是一条测试系统通知','system',NULL,NULL,1,'2025-12-13 17:36:20','2025-12-12 17:38:52','2025-12-13 17:37:31',1,1),(13,3,'新的系统通知','这是一条测试系统通知','system',NULL,NULL,0,NULL,'2025-12-12 17:38:52','2025-12-12 17:38:52',0,1),(14,1,'新的系统通知','这是一条测试系统通知2','system',NULL,NULL,0,NULL,'2025-12-12 18:32:11','2025-12-12 18:32:11',0,1),(15,2,'新的系统通知','这是一条测试系统通知2','system',NULL,NULL,0,NULL,'2025-12-12 18:32:11','2025-12-12 18:32:11',0,1),(16,3,'新的系统通知','这是一条测试系统通知2','system',NULL,NULL,0,NULL,'2025-12-12 18:32:11','2025-12-12 18:32:11',0,1),(17,1,'新的系统通知3','这是一条测试系统通知3','system',NULL,NULL,0,NULL,'2025-12-12 18:38:41','2025-12-12 18:38:41',0,1),(18,1,'新的俱乐部通知','这是一条测试俱乐部通知','system',NULL,NULL,0,NULL,'2025-12-12 18:39:53','2025-12-12 18:39:53',0,1),(19,1,'新的俱乐部通知','这是一条测试俱乐部通知','system',NULL,NULL,0,NULL,'2025-12-12 18:40:01','2025-12-12 18:40:01',0,1),(20,1,'新的俱乐部通知','这是一条测试俱乐部通知','club',NULL,NULL,0,NULL,'2025-12-13 17:44:07','2025-12-13 17:44:07',0,1);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码（加密）',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实姓名',
  `student_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user-普通用户, club_admin-社团管理员, system_admin-系统管理员',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '账户状态：0-禁用，1-正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`) USING BTREE,
  UNIQUE KEY `uk_student_id` (`student_id`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`) USING BTREE,
  KEY `idx_role` (`role`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','f3e21b99afc490d9441309d9768ed1a7','系统管理员','ADMIN001','admin@campus.com','13800000001',NULL,'system_admin',1,'2025-11-17 21:23:45','2025-11-17 21:55:38',0),(2,'test_user','f3e21b99afc490d9441309d9768ed1a7','测试用户','20230001','test@campus.com','13800000002',NULL,'user',1,'2025-11-17 21:23:45','2025-12-12 14:31:08',0),(3,'club_admin','f3e21b99afc490d9441309d9768ed1a7','社团管理员','20230002','club@campus.com','13800000003',NULL,'club_admin',1,'2025-11-17 21:23:45','2025-11-17 21:55:38',0),(4,'zhangsan','f3e21b99afc490d9441309d9768ed1a7','张三丰','20240001','newemail@campus.com','13900139000','https://example.com/avatar.jpg','user',1,'2025-11-17 21:55:24','2025-11-17 21:55:24',0),(5,'daipai','f3e21b99afc490d9441309d9768ed1a7','带派','20240002','daipai@campus.com','13800148000',NULL,'user',1,'2025-11-17 22:04:54','2025-11-17 22:04:54',0),(6,'test_user_new','f3e21b99afc490d9441309d9768ed1a7','?????','20230005','test_new@campus.com','13800000005',NULL,'user',1,'2025-12-12 14:29:09','2025-12-12 14:29:09',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notification_setting`
--

DROP TABLE IF EXISTS `user_notification_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notification_setting` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设置ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `email_enabled` tinyint NOT NULL DEFAULT '1' COMMENT '是否启用邮件通知：0-禁用，1-启用',
  `in_app_enabled` tinyint NOT NULL DEFAULT '1' COMMENT '是否启用站内消息：0-禁用，1-启用',
  `audit_notification` tinyint NOT NULL DEFAULT '1' COMMENT '审核消息通知：0-禁用，1-启用',
  `activity_notification` tinyint NOT NULL DEFAULT '1' COMMENT '活动提醒通知：0-禁用，1-启用',
  `club_notification` tinyint NOT NULL DEFAULT '1' COMMENT '社团通知：0-禁用，1-启用',
  `system_notification` tinyint NOT NULL DEFAULT '1' COMMENT '系统通知：0-禁用，1-启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  CONSTRAINT `fk_setting_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户通知偏好设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notification_setting`
--

LOCK TABLES `user_notification_setting` WRITE;
/*!40000 ALTER TABLE `user_notification_setting` DISABLE KEYS */;
INSERT INTO `user_notification_setting` VALUES (1,2,1,1,1,0,0,1,'2025-12-12 16:28:08','2025-12-12 16:28:08',0),(2,1,1,1,1,1,1,1,'2025-12-12 17:38:53','2025-12-12 17:38:53',0),(3,3,1,1,1,1,1,1,'2025-12-12 17:38:53','2025-12-12 17:38:53',0);
/*!40000 ALTER TABLE `user_notification_setting` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-13 18:28:16
