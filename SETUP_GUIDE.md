# Setup Guide - Smart City Microservices

This guide will walk you through setting up and running all the microservices.

## Prerequisites

### 1. Install Required Software

#### Java 17
- Download from: https://adoptium.net/
- Verify installation:
```bash
java -version
# Should show version 17 or higher
```

#### Maven 3.6+
- Download from: https://maven.apache.org/download.cgi
- Verify installation:
```bash
mvn -version
# Should show Maven 3.6 or higher
```

#### PostgreSQL 12+
- Download from: https://www.postgresql.org/download/
- Install and start PostgreSQL service
- Verify installation:
```bash
psql --version
```

### 2. Install Optional Services

#### Kafka (for Event Processing)
- Download from: https://kafka.apache.org/downloads
- Or use Docker:
```bash
docker run -d -p 9092:9092 apache/kafka:latest
```

#### Eureka Server (for Service Discovery)
- We'll create a simple Eureka server

## Step-by-Step Setup

### Step 1: Database Setup

1. **Start PostgreSQL** (if not running)

2. **Create Database**:
```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE smartcity_db;

# Create user (optional, or use default postgres user)
CREATE USER smartcity_user WITH PASSWORD 'smartcity_password';
GRANT ALL PRIVILEGES ON DATABASE smartcity_db TO smartcity_user;

# Exit psql
\q
```

3. **Update Database Credentials** in each service's `application.yml`:
   - `java1-user-management/src/main/resources/application.yml`
   - `java2-city-entities/src/main/resources/application.yml`

   Change if needed:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/smartcity_db
    username: postgres  # or smartcity_user
    password: postgres   # or smartcity_password
```

### Step 2: Create Eureka Server

Let's create a simple Eureka discovery server:

**Create `eureka-server/pom.xml`**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    
    <groupId>com.smartcity</groupId>
    <artifactId>eureka-server</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <java.version>17</java.version>
        <spring-cloud.version>2023.0.0</spring-cloud.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

**Create `eureka-server/src/main/resources/application.yml`**:
```yaml
server:
  port: 8761

spring:
  application:
    name: eureka-server

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

**Create `eureka-server/src/main/java/com/smartcity/eureka/EurekaServerApplication.java`**:
```java
package com.smartcity.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

### Step 3: Start Services in Order

**Important**: Start services in this order:

#### 1. Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
Wait for: "Started EurekaServerApplication"
Access: http://localhost:8761

#### 2. Start User Management Service
```bash
cd java1-user-management
mvn spring-boot:run
```
Wait for: "Started UserManagementApplication"
Port: 8081

#### 3. Start City Entities Service
```bash
cd java2-city-entities
mvn spring-boot:run
```
Wait for: "Started CityEntitiesApplication"
Port: 8082

#### 4. Start Event Processing Service
```bash
cd java3-event-processing
mvn spring-boot:run
```
Wait for: "Started EventProcessingApplication"
Port: 8083

#### 5. Start Aggregation Service
```bash
cd java4-aggregation
mvn spring-boot:run
```
Wait for: "Started AggregationApplication"
Port: 8084

#### 6. Start API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```
Wait for: "Started ApiGatewayApplication"
Port: 8080

### Step 4: Verify Services

1. **Check Eureka Dashboard**: http://localhost:8761
   - Should show all 5 services registered

2. **Test API Gateway**: http://localhost:8080/actuator/health
   - Should return health status

3. **Test User Service** (via Gateway):
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@smartcity.ai","password":"password123"}'
```

### Step 5: Initialize Data (Optional)

Create an initialization script or use the API to create:
- Admin user
- Default roles and permissions
- Sample sensors, cameras, assets

## Troubleshooting

### Port Already in Use
If a port is already in use:
1. Find the process: `netstat -ano | findstr :8081` (Windows) or `lsof -i :8081` (Mac/Linux)
2. Kill the process or change the port in `application.yml`

### Database Connection Error
- Verify PostgreSQL is running
- Check database credentials in `application.yml`
- Ensure database `smartcity_db` exists

### Service Not Registering in Eureka
- Check Eureka server is running first
- Verify service name matches in `application.yml`
- Check Eureka URL is correct

### Kafka Connection Error (Event Service)
- If Kafka is not available, the service will still start
- Event publishing will fail silently
- Either install Kafka or comment out Kafka dependencies

## Quick Start Scripts

### Windows (start-all.bat)
```batch
@echo off
start "Eureka" cmd /k "cd eureka-server && mvn spring-boot:run"
timeout /t 10
start "User Service" cmd /k "cd java1-user-management && mvn spring-boot:run"
start "Entities Service" cmd /k "cd java2-city-entities && mvn spring-boot:run"
start "Event Service" cmd /k "cd java3-event-processing && mvn spring-boot:run"
start "Aggregation Service" cmd /k "cd java4-aggregation && mvn spring-boot:run"
timeout /t 10
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
echo All services starting...
```

### Linux/Mac (start-all.sh)
```bash
#!/bin/bash
cd eureka-server && mvn spring-boot:run &
sleep 10
cd ../java1-user-management && mvn spring-boot:run &
cd ../java2-city-entities && mvn spring-boot:run &
cd ../java3-event-processing && mvn spring-boot:run &
cd ../java4-aggregation && mvn spring-boot:run &
sleep 10
cd ../api-gateway && mvn spring-boot:run &
echo "All services starting..."
```

## Testing the APIs

### 1. Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@smartcity.ai",
    "password": "password123",
    "name": "Admin User",
    "roleNames": ["ADMIN"]
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@smartcity.ai",
    "password": "password123"
  }'
```

### 3. Create a Sensor
```bash
curl -X POST http://localhost:8080/api/sensors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Air Quality Sensor 1",
    "type": "air_quality",
    "location": "Downtown Plaza",
    "latitude": 37.7749,
    "longitude": -122.4194,
    "status": "ONLINE"
  }'
```

### 4. Get Dashboard Data
```bash
curl http://localhost:8080/api/dashboard
```

## Integration with Frontend

Update your frontend API base URL to point to the API Gateway:

```typescript
// In your frontend config
const API_BASE_URL = 'http://localhost:8080/api';
```

Then update your API calls to use this base URL:
- `/api/auth/login` → http://localhost:8080/api/auth/login
- `/api/dashboard` → http://localhost:8080/api/dashboard
- `/api/events/ingest` → http://localhost:8080/api/events/ingest

## Next Steps After Setup

1. **Create Initial Data**: Set up admin user, roles, and sample entities
2. **Configure AWS**: If using Kinesis, Step Functions, DynamoDB, Timestream
3. **Set up Kafka**: If using event streaming
4. **Add Monitoring**: Set up logging and monitoring (ELK, Prometheus)
5. **Security Hardening**: Configure CORS, rate limiting, etc.

## Production Deployment

For production:
1. Use environment variables for sensitive data
2. Set up proper logging
3. Configure health checks
4. Set up load balancing
5. Use container orchestration (Docker, Kubernetes)
6. Configure SSL/TLS
7. Set up monitoring and alerting

