# Academic Survey System
**Supplier Research Readiness Pre-Validation Questionnaire**  
*(3D Equipment Capture & Digital Twin Applied Research)*

## Overview
A Java Web-based academic survey system designed to evaluate research readiness across four key roles: Supplier, Production, Venue, and Planner. The system collects responses, calculates weighted scores, and computes an Innovation Feasibility Index (IFI).

## Features
- **Role-based surveys**: 4 distinct roles with 15 questions each
- **Question types**: Single-select and multi-select options
- **Scoring system**: Weighted scoring (0-100 scale) based on question dimensions
- **IFI Calculation**: IFI = 0.45 × Trust + 0.35 × Rework + 0.20 × Safety
- **Database storage**: Questions, options, and responses stored in SQL Server
- **User-friendly interface**: Clean HTML/JavaScript frontend
- **Privacy**: Scores are not displayed to respondents

## Technology Stack
- **Backend**: Spring Boot 2.7.14, Java 11
- **Database**: Microsoft SQL Server
- **Build Tool**: Maven
- **Frontend**: HTML, CSS, JavaScript
- **ORM**: Spring Data JPA (Hibernate)

## Project Structure
```
.
├── src/
│   ├── main/
│   │   ├── java/com/survey/
│   │   │   ├── AcademicSurveyApplication.java  # Main application
│   │   │   ├── model/                           # Domain models
│   │   │   │   ├── Question.java
│   │   │   │   ├── QuestionOption.java
│   │   │   │   └── Response.java
│   │   │   ├── repository/                      # Data access layer
│   │   │   │   ├── QuestionRepository.java
│   │   │   │   ├── QuestionOptionRepository.java
│   │   │   │   └── ResponseRepository.java
│   │   │   ├── service/                         # Business logic
│   │   │   │   ├── SurveyService.java
│   │   │   │   └── DataInitializer.java
│   │   │   └── controller/                      # REST API
│   │   │       └── SurveyController.java
│   │   └── resources/
│   │       ├── application.properties           # Configuration
│   │       └── static/                          # Frontend assets
│   │           ├── index.html
│   │           ├── css/style.css
│   │           └── js/survey.js
│   └── test/
│       └── java/com/survey/                     # Test cases
└── pom.xml                                      # Maven configuration
```

## Prerequisites
- Java 11 or higher
- Maven 3.6+
- Microsoft SQL Server 2012 or higher

## Database Setup

### 1. Install SQL Server
Download and install SQL Server from [Microsoft's website](https://www.microsoft.com/sql-server/).

### 2. Create Database
```sql
CREATE DATABASE SurveyDB;
```

### 3. Configure Connection
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=SurveyDB;encrypt=true;trustServerCertificate=true
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

## Installation & Running

### 1. Clone the Repository
```bash
git clone https://github.com/jacql05/Supplier-Research-Readiness-Pre-Validation-Questionnaire-.git
cd Supplier-Research-Readiness-Pre-Validation-Questionnaire-
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access the Survey
Open your web browser and navigate to:
```
http://localhost:8080
```

## Database Schema

### Tables Created Automatically
The application uses JPA with `ddl-auto=update` to automatically create:

1. **questions** - Survey questions with dimensions and weights
2. **question_options** - Answer options with scores
3. **responses** - User responses with session tracking

### Data Initialization
On first run, the system automatically populates:
- 15 questions for Supplier role
- 15 questions for Production role
- 15 questions for Venue role
- 15 questions for Planner role

Each question includes:
- Question text
- Type (single-select or multi-select)
- Dimension (trust, rework, or safety)
- Weight (importance factor)
- 5 answer options with scores (0-100)

## API Endpoints

### Get Roles
```
GET /api/survey/roles
```
Returns list of available roles.

### Create Session
```
GET /api/survey/session
```
Generates a unique session ID for grouping responses.

### Get Questions
```
GET /api/survey/questions/{role}
```
Returns all questions for a specific role.

### Submit Response
```
POST /api/survey/response
Content-Type: application/json

{
  "respondentName": "John Doe",
  "role": "supplier",
  "questionId": 1,
  "optionIds": [1],
  "sessionId": "uuid-string"
}
```

## Scoring Logic

### Dimension Scores
Each question belongs to one of three dimensions:
- **Trust** (reliability, communication, documentation)
- **Rework** (quality, defects, efficiency)
- **Safety** (protocols, certifications, risk management)

Scores are calculated using weighted averages:
```
Dimension Score = Σ(Option Score × Question Weight) / Σ(Question Weight)
```

### IFI (Innovation Feasibility Index)
```
IFI = 0.45 × Trust Score + 0.35 × Rework Score + 0.20 × Safety Score
```

### Privacy
Scores are calculated and stored internally but are **not displayed** to survey respondents. This ensures unbiased responses.

## Development

### Run Tests
```bash
mvn test
```

### Build WAR for Deployment
```bash
mvn clean package
```
The WAR file will be in `target/academic-survey-system-1.0.0.jar`

## Configuration Options

### Change Server Port
Edit `application.properties`:
```properties
server.port=9090
```

### Database Configuration
For production, update database settings:
```properties
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
```

## Troubleshooting

### Database Connection Issues
- Verify SQL Server is running
- Check firewall allows port 1433
- Confirm username/password in application.properties
- Enable TCP/IP in SQL Server Configuration Manager

### Application Won't Start
```bash
# Check Java version
java -version

# Clean and rebuild
mvn clean install -U
```

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License
This project is part of academic research for 3D Equipment Capture & Digital Twin Applied Research.

## Contact
For questions or support, please open an issue on GitHub.
