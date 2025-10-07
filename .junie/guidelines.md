PokerChipsApplication – Project Guidelines for Contributors

This document captures project-specific knowledge that helps you build, configure, test, and extend the PokerChipsApplication efficiently. It focuses on the non-obvious details of this codebase.

1) Build and Configuration

Backend (Spring Boot)
- Java version: The project’s pom.xml sets <java.version> to 24 (a very new JDK). Ensure your local JDK matches or adjust pom.xml to a locally available LTS (e.g., 21 or 17) across team members before committing. All examples below assume you have a compatible JDK installed.
- Build: Use Maven.
  - Clean build: mvn -q -DskipTests clean package
  - Run tests: mvn -q test
  - Run app: mvn -q spring-boot:run
- Database: The default application.properties points to a local PostgreSQL instance:
  - spring.datasource.url=jdbc:postgresql://localhost:5432/joel
  - spring.datasource.username=joelcode
  - spring.datasource.password=password
  - spring.jpa.hibernate.ddl-auto=create-drop (drops and recreates schema on each run)
  - Notes:
    - There is a docker-compose.yml that starts a Postgres 15 container with:
      - POSTGRES_DB=pokerchips, POSTGRES_USER=joel, POSTGRES_PASSWORD=secret123
      - This does NOT match the application.properties credentials. You must align one side:
        - Option A: change application.properties to point to DB=pokerchips, user=joel, password=secret123
        - Option B: change docker-compose.yml to match the application.properties (DB=joel, user=joelcode, password=password).
      - Start DB: docker compose up -d
    - Dialect is set to org.hibernate.dialect.PostgreSQLDialect.
- Security:
  - Spring Security is enabled via SecurityConfig. JWT-based authentication is enforced for most endpoints. The following endpoints are permitAll:
    - /api/auth/**, /ws/**, /api/rooms/join/**, /h2-console/**
  - CORS is globally configured to allow all origins/methods/headers for development. Tighten for production by setting specific allowed origins.
  - Passwords are encoded with BCryptPasswordEncoder(12).

Frontend (front_end)
- The frontend is a React-based app (vite or CRA-style). Typical workflow:
  - cd front_end
  - npm install
  - npm start or npm run dev (depending on the setup; check package.json scripts)
- You may need to set the backend base URL in the frontend code or via environment file for local development.

2) Testing – How to Configure and Run

Current State
- Tests are executed with: mvn test
- A simple JUnit 5 test exists: src/test/java/com/joelcode/pokerchipsapplication/PokerChipsApplicationTests.java (pure unit test, no Spring context).
  - This is intentional to avoid coupling unit tests to a live database.

Recommended Test Layers
- Unit tests (fast, no Spring context):
  - Place under src/test/java.
  - Avoid @SpringBootTest; keep tests focused and deterministic.
  - Example (already in repo):
    - @Test void simpleMathWorks() { assertEquals(4, 2 + 2); }
- Slice tests (web, data) or integration tests (with Spring context):
  - If you need Spring context or database access, prefer an isolated configuration that does not require a local Postgres server.
  - Recommended approach:
    1) Add an H2 dependency to pom.xml for tests only (managed by Spring Boot BOM in most environments):
       <dependency>
         <groupId>com.h2database</groupId>
         <artifactId>h2</artifactId>
         <scope>test</scope>
       </dependency>
       - If your environment has issues resolving the managed version, explicitly pin a known compatible version, e.g. 2.2.224.
    2) Create src/test/resources/application.properties with H2 settings:
       spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
       spring.datasource.driverClassName=org.h2.Driver
       spring.datasource.username=sa
       spring.datasource.password=
       spring.jpa.hibernate.ddl-auto=create-drop
       spring.jpa.show-sql=false
       spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
    3) Use @SpringBootTest on integration tests; optionally @ActiveProfiles("test") if you create src/test/resources/application-test.properties instead.
  - Web slice example: @WebMvcTest(controllers = MyController.class)
  - JPA slice example: @DataJpaTest (will use embedded DB if H2 is on the classpath)

How to Add and Run a New Test (Demonstration)
- Example unit test you can add under src/test/java/com/joelcode/pokerchipsapplication/util/StringUtilTests.java:
  package com.joelcode.pokerchipsapplication.util;
  import org.junit.jupiter.api.Test;
  import static org.junit.jupiter.api.Assertions.*;
  class StringUtilTests {
      @Test void trimWorks() {
          assertEquals("abc", "  abc  ".trim());
      }
  }
- Run it along with the existing tests:
  mvn -q test
- You should see the tests pass without needing a database.

Notes for Running Tests in CI
- Ensure the CI JDK matches the pom.xml Java version (or lower the project Java version to 21/17 for broader compatibility).
- If you later introduce Spring context tests, ensure H2 test configuration exists, or bring up a Postgres service in CI (e.g., via docker-compose or CI service container) and align credentials with application.properties.

3) Additional Development Information

Entities and Repositories
- Entities live under src/main/java/com/joelcode/pokerchipsapplication/entities with UUID as primary keys.
- Repositories extend Spring Data JPA interfaces in .../repositories. Example: UserRepo extends JpaRepository<User, UUID> and declares common lookup methods.

Services
- Business logic lives in .../service. Noteworthy:
  - UserService exposes createUser, authenticateUser, and findById(UUID). The findById method is used by the JWT auth filter to load the authenticated user.

Security/JWT
- JwtAuthenticationFilter reads the Authorization: Bearer <token> header, validates via JwtTokenProvider, and sets Authentication with a UserPrincipal.
- For local debugging of endpoints behind auth, you can temporarily permit specific paths in SecurityConfig or stub JwtTokenProvider to issue short-lived tokens.

CORS
- The current CORS configuration allows all origins and headers for development convenience. Before production, restrict allowed origins to your deployed frontend domain(s) and limit methods/headers as needed.

H2 Console
- /h2-console/** is permitAll in SecurityConfig, but the application.properties is configured for Postgres. If you want to use H2 console locally, set an H2 datasource in application.properties (not just test resources) and enable spring.h2.console.enabled=true.

Code Style & Conventions
- Prefer constructor injection over field injection (@Autowired) for testability and immutability, especially in services and components.
- Keep service methods transactional where appropriate (@Transactional is already used on UserService class).
- Use Optional-returning repository methods for lookups and convert to domain-specific exceptions at service boundaries.
- Validate inputs at API boundaries and propagate meaningful error messages.

Troubleshooting Tips
- Context fails during tests with DB errors: Either use H2-based test configuration or ensure Postgres is running and credentials match. For quick unit tests, avoid @SpringBootTest.
- JWT or security-related 401s: Verify Authorization header format ("Bearer <token>") and token validity; ensure JwtTokenProvider secret/exp match your environment.
- CORS issues: Confirm the frontend dev server origin is allowed by CorsConfigurationSource; switch from setAllowedOriginPatterns(["*"]) to explicit domains for production.

Verified Commands (Run Locally)
- mvn -q test → passes with the current unit test suite (no DB required)
- docker compose up -d → starts Postgres 15 container as defined (remember to align app credentials)

Appendix: Aligning DB Credentials Quickly
- To immediately align with docker-compose defaults, update src/main/resources/application.properties to:
  spring.datasource.url=jdbc:postgresql://localhost:5432/pokerchips
  spring.datasource.username=joel
  spring.datasource.password=secret123
- Or change docker-compose.yml to:
  POSTGRES_DB=joel
  POSTGRES_USER=joelcode
  POSTGRES_PASSWORD=password

End of guidelines.
