# START HERE - First Steps Checklist ✅

Follow these steps in order. Don't skip ahead!

## STEP 1: Check Prerequisites ⚙️

Open your terminal/command prompt and run these commands:

### 1.1 Check Java
```bash
java -version
```
**Expected**: Should show version 17 or higher  
**If not**: Download from https://adoptium.net/ (select Java 17)

### 1.2 Check Maven
```bash
mvn -version
```
**Expected**: Should show Maven 3.6 or higher  
**If not**: Download from https://maven.apache.org/download.cgi

### 1.3 Check PostgreSQL
```bash
# Windows
psql --version

# Linux/Mac  
psql --version
```
**Expected**: Should show PostgreSQL version  
**If not**: Download from https://www.postgresql.org/download/

**✅ When all 3 commands work, move to Step 2**

---

## STEP 2: Start PostgreSQL Database 🗄️

### 2.1 Start PostgreSQL Service

**Windows:**
- Open Services (Win+R, type `services.msc`)
- Find "PostgreSQL" service
- Right-click → Start (if not running)

**Linux:**
```bash
sudo systemctl start postgresql
```

**Mac:**
```bash
brew services start postgresql
```

### 2.2 Create Database

Open a new terminal and run:

```bash
# Connect to PostgreSQL
psql -U postgres

# Create the database
CREATE DATABASE smartcity_db;

# Exit PostgreSQL
\q
```

**✅ When database is created, move to Step 3**

---

## STEP 3: Start Eureka Server (Service Discovery) 🔍

This must be started FIRST before all other services.

### 3.1 Navigate to Eureka Server
```bash
cd eureka-server
```

### 3.2 Start Eureka
```bash
mvn spring-boot:run
```

**Wait for this message:**
```
Started EurekaServerApplication in X.XXX seconds
```

### 3.3 Verify Eureka is Running
Open browser: **http://localhost:8761**

You should see the Eureka dashboard.

**✅ When Eureka dashboard loads, move to Step 4**

**⚠️ Keep this terminal window open - don't close it!**

---

## STEP 4: Start All Other Services 🚀

Now open **5 more terminal windows** (one for each service).

### Terminal 2: User Management Service
```bash
cd java1-user-management
mvn spring-boot:run
```
Wait for: `Started UserManagementApplication`

### Terminal 3: City Entities Service
```bash
cd java2-city-entities
mvn spring-boot:run
```
Wait for: `Started CityEntitiesApplication`

### Terminal 4: Event Processing Service
```bash
cd java3-event-processing
mvn spring-boot:run
```
Wait for: `Started EventProcessingApplication`

### Terminal 5: Aggregation Service
```bash
cd java4-aggregation
mvn spring-boot:run
```
Wait for: `Started AggregationApplication`

### Terminal 6: API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```
Wait for: `Started ApiGatewayApplication`

**✅ When all 6 services show "Started", move to Step 5**

---

## STEP 5: Verify Everything Works ✅

### 5.1 Check Eureka Dashboard
Go to: **http://localhost:8761**

You should see **5 services** registered:
- user-management-service
- city-entities-service
- event-processing-service
- aggregation-service
- api-gateway

### 5.2 Test API Gateway
Open a new terminal and run:
```bash
curl http://localhost:8080/actuator/health
```

**Expected**: Should return JSON with "status": "UP"

### 5.3 Test Dashboard API
```bash
curl http://localhost:8080/api/dashboard/kpis
```

**Expected**: Should return JSON with KPI data

**✅ If all tests pass, you're done!**

---

## STEP 6: Connect Frontend (Optional) 🎨

Update your frontend code to use the API Gateway:

```typescript
// In your frontend config file
const API_BASE_URL = 'http://localhost:8080/api';
```

Then all your API calls will go through the gateway:
- `POST /api/auth/login`
- `GET /api/dashboard`
- `GET /api/sensors`
- `POST /api/events/ingest`

---

## 🆘 Troubleshooting

### Problem: "Port already in use"
**Solution**: 
```bash
# Windows - Find what's using the port
netstat -ano | findstr :8080

# Kill the process (replace PID with actual number)
taskkill /PID <PID> /F
```

### Problem: "Database connection refused"
**Solution**: 
1. Check PostgreSQL is running
2. Verify database name is `smartcity_db`
3. Check username/password in `application.yml`

### Problem: Service won't start
**Solution**:
1. Check Java version: `java -version` (must be 17+)
2. Check Maven: `mvn -version`
3. Check for errors in the terminal output

### Problem: Services not showing in Eureka
**Solution**:
1. Make sure Eureka started first
2. Wait 30 seconds for services to register
3. Check service names match in `application.yml`

---

## 📋 Quick Reference

| Service | Port | What it does |
|---------|------|--------------|
| Eureka | 8761 | Service discovery |
| User Management | 8081 | Authentication & users |
| City Entities | 8082 | Sensors, cameras, assets |
| Event Processing | 8083 | Event ingestion |
| Aggregation | 8084 | Dashboard & KPIs |
| API Gateway | 8080 | **Main entry point** |

**Always use port 8080 (API Gateway) for your frontend!**

---

## ✅ Success Checklist

- [ ] Java 17 installed and verified
- [ ] Maven installed and verified
- [ ] PostgreSQL installed and running
- [ ] Database `smartcity_db` created
- [ ] Eureka server running on port 8761
- [ ] All 5 services started and registered in Eureka
- [ ] API Gateway responding on port 8080
- [ ] Test API calls working
- [ ] Frontend connected to API Gateway

**When all boxes are checked, you're ready to go! 🎉**

