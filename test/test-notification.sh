#!/bin/bash

# 通知API测试脚本
# 使用方法: ./test-notification.sh

BASE_URL="http://localhost:8080/api"

echo "=========================================="
echo "通知API测试脚本"
echo "=========================================="
echo ""

# 颜色定义
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 1. 登录获取 Token
echo -e "${YELLOW}1. 登录获取 Token...${NC}"
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/user/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"test_user","password":"123456"}')

TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
  echo -e "${RED}登录失败！请检查服务是否启动。${NC}"
  exit 1
fi

echo -e "${GREEN}登录成功！Token: ${TOKEN:0:20}...${NC}"
echo ""

# 2. 查询通知列表
echo -e "${YELLOW}2. 查询通知列表...${NC}"
curl -s -X GET "$BASE_URL/notification/list?pageNum=1&pageSize=10" \
  -H "Authorization: $TOKEN" | python3 -m json.tool
echo ""

# 3. 获取未读数量
echo -e "${YELLOW}3. 获取未读数量...${NC}"
curl -s -X GET "$BASE_URL/notification/unread-count" \
  -H "Authorization: $TOKEN" | python3 -m json.tool
echo ""

# 4. 获取通知设置
echo -e "${YELLOW}4. 获取通知设置...${NC}"
curl -s -X GET "$BASE_URL/notification/settings" \
  -H "Authorization: $TOKEN" | python3 -m json.tool
echo ""

# 5. 更新通知设置
echo -e "${YELLOW}5. 更新通知设置...${NC}"
curl -s -X PUT "$BASE_URL/notification/settings" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "emailEnabled": true,
    "inAppEnabled": true,
    "auditNotification": true,
    "activityNotification": true,
    "clubNotification": true,
    "systemNotification": true
  }' | python3 -m json.tool
echo ""

# 6. 标记全部已读
echo -e "${YELLOW}6. 标记全部已读...${NC}"
curl -s -X PUT "$BASE_URL/notification/mark-read" \
  -H "Authorization: $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"markAll": true}' | python3 -m json.tool
echo ""

echo -e "${GREEN}=========================================="
echo "测试完成！"
echo "==========================================${NC}"


