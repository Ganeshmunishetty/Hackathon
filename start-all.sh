#!/bin/bash

echo "Starting Smart City Microservices..."
echo ""

echo "[1/6] Starting Eureka Server..."
cd eureka-server
mvn spring-boot:run > ../logs/eureka.log 2>&1 &
EUREKA_PID=$!
cd ..
sleep 15

echo "[2/6] Starting User Management Service..."
cd java1-user-management
mvn spring-boot:run > ../logs/user-management.log 2>&1 &
USER_PID=$!
cd ..

echo "[3/6] Starting City Entities Service..."
cd java2-city-entities
mvn spring-boot:run > ../logs/city-entities.log 2>&1 &
ENTITIES_PID=$!
cd ..

echo "[4/6] Starting Event Processing Service..."
cd java3-event-processing
mvn spring-boot:run > ../logs/event-processing.log 2>&1 &
EVENT_PID=$!
cd ..

echo "[5/6] Starting Aggregation Service..."
cd java4-aggregation
mvn spring-boot:run > ../logs/aggregation.log 2>&1 &
AGGREGATION_PID=$!
cd ..

sleep 15

echo "[6/6] Starting API Gateway..."
cd api-gateway
mvn spring-boot:run > ../logs/api-gateway.log 2>&1 &
GATEWAY_PID=$!
cd ..

echo ""
echo "All services are starting!"
echo ""
echo "Check Eureka Dashboard: http://localhost:8761"
echo "API Gateway: http://localhost:8080"
echo ""
echo "Service PIDs:"
echo "  Eureka: $EUREKA_PID"
echo "  User Management: $USER_PID"
echo "  City Entities: $ENTITIES_PID"
echo "  Event Processing: $EVENT_PID"
echo "  Aggregation: $AGGREGATION_PID"
echo "  API Gateway: $GATEWAY_PID"
echo ""
echo "Logs are in the logs/ directory"
echo "To stop all services, run: ./stop-all.sh"

