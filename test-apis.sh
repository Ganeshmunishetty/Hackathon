#!/bin/bash

echo "Testing Smart City APIs..."
echo ""

API_BASE="http://localhost:8080/api"

echo "1. Testing API Gateway Health..."
curl -s http://localhost:8080/actuator/health | jq .
echo ""

echo "2. Testing Dashboard KPIs..."
curl -s $API_BASE/dashboard/kpis | jq .
echo ""

echo "3. Testing Create User..."
USER_RESPONSE=$(curl -s -X POST $API_BASE/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@smartcity.ai",
    "password": "password123",
    "name": "Test User"
  }')
echo $USER_RESPONSE | jq .
echo ""

echo "4. Testing Login..."
LOGIN_RESPONSE=$(curl -s -X POST $API_BASE/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@smartcity.ai",
    "password": "password123"
  }')
echo $LOGIN_RESPONSE | jq .
TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')
echo ""

if [ "$TOKEN" != "null" ] && [ "$TOKEN" != "" ]; then
    echo "5. Testing Authenticated Request..."
    curl -s $API_BASE/users \
      -H "Authorization: Bearer $TOKEN" | jq .
    echo ""
fi

echo "6. Testing Create Sensor..."
curl -s -X POST $API_BASE/sensors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Air Quality Sensor 1",
    "type": "air_quality",
    "location": "Downtown",
    "latitude": 37.7749,
    "longitude": -122.4194,
    "status": "ONLINE"
  }' | jq .
echo ""

echo "7. Testing Get All Sensors..."
curl -s $API_BASE/sensors | jq .
echo ""

echo "API Tests Complete!"

