# 项目运行和API测试指南

## 📋 目录

- [环境准备](#环境准备)
- [数据库配置](#数据库配置)
- [项目启动](#项目启动)
- [API测试工具](#api测试工具)
- [通知API测试](#通知api测试)
- [常见问题](#常见问题)

---

## 环境准备

### 1. 检查环境

确保已安装以下软件：

```bash
# 检查 Java 版本（需要 JDK 21+）
java -version

# 检查 Maven 版本（需要 3.8+）
mvn -version

# 检查 MySQL 版本（需要 8.0+）
mysql --version
```

### 2. 安装缺失的软件

- **JDK 21**: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) 或 [OpenJDK](https://openjdk.org/)
- **Maven 3.8+**: [Apache Maven](https://maven.apache.org/download.cgi)
- **MySQL 8.0+**: [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

---

## 数据库配置

### 1. 创建数据库

```bash
# 登录 MySQL
mysql -u root -p

# 创建数据库
CREATE DATABASE campus_club CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 使用数据库
USE campus_club;
```

### 2. 执行数据库脚本

**方式一：使用 MySQL 命令行**

```bash
# 在项目根目录执行
mysql -u root -p campus_club < src/main/resources/db/migration/campus_club.sql

# 执行通知模块迁移脚本
mysql -u root -p campus_club < src/main/resources/db/migration/V4__Add_Notification_Features.sql
```

**方式二：在 MySQL 客户端中执行**

```sql
-- 1. 先执行完整数据库脚本
SOURCE E:/bangong/2025软件开发/后端/campus-club-manager-backend-master/src/main/resources/db/migration/campus_club.sql;

-- 2. 再执行通知模块迁移脚本
SOURCE E:/bangong/2025软件开发/后端/campus-club-manager-backend-master/src/main/resources/db/migration/V4__Add_Notification_Features.sql;
```

**注意**：请将路径替换为你的实际项目路径。

### 3. 验证数据库

```sql
-- 检查表是否创建成功
SHOW TABLES;

-- 应该看到以下表：
-- user, club, club_member, club_application, activity, activity_signup, notification, user_notification_setting

-- 检查 notification 表是否有 priority 字段
DESC notification;

-- 检查 user_notification_setting 表是否存在
DESC user_notification_setting;
```

### 4. 查看测试数据

```sql
-- 查看测试用户
SELECT id, username, role, email FROM user WHERE is_deleted = 0;

-- 应该看到：
-- admin (system_admin)
-- test_user (user)
-- club_admin (club_admin)
```

---

## 项目启动

### 1. 修改配置文件

编辑 `src/main/resources/application.yaml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_club?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
    username: root        # 修改为你的 MySQL 用户名
    password: root        # 修改为你的 MySQL 密码
```

**邮件配置（可选，测试站内消息时不需要）**：

如果需要测试邮件功能，配置邮件服务器：

```yaml
spring:
  mail:
    host: smtp.qq.com
    port: 587
    username: ${MAIL_USERNAME:your-email@qq.com}
    password: ${MAIL_PASSWORD:your-auth-code}
    from: ${MAIL_FROM:noreply@campus-club.com}
```

或者使用环境变量：

```bash
# Windows PowerShell
$env:MAIL_USERNAME="your-email@qq.com"
$env:MAIL_PASSWORD="your-auth-code"
$env:MAIL_FROM="noreply@campus-club.com"

# Windows CMD
set MAIL_USERNAME=your-email@qq.com
set MAIL_PASSWORD=your-auth-code
set MAIL_FROM=noreply@campus-club.com

# Linux/Mac
export MAIL_USERNAME=your-email@qq.com
export MAIL_PASSWORD=your-auth-code
export MAIL_FROM=noreply@campus-club.com
```

### 2. 启动项目

**方式一：使用 IDE（推荐）**

1. 使用 IntelliJ IDEA 或 Eclipse 打开项目
2. 找到 `CampusClubManagerBackendApplication.java`
3. 右键 → Run 'CampusClubManagerBackendApplication'
4. 等待启动完成，看到日志输出

**方式二：使用 Maven 命令**

```bash
# 在项目根目录执行
mvn clean package -DskipTests

# 启动项目
java -jar target/campus-club-manager-backend-0.0.1-SNAPSHOT.jar
```

**方式三：使用 Maven Spring Boot 插件**

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### 3. 验证启动成功

启动成功后，你应该看到类似以下日志：

```
---------------------------------------------------------
	应用程序"campus-club-manager-backend"正在运行中......
	接口文档访问 URL:
	本地: 		http://localhost:8080/api/doc.html
	外部: 	http://192.168.x.x:8080/api/doc.html
	配置文件: 	[dev]
---------------------------------------------------------
```

访问 Swagger 文档：http://localhost:8080/api/doc.html

---

## API测试工具

### 工具选择

1. **Swagger UI**（推荐）- 浏览器访问 http://localhost:8080/api/doc.html
2. **Postman** - 图形化界面，功能强大
3. **curl** - 命令行工具，适合脚本测试
4. **VS Code REST Client** - 在代码中写测试用例

### 获取认证 Token

所有需要登录的接口都需要在请求头中携带 Token。

**测试账号**（密码均为 `123456`）：
- `admin` - 系统管理员
- `test_user` - 普通用户
- `club_admin` - 社团管理员

**登录获取 Token**：

```bash
# 使用 curl
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"test_user\",\"password\":\"123456\"}"

# 响应示例
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "your-token-here",
    "userInfo": {
      "id": 2,
      "username": "test_user",
      ...
    }
  }
}
```

**保存 Token**（后续请求需要使用）：

```bash
# Linux/Mac
export TOKEN="your-token-here"

# Windows PowerShell
$env:TOKEN="your-token-here"

# Windows CMD
set TOKEN=your-token-here
```

---

## 通知API测试

### 测试流程概览

1. ✅ 登录获取 Token
2. ✅ 查询通知列表（应该看到测试数据）
3. ✅ 获取未读数量
4. ✅ 标记通知已读
5. ✅ 获取通知设置
6. ✅ 更新通知设置
7. ✅ 发布测试通知（通过业务接口）
8. ✅ 验证通知接收

### 1. 查询通知列表

**接口**：`GET /api/notification/list`

```bash
# 使用 curl
curl -X GET "http://localhost:8080/api/notification/list?pageNum=1&pageSize=10" \
  -H "Authorization: $TOKEN"

# 带筛选条件
curl -X GET "http://localhost:8080/api/notification/list?pageNum=1&pageSize=10&type=audit&readFlag=false" \
  -H "Authorization: $TOKEN"
```

**使用 Swagger UI**：
1. 访问 http://localhost:8080/api/doc.html
2. 找到 "通知管理" 标签
3. 点击 "分页查询我的通知"
4. 点击 "Try it out"
5. 填写参数（可选）
6. 点击 "Execute"

**预期响应**：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "欢迎加入平台",
        "content": "欢迎你注册学校社团管理平台！",
        "type": "system",
        "relatedType": null,
        "relatedId": null,
        "priority": 1,
        "read": true,
        "readTime": "2024-09-02 09:00:00",
        "createTime": "2024-09-02 09:00:00"
      }
    ],
    "total": 10,
    "size": 10,
    "current": 1,
    "pages": 1
  }
}
```

### 2. 获取未读数量

**接口**：`GET /api/notification/unread-count`

```bash
curl -X GET http://localhost:8080/api/notification/unread-count \
  -H "Authorization: $TOKEN"
```

**预期响应**：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": 5
}
```

### 3. 标记通知已读

**接口**：`PUT /api/notification/mark-read`

**标记单条已读**：

```bash
curl -X PUT http://localhost:8080/api/notification/mark-read \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"notificationId\":1,\"markAll\":false}"
```

**标记全部已读**：

```bash
curl -X PUT http://localhost:8080/api/notification/mark-read \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"markAll\":true}"
```

**预期响应**：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

### 4. 删除通知

**接口**：`DELETE /api/notification/{id}`

```bash
curl -X DELETE http://localhost:8080/api/notification/1 \
  -H "Authorization: $TOKEN"
```

### 5. 获取通知设置

**接口**：`GET /api/notification/settings`

```bash
curl -X GET http://localhost:8080/api/notification/settings \
  -H "Authorization: $TOKEN"
```

**预期响应**：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "emailEnabled": true,
    "inAppEnabled": true,
    "auditNotification": true,
    "activityNotification": true,
    "clubNotification": true,
    "systemNotification": true
  }
}
```

### 6. 更新通知设置

**接口**：`PUT /api/notification/settings`

```bash
curl -X PUT http://localhost:8080/api/notification/settings \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"emailEnabled\": true,
    \"inAppEnabled\": true,
    \"auditNotification\": true,
    \"activityNotification\": false,
    \"clubNotification\": true,
    \"systemNotification\": true
  }"
```

### 7. 发布测试通知（通过业务接口）

由于通知是通过事件驱动的，我们需要通过业务操作来触发通知。

**方式一：通过社团申请审核触发**

```bash
# 1. 先申请加入社团（使用 test_user）
curl -X POST http://localhost:8080/api/club/apply \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"clubId\":1,\"reason\":\"我想加入这个社团\"}"

# 2. 使用 admin 或 club_admin 审核申请
# 先登录 club_admin
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"club_admin\",\"password\":\"123456\"}"

# 保存新的 token
export ADMIN_TOKEN="new-token-here"

# 审核申请（通过）
curl -X POST http://localhost:8080/api/club/management/1/applications/review \
  -H "Authorization: $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"applicationId\": 1,
    \"status\": \"approved\",
    \"reviewNote\": \"欢迎加入\"
  }"

