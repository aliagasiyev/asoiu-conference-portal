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

- **Authentication & Authorization**: Email/password login, JWT-based sessions, role-based access control (RBAC).
- **Paper Management**: Submit papers, add/update co-authors, upload new versions, withdraw submissions.
- **Review Workflow**: Assign reviewers, submit reviews, track recommendations and final decisions.
- **Sessions & Topics**: Manage conference sessions, topics, and scheduling.
- **Profiles & Contributions**: User profiles, speaker/reviewer contribution forms.
- **Auditability**: Status transitions, timestamps, persistent history via the database.

---
graph TD
  subgraph "Client"
    U["User: Author / Reviewer / Admin"]
  end

  subgraph "Backend"
    A["Auth"]
    P["Papers"]
    C["Contributions"]
    R["Reference/Reviews"]
    S["Admin Reference"]
    Sec["Security (JWT + RBAC)"]
    Repo["JPA Repositories"]
  end

  DB["Relational Database"]

  U --> A
  U --> P
  U --> C
  U --> R
  U --> S

  A --> Sec
  P --> Sec
  C --> Sec
  R --> Sec
  S --> Sec

  Sec --> Repo
  Repo --> DB

### Project Structure

```text
asoiu-conference-portal/
├─ build.gradle / settings.gradle / gradlew / gradlew.bat
├─ docs/
│  └─ images/
│     ├─ swagger-admin-contribution.png
│     └─ swagger-papers-users.png
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ az/edu/asiouconferenceportal/
│  │  │     ├─ config/
│  │  │     │  └─ DataInitializer.java
│  │  │     ├─ security/
│  │  │     │  └─ SecurityConfig.java
│  │  │     ├─ controller/      (auth, user, paper, contribution, reference, file, home)
│  │  │     ├─ service/         (business logic)
│  │  │     ├─ repository/      (Spring Data JPA repositories)
│  │  │     ├─ entity/          (JPA entities)
│  │  │     ├─ dto/             (request/response DTOs)
│  │  │     └─ exception/       (global handlers, custom exceptions)
│  │  └─ resources/
│  │     └─ application.properties
└─ storage/                      (runtime uploads/artifacts; consider .gitignore)
```

---

### Installation & Run

#### Prerequisites
- Java 17+
- PostgreSQL/MySQL running locally
- Gradle Wrapper is included (no local Gradle needed)

#### Steps
```bash
git clone https://github.com/aliagasiyev/asoiu-conference-portal.git
cd asoiu-conference-portal

# Configure DB in src/main/resources/application.properties or application.yml
# Example (PostgreSQL):
# spring.datasource.url=jdbc:postgresql://localhost:5432/asoiu_conf
# spring.datasource.username=asoiu
# spring.datasource.password=change_me
# spring.jpa.hibernate.ddl-auto=update

./gradlew clean bootRun
# or build a jar:
./gradlew clean build
java -jar build/libs/*.jar
```

---

### API Endpoints

#### Auth Controller
- `POST /api/auth/register`
- `POST /api/auth/login`

#### User Controller
- `GET /api/me`
- `PUT /api/me/password`

#### Paper Controller
- `GET /api/papers`
- `POST /api/papers`
- `GET /api/papers/{id}`
- `PUT /api/papers/{id}`
- `DELETE /api/papers/{id}`
- `POST /api/papers/{id}/withdraw`
- `POST /api/papers/{id}/submit`
- `POST /api/papers/{id}/submit-camera-ready`
- `POST /api/papers/{id}/camera-ready`
- `POST /api/papers/{id}/file`
- `POST /api/papers/{id}/co-authors`
- `PUT /api/papers/{id}/co-authors/{coAuthorId}`
- `DELETE /api/papers/{id}/co-authors/{coAuthorId}`

#### Contribution Controller
- `GET /api/contributions`
- `POST /api/contributions`
- `GET /api/contributions/{id}`
- `PUT /api/contributions/{id}`
- `DELETE /api/contributions/{id}`

#### Reference (Public)
- `GET /api/reference/topics`
- `GET /api/reference/paper-types`

#### Admin Reference
- `GET /api/admin/reference/settings`
- `PUT /api/admin/reference/settings`
- `GET /api/admin/reference/topics`
- `POST /api/admin/reference/topics`
- `PUT /api/admin/reference/topics/{id}`
- `DELETE /api/admin/reference/topics/{id}`
- `GET /api/admin/reference/paper-types`
- `POST /api/admin/reference/paper-types`
- `PUT /api/admin/reference/paper-types/{id}`
- `DELETE /api/admin/reference/paper-types/{id}`

#### Files and Home
- `GET /api/files/{id}`
- `GET /api/home`

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
    C[Contribution Controller/Service]
    R[Reviews/Reference Controllers/Services]
    S[Admin Reference Controller/Service]
    Sec[Spring Security (JWT + RBAC)]
    Repo[(JPA Repositories)]
  end

  DB[(Relational Database)]

  U -->|HTTP/JSON| A
  U -->|HTTP/JSON| P
  U -->|HTTP/JSON| C
  U -->|HTTP/JSON| R
  U -->|HTTP/JSON| S

  A --> Sec
  P --> Sec
  C --> Sec
  R --> Sec
  S --> Sec

  Sec --> Repo
  Repo --> DB
```

---

### Techniques & Patterns Used

- **Layered Architecture**: `controller` → `service` → `repository` → `entity` (+ DTOs).
- **Spring Security + JWT**: Stateless auth; `Authorization: Bearer <token>`; method/route-level RBAC.
- **Repository Pattern (Spring Data JPA)**: CRUD + derived queries; Hibernate as provider.
- **DTOs & Validation**: Request/response DTOs; Bean Validation on inputs; global exception handling.
- **REST Best Practices**: Resource-oriented routes, proper verbs/status codes, pagination/filtering on list endpoints.
- **Config Profiles**: Externalized configuration via `application.properties`/`application.yml`.

---

### Future Improvements

- CI/CD with GitHub Actions, code quality/security scans.
- Containerization (Dockerfile + Compose for app + DB).
- Email notifications for submissions/assignments/decisions.
- External file storage (S3) + antivirus scanning.
- Observability: Micrometer/Prometheus, health checks, structured logging.
- Caching (Redis) for frequently accessed references and lists.
- Rate limiting on auth/upload endpoints.
- Admin dashboards and audit logs.

---

### License

Add a `LICENSE` (MIT/Apache-2.0) to clarify usage.

---

### Contact

- GitHub: [aliagasiyev/asoiu-conference-portal](https://github.com/aliagasiyev/asoiu-conference-portal)
