@echo off
echo Testing Smart City APIs...
echo.

set API_BASE=http://localhost:8080/api

echo 1. Testing API Gateway Health...
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo 2. Testing Dashboard KPIs...
curl -s %API_BASE%/dashboard/kpis
echo.
echo.

echo 3. Testing Create User...
curl -s -X POST %API_BASE%/users -H "Content-Type: application/json" -d "{\"email\":\"test@smartcity.ai\",\"password\":\"password123\",\"name\":\"Test User\"}"
echo.
echo.

echo 4. Testing Login...
curl -s -X POST %API_BASE%/auth/login -H "Content-Type: application/json" -d "{\"email\":\"test@smartcity.ai\",\"password\":\"password123\"}"
echo.
echo.

echo 5. Testing Get All Sensors...
curl -s %API_BASE%/sensors
echo.
echo.

echo API Tests Complete!
pause