# 3. 切换回 test_user，查询通知列表，应该看到审核结果通知
curl -X GET http://localhost:8080/api/notification/list \
  -H "Authorization: $TOKEN"
```

**方式二：直接调用通知服务（需要修改代码）**

如果需要直接测试通知功能，可以创建一个测试接口：

```java
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class NotificationTestController {
    private final NotificationEventPublisher notificationEventPublisher;
    
    @PostMapping("/notification")
    @SaCheckRole("system_admin")
    public Result<Void> testNotification(@RequestParam Long userId) {
        notificationEventPublisher.publishNotification(
            userId,
            "测试通知",
            "这是一条测试通知",
            NotificationType.SYSTEM,
            null,
            null,
            NotificationPriority.NORMAL
        );
        return Result.success("通知已发送", null);
    }
}
```

---

## Postman 测试集合

### 导入 Postman Collection

创建 `postman/通知API测试.postman_collection.json`：

```json
{
  "info": {
    "name": "通知API测试",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080/api",
      "type": "string"
    },
    {
      "key": "token",
      "value": "",
      "type": "string"
    }
  ],
  "item": [
    {
      "name": "1. 登录",
      "request": {
        "method": "POST",
        "header": [{"key": "Content-Type", "value": "application/json"}],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"username\": \"test_user\",\n  \"password\": \"123456\"\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/user/login",
          "host": ["{{baseUrl}}"],
          "path": ["user", "login"]
        }
      },
      "event": [
        {
          "listen": "test",
          "script": {
            "exec": [
              "if (pm.response.code === 200) {",
              "    var jsonData = pm.response.json();",
              "    pm.collectionVariables.set('token', jsonData.data.token);",
              "}"
            ]
          }
        }
      ]
    },
    {
      "name": "2. 查询通知列表",
      "request": {
        "method": "GET",
        "header": [
          {"key": "Authorization", "value": "{{token}}"}
        ],
        "url": {
          "raw": "{{baseUrl}}/notification/list?pageNum=1&pageSize=10",
          "host": ["{{baseUrl}}"],
          "path": ["notification", "list"],
          "query": [
            {"key": "pageNum", "value": "1"},
            {"key": "pageSize", "value": "10"}
          ]
        }
      }
    },
    {
      "name": "3. 获取未读数量",
      "request": {
        "method": "GET",
        "header": [
          {"key": "Authorization", "value": "{{token}}"}
        ],
        "url": {
          "raw": "{{baseUrl}}/notification/unread-count",
          "host": ["{{baseUrl}}"],
          "path": ["notification", "unread-count"]
        }
      }
    },
    {
      "name": "4. 标记全部已读",
      "request": {
        "method": "PUT",
        "header": [
          {"key": "Authorization", "value": "{{token}}"},
          {"key": "Content-Type", "value": "application/json"}
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"markAll\": true\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/notification/mark-read",
          "host": ["{{baseUrl}}"],
          "path": ["notification", "mark-read"]
        }
      }
    },
    {
      "name": "5. 获取通知设置",
      "request": {
        "method": "GET",
        "header": [
          {"key": "Authorization", "value": "{{token}}"}
        ],
        "url": {
          "raw": "{{baseUrl}}/notification/settings",
          "host": ["{{baseUrl}}"],
          "path": ["notification", "settings"]
        }
      }
    },
    {
      "name": "6. 更新通知设置",
      "request": {
        "method": "PUT",
        "header": [
          {"key": "Authorization", "value": "{{token}}"},
          {"key": "Content-Type", "value": "application/json"}
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"emailEnabled\": true,\n  \"inAppEnabled\": true,\n  \"auditNotification\": true,\n  \"activityNotification\": true,\n  \"clubNotification\": true,\n  \"systemNotification\": true\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/notification/settings",
          "host": ["{{baseUrl}}"],
          "path": ["notification", "settings"]
        }
      }
    }
  ]
}
```

### 使用 Postman

1. 打开 Postman
2. 点击 Import → 选择上面的 JSON 文件
3. 设置环境变量 `baseUrl` = `http://localhost:8080/api`
4. 按顺序执行请求（登录会自动保存 token）

