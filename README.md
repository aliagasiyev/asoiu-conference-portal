## ASOIU Conference Portal

[![Java](https://img.shields.io/badge/Java-17+-red?logo=openjdk)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-Wrapper-02303A?logo=gradle)](https://gradle.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-App-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Web](https://img.shields.io/badge/Spring%20Web-REST-6DB33F?logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-RBAC-6DB33F?logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![JPA/Hibernate](https://img.shields.io/badge/JPA%2FHibernate-ORM-59666C?logo=hibernate)](https://hibernate.org/)

[![JWT](https://img.shields.io/badge/JWT-Auth-000000?logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![JUnit 5](https://img.shields.io/badge/JUnit%205-Tests-25A162?logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supported-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![MySQL](https://img.shields.io/badge/MySQL-Supported-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)

Web platform for ASOIU academic conferences: user registration, secure login, paper submission with co-authors, dynamic session topics, and contribution workflows for speakers, reviewers, and admins. Role-based access for authors, committee, and administrators.

- Repository: [aliagasiyev/asoiu-conference-portal](https://github.com/aliagasiyev/asoiu-conference-portal)

---

### Features

- **Authentication & Authorization**: Email/password login, JWT-based sessions, Role-Based Access Control (RBAC).
- **Paper Management**: Submit papers, add/update co-authors, upload new versions, withdraw submissions.
- **Review Workflow**: Assign reviewers, submit reviews, track recommendations and final decisions.
- **Sessions & Topics**: Manage conference sessions, topics, and scheduling.
- **Profiles & Contributions**: User profiles, speaker/reviewer contribution forms.
- **Auditability**: Status transitions, timestamps, persistent history via the database.

---

### Project Structure

> Layout derived from repository structure and standard Spring Boot conventions.

```text current authenticated user profile

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
---

### License

No license file detected. Consider adding `LICENSE` (MIT/Apache-2.0) to clarify usage.

---

### Contact

- GitHub: [aliagasiyev/asoiu-conference-portal](https://github.com/aliagasiyev/asoiu-conference-portal)
