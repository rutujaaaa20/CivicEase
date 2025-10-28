# City Report Management System

## 1. Application Description

The **City Report Management System** is a RESTful web service that allows citizens to report municipal issues, such as:
- Damaged roads
- Public littering
- Broken streetlights
- Other infrastructure problems

The application streamlines the process of handling, assigning, and resolving these issues by enabling city employees and technicians to collaborate through a structured workflow.

---

## 2. Features

- Citizens can report issues with descriptions, location, and priority.
- Reports progress through dynamic statuses: `NEW`, `IN_PROGRESS`, `RESOLVED`.
- Reports are assigned to departments and technicians.
- Technicians can update report statuses.
- RESTful API built with Spring Boot and Hibernate (JPA).
- API documentation via **SpringDoc OpenAPI / Swagger UI**.

---

## 3. Workflow

1. Citizen submits a report with description, location, and priority.
2. The report is saved with status `NEW`.
3. A municipal employee assigns the report to a `Department`.
4. A department head assigns the report to one or more `Technicians` → status becomes `IN_PROGRESS`.
5. Technician resolves the issue and updates status to `RESOLVED`.

---

## 4. Running the Application

### Prerequisites
- Java 21
- Maven 3.6+
- MySQL 8.0+
- IDE with Lombok plugin installed (e.g. IntelliJ IDEA)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/borisbabcak/CityReport.git
   cd CityReport
   ```

2. Create a MySQL database:
   ```sql
   CREATE DATABASE cityreportdb;
   ```

3. Update your `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cityreportdb
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   springdoc.api-docs.path=/api-docs
   ```

4. Run the application:
   - Via IDE (run `CityReportApplication.java`), or
   - Via terminal:
     ```bash
     mvn spring-boot:run
     ```

5. The application will start at: **http://localhost:8080**

---

## 5. ER Diagram & Database Schema

### Entities:
- Citizen
- Report
- Department
- Technician

### Relationships:
- Citizen **1 : M** Report
- Department **1 : M** Report
- Department **1 : M** Technician
- Report **M : N** Technician (via `report_technician` table)

📎 ER diagram included in the repository as `CityReport/er-diagram.pdf`.

---

## 6. API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/reports` | Get list of all reports |
| POST | `/api/reports` | Create a new report |
| PUT | `/api/reports/{id}` | Update report status and department |
| GET | `/api/technicians` | List all technicians |
| POST | `/api/reports/{reportId}/assign-technician/{technicianId}` | Assign technician to report |

### Sample Requests

**GET /api/reports**
```json
{
  "id": 1,
  "title": "Broken traffic light",
  "description": "Traffic light on Main St. doesn't work.",
  "location": "Main St 5",
  "status": "NEW",
  "priority": "HIGH",
  "citizenId": 2
}
```

**POST /api/reports**
```json
{
  "title": "Overflowing trash bin",
  "description": "Bin is full at Central Park",
  "location": "Central Park - Entrance A",
  "priority": "LOW",
  "citizenId": 3
}
```

**PUT /api/reports/{id}**
```json
{
  "status": "IN_PROGRESS",
  "departmentId": 2
}
```

**POST /api/reports/{reportId}/assign-technician/{technicianId}**
```json
{
  "reportId": 5,
  "assignedTechnicians": [
    {
      "id": 3,
      "name": "Jana Hricova"
    }
  ]
}
```

⚠️ **Note**: When testing endpoints in Postman, you might need basic authentication (see credentials below).

---

## 7. API Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

---

## 8. Security

The application uses Spring Security for basic authentication.

**Default credentials:**
```
Username: admin
Password: admin
```