---

## VS Code REST Client 测试

创建 `test/notification.http`：

```http
### 变量定义
@baseUrl = http://localhost:8080/api
@token = your-token-here

### 1. 登录获取 Token
POST {{baseUrl}}/user/login
Content-Type: application/json

{
  "username": "test_user",
  "password": "123456"
}

### 2. 查询通知列表
GET {{baseUrl}}/notification/list?pageNum=1&pageSize=10
Authorization: {{token}}

### 3. 获取未读数量
GET {{baseUrl}}/notification/unread-count
Authorization: {{token}}

### 4. 标记单条已读
PUT {{baseUrl}}/notification/mark-read
Authorization: {{token}}
Content-Type: application/json

{
  "notificationId": 1,
  "markAll": false
}

### 5. 标记全部已读
PUT {{baseUrl}}/notification/mark-read
Authorization: {{token}}
Content-Type: application/json

{
  "markAll": true
}

### 6. 获取通知设置
GET {{baseUrl}}/notification/settings
Authorization: {{token}}

### 7. 更新通知设置
PUT {{baseUrl}}/notification/settings
Authorization: {{token}}
Content-Type: application/json

{
  "emailEnabled": true,
  "inAppEnabled": true,
  "auditNotification": true,
  "activityNotification": true,
  "clubNotification": true,
  "systemNotification": true
}

### 8. 删除通知
DELETE {{baseUrl}}/notification/1
Authorization: {{token}}
```

