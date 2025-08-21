asoiu-conference-portal/
├─ build.gradle
├─ settings.gradle
├─ gradlew
├─ gradlew.bat
├─ gradle/
│  └─ wrapper/
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
└─ src/
   ├─ main/
   │  ├─ java/
   │  │  └─ (Spring Boot application root)
   │  │     ├─ config/           (security, web, application config)
   │  │     ├─ controller/       (REST controllers: auth, users, papers, reviews, sessions)
   │  │     ├─ service/          (business logic services)
   │  │     ├─ repository/       (Spring Data JPA repositories)
   │  │     ├─ entity/           (JPA/Hibernate entities)
   │  │     ├─ dto/              (request/response DTOs)
   │  │     ├─ mapper/           (DTO↔entity mapping if used)
   │  │     └─ exception/        (global handlers, custom exceptions)
   │  └─ resources/
   │     ├─ application.yml      (environment configuration)
   │     └─ db/migration/        (Flyway/Liquibase scripts if used)
   └─ test/
      └─ java/                   (JUnit 5 tests))
```

---

### Installation & Run

#### Prerequisites
- Java 17+ installed
- A relational DB (e.g., PostgreSQL/MySQL)
- No local Gradle required (uses Gradle Wrapper)

#### 1) Clone
```bash
git clone https://github.com/aliagasiyev/asoiu-conference-portal.git
cd asoiu-conference-portal
```

#### 2) Configure application properties
Create or edit `src/main/resources/application.yml`:
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/asoiu_conf
    username: asoiu
    password: change_me
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true

app:
  security:
    jwt:
      secret: replace_with_strong_secret
      expirationMinutes: 60
```
- For MySQL, switch the JDBC URL/driver as needed.

#### 3) Run (development)
```bash
./gradlew clean bootRun
```

#### 4) Build and run (production)
```bash
./gradlew clean build
java -jar build/libs/asoiu-conference-portal-*.jar
```

---

### API Endpoints (Representative)

> Align these with your implemented controllers. The following reflects the domain described by the project.

- **Auth**
  - `POST /api/auth/register` — Register a new user (default role: AUTHOR)
  - `POST /api/auth/login` — Obtain JWT access token
  - `POST /api/auth/refresh` — Refresh access token
  - `GET /api/auth/me` — Get current authenticated user profile

- **Users**
  - `GET /api/users/me` — Fetch own profile
  - `PUT /api/users/me` — Update own profile
  - `GET /api/admin/users` — List users (ADMIN)
  - `PATCH /api/admin/users/{id}/roles` — Update user roles (ADMIN)

- **Papers**
  - `GET /api/papers` — List papers (filter by status/author)
  - `POST /api/papers` — Submit a paper
  - `GET /api/papers/{id}` — Get paper details
  - `PUT /api/papers/{id}` — Update paper metadata
  - `DELETE /api/papers/{id}` — Withdraw paper
  - `POST /api/papers/{id}/coauthors` — Add a co-author
  - `DELETE /api/papers/{id}/coauthors/{coAuthorId}` — Remove co-author
  - `POST /api/papers/{id}/files` — Upload manuscript/revision

- **Reviews**
  - `GET /api/reviews?paperId={id}` — List reviews for a paper (COMMITTEE/ADMIN)
  - `POST /api/reviews` — Submit a review (assigned reviewer)
  - `PUT /api/reviews/{id}` — Update review
  - `POST /api/papers/{id}/assignments` — Assign reviewer(s) (COMMITTEE/ADMIN)
  - `POST /api/papers/{id}/decision` — Record final decision (COMMITTEE/ADMIN)

- **Sessions & Topics**
  - `GET /api/sessions` — List sessions
  - `POST /api/sessions` — Create session (ADMIN)
  - `PUT /api/sessions/{id}` — Update session (ADMIN)
  - `DELETE /api/sessions/{id}` — Delete session (ADMIN)
  - `GET /api/topics` — List topics
  - `POST /api/topics` — Create topic (ADMIN)

---

### Architecture Diagram

```mermaid
graph TD
  subgraph Client
    U[User (Author/Reviewer/Admin)]
  end

  subgraph Backend[Spring Boot Application]
    A[Auth Controller/Service]
    P[Papers Controller/Service]
    R[Reviews Controller/Service]
    S[Sessions/Topics Controller/Service]
    Sec[Spring Security (JWT + RBAC)]
    Repo[(JPA Repositories)]
  end

  DB[(Relational Database)]

  U -->|HTTP/JSON| A
  U -->|HTTP/JSON| P
  U -->|HTTP/JSON| R
  U -->|HTTP/JSON| S

  A --> Sec
  P --> Sec
  R --> Sec
  S --> Sec

  Sec --> Repo
  Repo --> DB
```

---

### Techniques & Patterns Used

- **Layered Architecture**
  - `controller` → `service` → `repository` → `entity` with DTOs at the edges.
- **RESTful API Design**
  - Resource-oriented endpoints, standard HTTP verbs and status codes.
- **Spring Security with JWT**
  - Stateless authentication using JWT; Bearer tokens in `Authorization` header; method or route-level RBAC.
- **RBAC (Role-Based Access Control)**
  - Distinct roles for AUTHOR, COMMITTEE, ADMIN; restricted admin endpoints.
- **Repository Pattern (Spring Data JPA)**
  - `repository` interfaces delegate CRUD and query methods to Spring Data; Hibernate as JPA provider.
- **DTOs and (Optional) Mapping Layer**
  - Request/response DTOs for input validation and clean API contracts; mapper utilities (manual or MapStruct if introduced).
- **Validation & Error Handling**
  - Bean Validation (e.g., `@Valid`) on DTOs; global exception handlers for consistent error responses.
- **File Handling (Submissions)**
  - Endpoints to upload manuscripts and revisions; persistence of metadata and storage location.
- **Pagination & Filtering**
  - Common list endpoints support pagination/sorting/filter query parameters for large datasets.
- **Configuration Management**
  - Externalized config via `application.yml`, profiles for `dev`/`prod`.

> Note: Class/package names above reflect standard Spring Boot conventions for a project of this kind. Align names with your codebase.

---

### Future Improvements

- **CI/CD**: GitHub Actions pipelines (build, test, security scan).
- **Containerization**: Dockerfile + Docker Compose for app + DB.
- **Email Notifications**: Submission, assignment, and decision updates.
- **File Security**: Antivirus scanning, file type/size validation, S3 or similar storage.
- **Observability**: Centralized logging, metrics (Micrometer), health checks.
- **Caching & Performance**: Redis caching for frequently accessed data.
- **Rate Limiting & Throttling**: Protect auth and upload endpoints.
- **Admin UI/Dashboard**: Review workloads, session planning, audit trails.

### License

No license file detected. Consider adding `LICENSE` (MIT/Apache-2.0) to clarify usage.

---

### Contact

- GitHub: [aliagasiyev/asoiu-conference-portal](https://github.com/aliagasiyev/asoiu-conference-portal)
