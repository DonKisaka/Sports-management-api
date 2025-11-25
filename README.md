# Sports Management System

A comprehensive REST API for managing sports facilities, fields, and bookings built with Spring Boot. This system allows administrators to manage sport fields and enables users to book facilities for various sports activities.

## Features

- 🔐 **JWT-based Authentication** - Secure user authentication and authorization
- 👥 **Role-based Access Control** - Admin and User roles with appropriate permissions
- 🏟️ **Sport Field Management** - Create, read, update, and deactivate sport fields
- 📅 **Booking Management** - Manage bookings with status tracking (Pending, Confirmed, Cancelled, Finished)
- 🔍 **Advanced Filtering** - Filter sport fields by type, location, and active status
- 🐳 **Docker Support** - Easy deployment with Docker and Docker Compose
- 📊 **PostgreSQL Database** - Reliable data persistence

## Tech Stack

- **Framework**: Spring Boot 3.5.6
- **Java Version**: 25
- **Database**: PostgreSQL 16
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Mapping**: MapStruct
- **Utilities**: Lombok
- **Validation**: Jakarta Validation

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 25 or higher
- Maven 3.6+ (or use the included Maven Wrapper)
- PostgreSQL 16 (or use Docker Compose)
- Docker and Docker Compose (optional, for containerized deployment)

## Project Structure

```
sportsmanagementsystem/
├── src/
│   ├── main/
│   │   ├── java/com/example/sportsmanagementsystem/
│   │   │   ├── config/          # Security and JWT configuration
│   │   │   ├── controller/      # REST API endpoints
│   │   │   ├── Dto/             # Data Transfer Objects
│   │   │   ├── mapper/          # MapStruct mappers
│   │   │   ├── model/           # Entity models
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── service/         # Business logic
│   │   │   └── SportsmanagementsystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Test files
├── docker-compose.yml           # Docker Compose configuration
├── Dockerfile                   # Docker image configuration
└── pom.xml                      # Maven dependencies
```

## Installation & Setup

### Option 1: Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd sportsmanagementsystem/sportsmanagementsystem
   ```

2. **Set up PostgreSQL Database**
   
   Create a database and user:
   ```sql
   CREATE DATABASE sports_complex;
   CREATE USER sportuser WITH PASSWORD 'sportpass';
   GRANT ALL PRIVILEGES ON DATABASE sports_complex TO sportuser;
   ```

3. **Configure Application Properties**
   
   Update `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/sports_complex
   spring.datasource.username=sportuser
   spring.datasource.password=sportpass
   server.port=8082
   ```

4. **Build the Project**
   ```bash
   ./mvnw clean install
   ```

5. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Or if using the wrapper on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

The application will be available at `http://localhost:8082`

### Option 2: Docker Compose Setup (Recommended)

1. **Navigate to the project directory**
   ```bash
   cd sportsmanagementsystem/sportsmanagementsystem
   ```

2. **Start the services**
   ```bash
   docker-compose up --build
   ```

   This will:
   - Build the Spring Boot application
   - Start PostgreSQL database on port 5433
   - Start the application on port 8080

3. **Stop the services**
   ```bash
   docker-compose down
   ```

## Configuration

### Environment Variables

You can override the default configuration using environment variables:

- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `SERVER_PORT` - Application server port (default: 8082)
- `SECURITY_JWT_SECRET_KEY` - JWT secret key
- `SECURITY_JWT_EXPIRATION_TIME` - JWT expiration time in milliseconds

### JWT Configuration

The JWT secret key and expiration time can be configured in `application.properties`:

```properties
security.jwt.secret-key=your-secret-key-here
security.jwt.expiration-time=86400000  # 24 hours in milliseconds
```

## API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "USER"
}
```

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

### Sport Field Endpoints

All sport field endpoints are prefixed with `/api/v1/sport-fields`

#### Get All Sport Fields
```http
GET /api/v1/sport-fields
Authorization: Bearer <token>
```

**Query Parameters:**
- `active` (boolean) - Filter by active status
- `sportType` (enum) - Filter by sport type
- `locationType` (enum) - Filter by location type

**Example:**
```http
GET /api/v1/sport-fields?active=true&sportType=FOOTBALL
```

#### Get Sport Field by ID
```http
GET /api/v1/sport-fields/{fieldId}
Authorization: Bearer <token>
```

#### Create Sport Field (Admin Only)
```http
POST /api/v1/sport-fields
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "name": "Field A",
  "sportType": "FOOTBALL",
  "locationType": "INDOOR",
  "pricePerHour": 50.00
}
```

#### Update Sport Field (Admin Only)
```http
PUT /api/v1/sport-fields/{fieldId}
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "name": "Field A Updated",
  "sportType": "FOOTBALL",
  "locationType": "OUTDOOR",
  "pricePerHour": 60.00
}
```

#### Deactivate Sport Field (Admin Only)
```http
DELETE /api/v1/sport-fields/{fieldId}
Authorization: Bearer <admin_token>
```

### Booking Endpoints

All booking endpoints are prefixed with `/api/v1/bookings`

#### Create Booking
```http
POST /api/v1/bookings
Authorization: Bearer <token>
Content-Type: application/json

{
  "sportFieldId": 1,
  "clientName": "John Doe",
  "clientPhone": "+1234567890",
  "clientEmail": "client@example.com",
  "startDateTime": "2024-01-15T10:00:00",
  "endDateTime": "2024-01-15T12:00:00"
}
```

#### Get All Bookings
```http
GET /api/v1/bookings
Authorization: Bearer <token>
```

#### Get Booking by ID
```http
GET /api/v1/bookings/{bookingId}
Authorization: Bearer <token>
```

#### Update Booking Status (Admin Only)
```http
PATCH /api/v1/bookings/{bookingId}/status
Authorization: Bearer <admin_token>
Content-Type: application/json

{
  "status": "CONFIRMED"
}
```

#### Cancel Booking
```http
DELETE /api/v1/bookings/{bookingId}
Authorization: Bearer <token>
```

## Models

### Role Enum
- `ADMIN` - Full system access
- `USER` - Standard user access

### BookingStatus Enum
- `PENDING` - Initial booking status
- `CONFIRMED` - Booking confirmed
- `CANCELLED` - Booking cancelled
- `FINISHED` - Booking completed

### SportType & LocationType
Refer to the enum definitions in the `model` package for available values.

## Security

- All endpoints (except authentication) require JWT Bearer token authentication
- Admin-only endpoints are protected using `@PreAuthorize("hasRole('ADMIN')")`
- Passwords are encoded using Spring Security's BCryptPasswordEncoder
- JWT tokens are signed using HS256 algorithm

## Database

The application uses JPA/Hibernate with automatic schema generation. The `ddl-auto` is set to `update`, which will:
- Automatically create tables on first run
- Update schema based on entity changes
- Preserve existing data

**Important**: For production, consider using `validate` or `none` with proper migration scripts.

## Running Tests

```bash
./mvnw test
```

Or on Windows:
```bash
mvnw.cmd test
```

## Building for Production

```bash
./mvnw clean package -DskipTests
```

The JAR file will be created in the `target/` directory.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues, questions, or contributions, please open an issue in the repository.