---

## 常见问题

### 1. 数据库连接失败

**错误**：`Communications link failure`

**解决方案**：
- 检查 MySQL 服务是否启动
- 检查 `application.yaml` 中的数据库配置
- 检查防火墙设置

### 2. Token 无效

**错误**：`401 未登录或登录已过期`

**解决方案**：
- 重新登录获取新的 Token
- 检查请求头是否正确：`Authorization: your-token`
- Token 有效期为 30 天

### 3. 权限不足

**错误**：`403 权限不足`

**解决方案**：
- 使用正确的账号登录（不同接口需要不同角色）
- 检查用户角色是否正确

### 4. 邮件发送失败

**错误**：邮件发送失败

**解决方案**：
- 检查邮件配置是否正确
- QQ 邮箱需要使用授权码，不是登录密码
- 检查网络连接和防火墙设置
- 站内消息不受影响，可以继续测试

### 5. 通知表不存在

**错误**：`Table 'campus_club.notification' doesn't exist`

**解决方案**：
- 执行数据库迁移脚本 `V4__Add_Notification_Features.sql`
- 检查数据库名称是否正确

### 6. 端口被占用

**错误**：`Port 8080 is already in use`

**解决方案**：
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

或修改 `application.yaml` 中的端口号。

---

## 测试检查清单

- [ ] 数据库创建成功
- [ ] 数据库脚本执行成功
- [ ] 项目启动成功
- [ ] Swagger 文档可以访问
- [ ] 登录接口正常
- [ ] 查询通知列表正常
- [ ] 获取未读数量正常
- [ ] 标记已读正常
- [ ] 获取通知设置正常
- [ ] 更新通知设置正常
- [ ] 删除通知正常
- [ ] 通过业务操作触发通知正常

---

## 下一步

完成基础测试后，可以：

1. **集成到现有业务** - 在社团申请、活动审核等业务中添加通知
2. **测试邮件功能** - 配置邮件服务器，测试邮件通知
3. **性能测试** - 测试批量通知发送性能
4. **扩展功能** - 添加 WebSocket 实时推送等

如有问题，请查看日志或参考项目文档。


