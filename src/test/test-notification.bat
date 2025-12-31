@echo off
REM 通知API测试脚本 (Windows)
REM 使用方法: test-notification.bat

set BASE_URL=http://localhost:8080/api

echo ==========================================
echo 通知API测试脚本
echo ==========================================
echo.

REM 1. 登录获取 Token
echo 1. 登录获取 Token...
curl -s -X POST "%BASE_URL%/user/login" ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"test_user\",\"password\":\"123456\"}" > login_response.json

REM 提取 Token (需要安装 jq 或使用 PowerShell)
echo 登录成功！
echo.

REM 2. 查询通知列表
echo 2. 查询通知列表...
curl -s -X GET "%BASE_URL%/notification/list?pageNum=1&pageSize=10" ^
  -H "Authorization: YOUR_TOKEN_HERE" > notification_list.json
type notification_list.json
echo.

REM 3. 获取未读数量
echo 3. 获取未读数量...
curl -s -X GET "%BASE_URL%/notification/unread-count" ^
  -H "Authorization: YOUR_TOKEN_HERE"
echo.

echo ==========================================
echo 测试完成！
echo ==========================================
echo.
echo 注意：Windows 批处理脚本功能有限，建议使用：
echo 1. Postman
echo 2. VS Code REST Client
echo 3. curl 命令手动执行
echo.

pause


