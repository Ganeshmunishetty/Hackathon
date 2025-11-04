#!/bin/bash

echo "Stopping all Smart City Microservices..."

# Find and kill Java processes for our services
pkill -f "eureka-server"
pkill -f "user-management-service"
pkill -f "city-entities-service"
pkill -f "event-processing-service"
pkill -f "aggregation-service"
pkill -f "api-gateway"

echo "All services stopped."

