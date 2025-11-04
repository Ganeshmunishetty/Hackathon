# Smart City Microservices Backend

A comprehensive Spring Boot microservices architecture for a smart city management system with full validation and exception handling.

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Setup
1. Create database: `CREATE DATABASE smartcity_db;`
2. Start Eureka Server: `cd eureka-server && mvn spring-boot:run`
3. Start all services (see START_HERE.md for details)
4. Access API Gateway: http://localhost:8080

## 📁 Project Structure

```
├── java1-user-management/     # User & Auth Service (Port 8081)
├── java2-city-entities/        # Entities CRUD Service (Port 8082)
├── java3-event-processing/     # Event Ingestion Service (Port 8083)
├── java4-aggregation/          # Dashboard & KPI Service (Port 8084)
├── api-gateway/                # API Gateway (Port 8080)
├── eureka-server/              # Service Discovery (Port 8761)
└── database/                   # Database scripts
```

## 🎯 Features

### ✅ Production Ready
- User Management with JWT authentication
- City Entities CRUD (Sensors, Cameras, Assets, Vehicles)
- Event ingestion and normalization
- API Gateway with service discovery
- Comprehensive validation
- Global exception handling

### ⚠️ Partial Implementation
- **Aggregation Service**: Returns mock data for KPIs/analytics
  - Real-time data integration can be added
  - Currently returns static data for demonstration
- **Event Processing**: Step Functions trigger is placeholder
  - Event ingestion works completely
  - AWS Step Functions integration needs configuration

## 📡 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/validate` - Validate token

### Users
- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user
- `GET /api/users` - Get all users
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Entities
- `GET /api/sensors` - Get all sensors
- `POST /api/sensors` - Create sensor
- `GET /api/cameras` - Get all cameras
- `POST /api/cameras` - Create camera
- `GET /api/assets` - Get all assets
- `POST /api/assets` - Create asset
- `GET /api/vehicles` - Get all vehicles
- `POST /api/vehicles` - Create vehicle

### Events
- `POST /api/events/ingest` - Ingest single event
- `POST /api/events/batch` - Ingest batch of events

### Dashboard
- `GET /api/dashboard` - Get dashboard data
- `GET /api/dashboard/kpis` - Get KPIs
- `GET /api/dashboard/analytics` - Get analytics

**All endpoints are accessible through API Gateway at port 8080**

## 🔧 Configuration

### Database
Update `application.yml` in each service:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/smartcity_db
    username: postgres
    password: your_password
```

### Service Discovery
All services register with Eureka at: http://localhost:8761

### Optional Services
- **Kafka**: For event streaming (configure in `java3-event-processing`)
- **AWS Services**: For Step Functions, DynamoDB, Timestream (configure credentials)

## 📚 Documentation

- **START_HERE.md** - Step-by-step setup guide
- **QUICK_START.md** - Quick reference
- **SETUP_GUIDE.md** - Detailed setup instructions
- **DEPLOYMENT_STATUS.md** - What's complete and what needs work

## 🏗️ Architecture

- **Microservices**: 4 core services + API Gateway
- **Service Discovery**: Eureka Server
- **Database**: PostgreSQL (auto-schema generation)
- **Messaging**: Kafka (optional)
- **Authentication**: JWT tokens
- **Validation**: Bean Validation
- **Exception Handling**: Global exception handlers

## 🧪 Testing

```bash
# Test API Gateway
curl http://localhost:8080/actuator/health

# Test Dashboard
curl http://localhost:8080/api/dashboard/kpis

# Create User
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123","name":"Test User"}'
```

## 📝 Notes

- Services use auto-generated database schemas (Hibernate DDL)
- Mock data in Aggregation Service can be replaced with real service calls
- AWS integrations are optional - services work without them
- Kafka is optional - event ingestion works without it

## 🚀 Deployment

For production deployment:
1. Set up PostgreSQL database
2. Configure environment variables
3. Deploy Eureka Server
4. Deploy all microservices
5. Configure API Gateway
6. Optional: Set up Kafka, AWS services

## 📄 License

Part of hackathon submission.

