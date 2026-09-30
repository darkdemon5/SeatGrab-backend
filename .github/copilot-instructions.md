# Copilot instructions for SeatGrab-backend

## Project overview

SeatGrab-backend is a Spring Boot 4.1.1 REST API targeting Java 21. It uses MongoDB through Spring Data MongoDB and exposes the application from `SeatGrabBackendApplication` on port `8080` by default.

The current implemented vertical slice is owner authentication:

- `controller/ownerAuth/OwnerAuthController` defines the `/api/owner` HTTP endpoints.
- `service/ownerService/OwnerAuthService` owns signup and owner lookup behavior, including email normalization, duplicate checks, password hashing, timestamps, and response DTO mapping.
- `repo/ownerRepo/OwnerRepo` provides MongoDB persistence for `Owner`.
- `dto/ownerDto` contains request and response boundary types; do not expose the persisted password in response DTOs.
- `security` contains the shared `SecurityFilterChain` and BCrypt `PasswordEncoder`.

The other domain types (`User`, `Theater`, `Movie`, `Showtime`, `Booking`, `Ticket`, and `Payment`) are MongoDB `@Document` models. They currently link to one another with string IDs rather than object relationships. Keep that persistence style consistent when adding features.

## Build, run, and test

Use the checked-in Maven wrapper from the repository root:

```powershell
.\mvnw.cmd clean verify
.\mvnw.cmd test
.\mvnw.cmd -Dtest=SeatGrabBackendApplicationTests test
.\mvnw.cmd spring-boot:run
```

The project has no configured lint or formatter Maven plugin. The existing test is a Spring context-load test; integration tests that start the application need a reachable MongoDB instance. At present, the test source imports JUnit and Spring Boot test classes, but `pom.xml` does not declare a test starter, so the test command may fail during test compilation until those dependencies are added.

Runtime configuration is in `src/main/resources/application.properties`. Set the `MONGO` environment variable to the MongoDB connection URI before running the application, for example:

```powershell
$env:MONGO = "mongodb://localhost:27017/seatgrab"
.\mvnw.cmd spring-boot:run
```

## Code conventions and integration points

- Follow the existing package layout and naming, including the lower-camel package segments such as `ownerAuth`, `ownerService`, `ownerRepo`, and `ownerDto`.
- Use constructor injection for controllers and services. Keep persistence access in repository interfaces and business behavior in services.
- Use Lombok annotations already established on models and DTOs (`@Data`, `@NoArgsConstructor`, and `@AllArgsConstructor`) unless a type needs intentionally customized behavior.
- Persist domain objects as MongoDB documents with `@Id`; use string IDs and explicit `...Id` fields for cross-document references.
- Owner email identity is normalized with `trim().toLowerCase(Locale.ROOT)` before duplicate checks and persistence. Preserve this rule for owner-related email operations.
- Owner passwords must be stored only after passing through the injected BCrypt `PasswordEncoder`; never place password fields in response DTOs.
- Request validation is supplied with Jakarta Validation and `@Valid` at the controller boundary. Add constraints to request DTO fields rather than embedding input validation in controllers.
- HTTP response construction currently lives in the service layer and uses `ResponseEntity` with explicit statuses (`201`, `404`, `409`, and `400`). Preserve the established response shapes when extending the owner API.
- Security is configured in `SecurityConfig`: CSRF is disabled, HTTP Basic is enabled, and authorization rules are route-specific. Update the security matcher deliberately whenever adding a public endpoint.
- Keep secrets and environment-specific connection details out of source control; use properties placeholders/environment variables like `${MONGO}`.
