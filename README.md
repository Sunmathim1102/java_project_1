# SkillMatrix — Employee Skill Matrix & Project Staffing Recommender

Backend-focused Java 21 + Spring Boot monolithic application for maintaining employee technical skill matrices, evaluating team-wide skill gaps, enforcing employee allocation capacity limits (sum of active allocations &le; 100%), and calculating ranked staffing recommendations for project requirements.

---

## 🚀 Key Features

1. **Employee Skill Matrix**: Manage employees and multi-level skill proficiencies on a clear 1 to 5 scale:
   - `1` = Beginner, `2` = Basic, `3` = Intermediate, `4` = Advanced, `5` = Expert.
2. **Project Skill Requirements**: Define project staffing requirements with required proficiency levels and importance weightings.
3. **Intelligent Recommendation Engine**: Multi-factor scoring algorithm ranking candidates by:
   - **Skill Match Score** (Weight = 0.40)
   - **Proficiency Score** (Weight = 0.40) — explicitly prioritizes candidates with higher proficiency levels for the same skill.
   - **Availability Score** (Weight = 0.20)
4. **Capacity-Constrained Allocation Management**: Enforces strict business rule in the Service layer:
   $$\text{Current Active Allocations} + \text{Requested Allocation} \le 100\%$$
   Rejects requests with HTTP 400 `InsufficientCapacityException` when capacity is exceeded.
5. **Team Skill Coverage & Gap Analysis**: Dynamic telemetry reporting total available capacity, highest available proficiency, and gap status (`AVAILABLE`, `PARTIAL`, `GAP`).
6. **OpenAPI / Swagger Documentation**: Full interactive Swagger UI available at `/swagger-ui.html`.

---

## 🛠 Technology Stack

- **Language**: Java 21 (LTS)
- **Framework**: Spring Boot 3.3.4 (Spring Web, Spring Data JPA, Hibernate, Validation)
- **Database**: MySQL (`skillmatrix_db`) with H2 in-memory profile for tests
- **Build Tool**: Apache Maven
- **API Documentation**: Springdoc OpenAPI / Swagger UI 2.6.0
- **Testing**: JUnit 5, Mockito, Spring Boot Test / MockMvc

---

## 🏛 Architecture Overview

```
Client (HTTP / Swagger / Frontend)
   │
   ▼
Controller Layer (@RestController, Jakarta Validation)
   │
   ▼
Service Layer (@Service, @Transactional, Business Rules & Scorer)
   │
   ▼
Repository Layer (Spring Data JPA Repositories)
   │
   ▼
Entity Layer (Hibernate ORM / JPA Mappings)
   │
   ▼
MySQL Database (skillmatrix_db)
```

### Entity Relationship Model

```
Employee (1) <──── (N) EmployeeSkill (N) ────> (1) Skill
   │
   │ (1)
   ▼
Allocation (N)
   ▲
   │ (1)
Project (1) <──── (N) ProjectSkillRequirement (N) ────> (1) Skill
```

---

## 📋 REST API Endpoints

### 1. Employees (`/api/employees`)
- `POST   /api/employees` — Create an employee
- `GET    /api/employees` — Get all employees
- `GET    /api/employees/{id}` — Get employee by ID
- `PUT    /api/employees/{id}` — Update employee details
- `DELETE /api/employees/{id}` — Delete employee
- `GET    /api/employees/{id}/availability` — Capacity and active allocation breakdown

### 2. Skills (`/api/skills`)
- `POST   /api/skills` — Create a new skill
- `GET    /api/skills` — List all skills
- `GET    /api/skills/{id}` — Get skill by ID
- `PUT    /api/skills/{id}` — Update skill
- `DELETE /api/skills/{id}` — Delete skill

### 3. Employee Skills (`/api/employees/{employeeId}/skills`)
- `POST   /api/employees/{employeeId}/skills` — Assign skill with proficiency (1 to 5)
- `GET    /api/employees/{employeeId}/skills` — List skills for an employee
- `PUT    /api/employees/{employeeId}/skills/{skillId}` — Update proficiency level
- `DELETE /api/employees/{employeeId}/skills/{skillId}` — Remove skill mapping

### 4. Projects (`/api/projects`)
- `POST   /api/projects` — Create a project
- `GET    /api/projects` — List all projects
- `GET    /api/projects/{id}` — Get project by ID
- `PUT    /api/projects/{id}` — Update project
- `DELETE /api/projects/{id}` — Delete project

### 5. Project Skill Requirements (`/api/projects/{projectId}/skills`)
- `POST   /api/projects/{projectId}/skills` — Add required skill with proficiency
- `GET    /api/projects/{projectId}/skills` — List required skills
- `PUT    /api/projects/{projectId}/skills/{skillId}` — Update required proficiency
- `DELETE /api/projects/{projectId}/skills/{skillId}` — Remove skill requirement

### 6. Allocations (`/api/allocations`)
- `POST   /api/allocations` — Create allocation (strictly validates capacity &le; 100%)
- `GET    /api/allocations` — List all allocations (filter by `employeeId` or `projectId`)
- `GET    /api/allocations/{id}` — Get allocation by ID
- `PUT    /api/allocations/{id}` — Update allocation
- `DELETE /api/allocations/{id}` — Delete allocation (restores capacity)

### 7. Recommendation Engine (`/api/projects/{projectId}/recommendations`)
- `GET    /api/projects/{projectId}/recommendations` — Returns ranked candidates.

### 8. Team Skill Coverage (`/api/projects/{projectId}/skill-coverage`)
- `GET    /api/projects/{projectId}/skill-coverage` — Skill coverage and gap breakdown.

---

## ⚙️ Setup & Running

### Prerequisites
- JDK 21
- Apache Maven 3.8+
- MySQL Server running on localhost:3306

### Database Configuration
Ensure MySQL has a database named `skillmatrix_db` (or allow the connection string to auto-create):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/skillmatrix_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```
*(Environment variables `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` can also be passed).*

### Build & Test
```bash
mvn clean test
```

### Run Application
```bash
mvn spring-boot:run
```

- **Interactive Web Dashboard**: [http://localhost:8080/](http://localhost:8080/)
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
