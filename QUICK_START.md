# Quick Start Guide

## Prerequisites Check

Before starting, ensure you have:
- ✅ Java 17 installed (`java -version`)
- ✅ Maven installed (`mvn -version`)
- ✅ PostgreSQL installed and running
- ✅ Database `smartcity_db` created

## One-Command Setup (After Prerequisites)

### Windows:
```bash
# 1. Create database
psql -U postgres -c "CREATE DATABASE smartcity_db;"

# 2. Start all services
start-all.bat
```

### Linux/Mac:
```bash
# 1. Create database
psql -U postgres -c "CREATE DATABASE smartcity_db;"

# 2. Make script executable
chmod +x start-all.sh

# 3. Create logs directory
mkdir -p logs

# 4. Start all services
./start-all.sh
```

## Manual Step-by-Step

### Step 1: Database Setup
```sql
-- Connect to PostgreSQL
psql -U postgres

-- Create database
CREATE DATABASE smartcity_db;

-- Exit
\q
```

### Step 2: Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
✅ Wait for: "Started EurekaServerApplication"
🌐 Access: http://localhost:8761

### Step 3: Start All Services (in separate terminals)

**Terminal 2 - User Management:**
```bash
cd java1-user-management
mvn spring-boot:run
```

**Terminal 3 - City Entities:**
```bash
cd java2-city-entities
mvn spring-boot:run
```

**Terminal 4 - Event Processing:**
```bash
cd java3-event-processing
mvn spring-boot:run
```

**Terminal 5 - Aggregation:**
```bash
cd java4-aggregation
mvn spring-boot:run
```

**Terminal 6 - API Gateway:**
```bash
cd api-gateway
mvn spring-boot:run
```

### Step 4: Verify

1. **Eureka Dashboard**: http://localhost:8761
   - Should show 5 services registered

2. **API Gateway Health**: http://localhost:8080/actuator/health

3. **Test API**:
```bash
curl http://localhost:8080/api/dashboard/kpis
```

## Service Ports

| Service | Port | URL |
|---------|------|-----|
| Eureka Server | 8761 | http://localhost:8761 |
| User Management | 8081 | http://localhost:8081 |
| City Entities | 8082 | http://localhost:8082 |
| Event Processing | 8083 | http://localhost:8083 |
| Aggregation | 8084 | http://localhost:8084 |
| API Gateway | 8080 | http://localhost:8080 |

## Common Issues

### Port Already in Use
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8081
kill -9 <PID>
```

### Database Connection Error
- Check PostgreSQL is running: `pg_isready`
- Verify credentials in `application.yml`
- Ensure database exists: `psql -U postgres -l`

### Service Not Starting
- Check Java version: `java -version` (must be 17+)
- Check Maven: `mvn -version`
- Check logs in console output

## Testing APIs

### Create User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@smartcity.ai",
    "password": "password123",
    "name": "Admin User"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@smartcity.ai",
    "password": "password123"
  }'
```

### Get Dashboard
```bash
curl http://localhost:8080/api/dashboard
```

## Integration with Frontend

Update your frontend to use:
```typescript
const API_BASE_URL = 'http://localhost:8080/api';
```

Then all API calls go through the gateway:
- `/api/auth/login`
- `/api/dashboard`
- `/api/sensors`
- `/api/cameras`
- `/api/events/ingest`

