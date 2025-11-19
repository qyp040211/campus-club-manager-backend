# 校园社团管理平台 - 后端项目

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MyBatis Plus](https://img.shields.io/badge/MyBatis%20Plus-3.5.9-blue.svg)](https://baomidou.com/)
[![Sa-Token](https://img.shields.io/badge/Sa--Token-1.44.0-orange.svg)](https://sa-token.cc/)
[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📑 目录

- [项目简介](#项目简介)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [核心功能](#核心功能)
- [项目结构](#项目结构)
- [API 路由总览](#api-路由总览)
- [API 测试示例](#api-测试示例)
- [开发规范](#开发规范)
- [开发规划](#开发规划)
- [常见问题](#常见问题)

## 项目简介

基于 Spring Boot 3 + MyBatis Plus + Sa-Token 构建的**校园社团管理系统后端**，实现了完整的用户管理、社团管理、活动管理等核心功能，支持基于角色的权限控制（RBAC）。

**核心特性：**
- ✨ 完整的 RBAC 权限体系（普通用户、社团负责人、系统管理员）
- 🏛️ 社团全生命周期管理（创建、审核、成员管理）
- 🎯 活动发布、报名、签到完整流程
- 🔐 基于 Sa-Token 的认证授权
- 📊 分页查询、关键词搜索、状态筛选
- 📝 完善的 Swagger API 文档
- ☁️ 集成阿里云 OSS 和 Spring AI

## 技术栈

- **核心框架**: Spring Boot 3.4.4
- **ORM 框架**: MyBatis Plus 3.5.9
- **权限框架**: Sa-Token 1.44.0
- **数据库**: MySQL 8.0+
- **文档工具**: SpringDoc OpenAPI (Swagger)
- **对象存储**: Aliyun OSS 3.18.1
- **AI 集成**: Spring AI 1.0.3 + Alibaba Cloud AI 1.0.0.2
- **构建工具**: Maven 3.8+
- **JDK 版本**: Java 21

## 快速开始

### 1. 环境准备

- 安装 JDK 21+
- 安装 Maven 3.8+
- 安装 MySQL 8.0+

### 2. 数据库初始化

```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE campus_club CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入初始化脚本（按顺序执行）
USE campus_club;

SOURCE src/main/resources/db/migration/campus_club.sql;
```

### 3. 修改配置

编辑 `src/main/resources/application.yaml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_club
    username: root
    password: 你的密码
```

### 4. 启动项目

```bash
# 方式一：IDE 启动
# 直接运行 CampusClubManagerBackendApplication.main()

# 方式二：命令行启动
mvn clean package -DskipTests
java -jar target/campus-club-manager-backend-0.0.1-SNAPSHOT.jar
```

### 5. 访问接口文档

启动成功后访问：[http://localhost:8080/api/doc.html](http://localhost:8080/api/doc.html)

## 核心功能

### ✅ 已实现功能

#### 1. RBAC 权限系统

- 三种角色：普通用户（user）、社团管理员（club_admin）、系统管理员（system_admin）
- 基于注解的权限验证：`@SaCheckRole`、`@SaCheckLogin`
- 自动化的登录状态管理
- Token 自动生成与验证
- 分层权限控制（普通用户、社团负责人、系统管理员）

#### 2. 用户管理模块

| 接口 | 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|------|
| 用户注册 | POST | `/api/user/register` | 公开 | 新用户自助注册 |
| 用户登录 | POST | `/api/user/login` | 公开 | 获取访问令牌 |
| 获取用户信息 | GET | `/api/user/info` | user | 查询当前用户信息 |
| 更新用户信息 | POST | `/api/user/update` | user | 修改个人资料 |

#### 3. 社团管理模块

**学生端功能：**
- ✅ 分页查询社团列表（支持关键词搜索）
- ✅ 查询社团详情
- ✅ 申请加入社团
- ✅ 查看我加入的社团
- ✅ 查看社团成员列表
- ✅ 查看我的申请记录

**社团负责人功能：**
- ✅ 审核社团加入申请
- ✅ 更新社团基本信息
- ✅ 查看待审核申请列表

**系统管理员功能：**
- ✅ 创建社团
- ✅ 更新社团信息
- ✅ 删除社团
- ✅ 审核社团申请
- ✅ 设置/取消社团负责人

#### 4. 活动管理模块

**学生端功能：**
- ✅ 浏览活动列表（支持关键词和社团筛选）
- ✅ 查看活动详情
- ✅ 报名参加活动
- ✅ 取消活动报名
- ✅ 查看我的报名记录

**社团负责人功能：**
- ✅ 创建活动（需审核）
- ✅ 更新活动信息
- ✅ 取消活动
- ✅ 查看活动报名列表
- ✅ 活动签到/标记缺席

**系统管理员功能：**
- ✅ 查看所有活动
- ✅ 审核活动（通过/拒绝）
- ✅ 删除活动

#### 5. 数据模型

**核心实体：**
- `User` - 用户实体（用户名、密码、学号、邮箱、角色等）
- `Club` - 社团实体（名称、简介、负责人、状态等）
- `ClubMember` - 社团成员关系（用户、社团、角色等）
- `ClubApplication` - 社团加入申请（用户、社团、申请理由、审核状态等）
- `Activity` - 活动实体（名称、时间、地点、容量、状态等）
- `ActivitySignup` - 活动报名记录（用户、活动、签到状态等）

**枚举类型：**
- `UserRole` - 用户角色（user、club_admin、system_admin）
- `ClubStatus` - 社团状态
- `ApplicationStatus` - 申请状态（pending、approved、rejected）
- `MemberRole` - 成员角色（member、leader）
- `ActivityStatus` - 活动状态（pending、approved、ongoing、completed、cancelled、rejected）
- `SignupStatus` - 报名状态（signed_up、attended、absent、cancelled）

#### 6. 测试账号

系统预置了三个测试账号（密码均为 `123456`）：

| 用户名 | 角色 | 学号 | 邮箱 |
|-------|------|------|------|
| admin | system_admin | ADMIN001 | admin@campus.com |
| test_user | user | 20230001 | test@campus.com |
| club_admin | club_admin | 20230002 | club@campus.com |

## 项目结构

```
src/main/java/com/club/campusclubmanager/
├── config/                    # 配置类（MyBatis Plus、Sa-Token）
├── controller/                # 控制器层
│   ├── UserController.java            # 用户管理（注册、登录、信息维护）
│   ├── ClubController.java            # 社团管理（学生端）
│   ├── ClubManagementController.java  # 社团管理（负责人端）
│   ├── AdminClubController.java       # 社团管理（系统管理员）
│   ├── ActivityController.java        # 活动管理（学生端）
│   ├── ClubActivityController.java    # 活动管理（社团负责人）
│   └── AdminActivityController.java   # 活动管理（系统管理员）
├── dto/                       # 数据传输对象（请求参数）
│   ├── LoginRequest.java              # 登录请求
│   ├── RegisterRequest.java           # 注册请求
│   ├── UpdateUserRequest.java         # 更新用户信息
│   ├── CreateClubRequest.java         # 创建社团
│   ├── UpdateClubRequest.java         # 更新社团信息
│   ├── ApplyJoinClubRequest.java      # 申请加入社团
│   ├── ReviewApplicationRequest.java  # 审核申请
│   ├── CreateActivityRequest.java     # 创建活动
│   ├── UpdateActivityRequest.java     # 更新活动
│   ├── ReviewActivityRequest.java     # 审核活动
│   └── CheckinRequest.java            # 签到请求
├── entity/                    # 实体类（数据库映射）
│   ├── User.java                      # 用户实体
│   ├── Club.java                      # 社团实体
│   ├── ClubMember.java                # 社团成员实体
│   ├── ClubApplication.java           # 社团申请实体
│   ├── Activity.java                  # 活动实体
│   └── ActivitySignup.java            # 活动报名实体
├── enums/                     # 枚举类
│   ├── UserRole.java                  # 用户角色
│   ├── ClubStatus.java                # 社团状态
│   ├── ApplicationStatus.java         # 申请状态
│   ├── MemberRole.java                # 成员角色
│   ├── ActivityStatus.java            # 活动状态
│   └── SignupStatus.java              # 报名状态
├── exception/                 # 异常处理
│   ├── BusinessException.java         # 业务异常
│   └── GlobalExceptionHandler.java    # 全局异常处理器
├── mapper/                    # MyBatis Mapper
│   ├── UserMapper.java
│   ├── ClubMapper.java
│   ├── ClubMemberMapper.java
│   ├── ClubApplicationMapper.java
│   ├── ActivityMapper.java
│   └── ActivitySignupMapper.java
├── service/                   # 服务层
│   ├── UserService.java
│   ├── ClubService.java
│   ├── ActivityService.java
│   └── impl/                          # 服务实现
│       ├── UserServiceImpl.java
│       ├── ClubServiceImpl.java
│       └── ActivityServiceImpl.java
├── vo/                        # 视图对象（响应数据）
│   ├── LoginResponse.java             # 登录响应
│   ├── UserInfoVO.java                # 用户信息
│   ├── ClubVO.java                    # 社团信息
│   ├── ClubDetailVO.java              # 社团详情
│   ├── ClubMemberVO.java              # 社团成员
│   ├── ClubApplicationVO.java         # 社团申请
│   ├── ActivityVO.java                # 活动信息
│   ├── ActivityDetailVO.java          # 活动详情
│   └── ActivitySignupVO.java          # 活动报名
└── common/                    # 公共类（统一响应）
    └── Result.java                    # 统一响应对象

src/main/resources/
├── application.yaml           # 配置文件
└── db/migration/             # 数据库脚本
    ├── V1__Create_User_Table.sql      # 用户表
    ├── V2__Create_Core_Tables.sql     # 社团和活动表
    ├── V3__Insert_Test_Data.sql       # 测试数据
    └── campus_club.sql                # 完整数据库脚本

docs/
├── RBAC权限系统使用指南.md    # 权限系统详细说明
├── 快速启动指南.md           # 项目启动教程
└── 项目架构说明.md           # 架构设计文档
```

## API 路由总览

### 用户管理（/api/user）

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 用户注册 | POST | `/api/user/register` | 公开 |
| 用户登录 | POST | `/api/user/login` | 公开 |
| 获取用户信息 | GET | `/api/user/info` | user |
| 更新用户信息 | POST | `/api/user/update` | user |

### 社团管理 - 学生端（/club）

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 分页查询社团列表 | GET | `/club/list` | 公开 |
| 查询社团详情 | GET | `/club/{id}` | 公开 |
| 查询我加入的社团 | GET | `/club/my` | user |
| 申请加入社团 | POST | `/club/apply` | user |
| 查询社团成员列表 | GET | `/club/{id}/members` | 公开 |
| 查询我的申请记录 | GET | `/club/my/applications` | user |

### 社团管理 - 负责人（/club/management）

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 查询待审核申请列表 | GET | `/club/management/{clubId}/applications/pending` | 负责人 |
| 审核社团申请 | POST | `/club/management/{clubId}/applications/review` | 负责人 |
| 更新社团信息 | PUT | `/club/management/{clubId}` | 负责人 |

### 社团管理 - 管理员（/admin/club）

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 创建社团 | POST | `/admin/club/create` | system_admin |
| 更新社团信息 | PUT | `/admin/club/update` | system_admin |
| 删除社团 | DELETE | `/admin/club/{id}` | system_admin |
| 查询待审核申请列表 | GET | `/admin/club/applications/pending` | system_admin |
| 审核社团申请 | POST | `/admin/club/applications/review` | system_admin |
| 设置社团负责人 | POST | `/admin/club/{clubId}/leader/{userId}` | system_admin |
| 取消社团负责人 | DELETE | `/admin/club/{clubId}/leader/{userId}` | system_admin |

### 活动管理 - 学生端（/activity）

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 浏览活动列表 | GET | `/activity/list` | 公开 |
| 查看活动详情 | GET | `/activity/{id}` | 公开 |
| 报名活动 | POST | `/activity/{id}/signup` | user |
| 取消报名 | DELETE | `/activity/{id}/signup` | user |
| 查看我的报名记录 | GET | `/activity/my-signups` | user |

### 活动管理 - 社团负责人（/club-admin/activity）

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 创建活动 | POST | `/club-admin/activity/create` | 负责人 |
| 更新活动 | PUT | `/club-admin/activity/{id}` | 负责人 |
| 取消活动 | PUT | `/club-admin/activity/{id}/cancel` | 负责人 |
| 查看活动报名列表 | GET | `/club-admin/activity/{id}/signups` | 负责人 |
| 活动签到/标记缺席 | POST | `/club-admin/activity/{id}/checkin` | 负责人 |

### 活动管理 - 系统管理员（/admin/activity）

| 接口 | 方法 | 路径 | 权限 |
|------|------|------|------|
| 查看所有活动 | GET | `/admin/activity/list` | system_admin |
| 审核活动 | PUT | `/admin/activity/{id}/review` | system_admin |
| 删除活动 | DELETE | `/admin/activity/{id}` | system_admin |

## API 测试示例

### 用户注册

```bash
curl -X POST http://localhost:8080/api/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "123456",
    "realName": "新用户",
    "studentId": "20240001",
    "email": "newuser@campus.com",
    "phone": "13800138000"
  }'
```

### 用户登录

```bash
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "password": "123456"
  }'
```

### 获取用户信息

```bash
curl -X GET http://localhost:8080/api/user/info \
  -H "Authorization: Bearer {token}"
```

### 查询社团列表

```bash
curl -X GET "http://localhost:8080/club/list?pageNum=1&pageSize=10&keyword=篮球"
```

### 申请加入社团

```bash
curl -X POST http://localhost:8080/club/apply \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "clubId": 1,
    "reason": "我热爱篮球运动，希望加入篮球社"
  }'
```

### 创建活动（社团负责人）

```bash
curl -X POST http://localhost:8080/club-admin/activity/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "clubId": 1,
    "activityName": "篮球友谊赛",
    "description": "与计算机社进行一场友谊赛",
    "location": "体育馆",
    "startTime": "2025-11-25 14:00:00",
    "endTime": "2025-11-25 16:00:00",
    "maxParticipants": 30
  }'
```

### 报名参加活动

```bash
curl -X POST http://localhost:8080/activity/1/signup \
  -H "Authorization: Bearer {token}"
```

## 文档导航

- 📖 [RBAC 权限系统使用指南](docs/RBAC权限系统使用指南.md) - 详细的权限体系说明
- 🚀 [快速启动指南](docs/快速启动指南.md) - 从零开始启动项目
- 🏗️ [项目架构说明](docs/项目架构说明.md) - 技术架构与设计模式
- 📝 [后端开发项目文档](docs/学校社团管理平台%20·%20后端开发项目文档.md) - 完整需求与设计

## 开发规范

### 统一响应格式

所有接口返回统一的 JSON 格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    // 业务数据
  }
}
```

### 异常处理

系统自动捕获并处理以下异常：

- `BusinessException` - 业务异常（返回 500）
- `NotLoginException` - 未登录异常（返回 401）
- `NotRoleException` - 角色不足异常（返回 403）
- `MethodArgumentNotValidException` - 参数校验失败（返回 400）

### 权限验证注解

```java
// 要求用户具有 user 角色
@SaCheckRole("user")

// 要求用户具有 club_admin 或 system_admin 角色（满足任一）
@SaCheckRole(value = {"club_admin", "system_admin"}, mode = SaMode.OR)

// 要求用户具有特定权限
@SaCheckPermission("club:create")
```

## 开发规划

### ✅ 已完成

- [x] **用户管理模块** - 注册、登录、信息维护
- [x] **社团管理模块** - 社团 CRUD、成员管理、申请审核
- [x] **活动管理模块** - 活动发布、报名、签到、审核
- [x] **权限系统** - 基于 Sa-Token 的 RBAC 权限模型
- [x] **分层权限控制** - 普通用户、社团负责人、系统管理员
- [x] **对象存储集成** - Aliyun OSS（依赖已集成）
- [x] **AI 能力集成** - Spring AI + Alibaba Cloud AI（依赖已集成）
- [x] **API 文档** - SpringDoc OpenAPI (Swagger)

### 🚧 进行中

- [ ] **消息通知模块** - 站内消息、邮件通知
- [ ] **OSS 功能实现** - 文件上传接口（头像、活动封面等）
- [ ] **AI 功能实现** - 智能推荐、内容审核等

### 📋 待开发

- [ ] **数据统计与分析** - 社团数据、活动数据可视化
- [ ] **Redis 缓存集成** - 提升性能、分布式会话
- [ ] **单元测试覆盖** - 核心业务逻辑测试
- [ ] **Excel 导入导出** - 成员名单、活动报名表
- [ ] **定时任务** - 活动状态自动更新、过期数据清理

## 常见问题

### 1. 如何添加新的角色？

在 [UserRole.java](src/main/java/com/club/campusclubmanager/enums/UserRole.java) 中添加新的枚举值即可。

### 2. 如何添加细粒度权限？

修改 `SaTokenConfig.getPermissionList()` 方法，从数据库查询用户权限列表。

### 3. Token 如何刷新？

当前 Token 有效期为 30 天，可以通过 `StpUtil.renewTimeout()` 方法刷新。

### 4. 如何切换到 Redis 存储 Token？

添加 `sa-token-redis-jackson` 依赖，配置 Redis 连接即可。

## 许可证

MIT License

## 联系方式

如有问题，请提交 Issue 或查看文档。

---

**项目状态**: 🚧 核心功能已完成，持续优化中

**最后更新**: 2025-11-19

**版本**: v0.1.0-beta
