@echo off
echo Starting Smart City Microservices...
echo.

echo [1/6] Starting Eureka Server...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"
echo Waiting for Eureka to start...
timeout /t 15 /nobreak >nul

echo [2/6] Starting User Management Service...
start "User Management" cmd /k "cd java1-user-management && mvn spring-boot:run"

echo [3/6] Starting City Entities Service...
start "City Entities" cmd /k "cd java2-city-entities && mvn spring-boot:run"

echo [4/6] Starting Event Processing Service...
start "Event Processing" cmd /k "cd java3-event-processing && mvn spring-boot:run"

echo [5/6] Starting Aggregation Service...
start "Aggregation" cmd /k "cd java4-aggregation && mvn spring-boot:run"

echo Waiting for services to initialize...
timeout /t 15 /nobreak >nul

echo [6/6] Starting API Gateway...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"

echo.
echo All services are starting!
echo.
echo Check Eureka Dashboard: http://localhost:8761
echo API Gateway: http://localhost:8080
echo.
echo Press any key to exit this window (services will continue running)
pause >nul

