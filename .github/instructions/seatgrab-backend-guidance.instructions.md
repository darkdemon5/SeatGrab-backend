---
applyTo: '**/*'
---

# SeatGrab backend guidance

This repository is a Spring Boot 4.1.1 backend for SeatGrab, built with Java 21, Spring Data MongoDB, and Spring Security. The app entry point is `com.daas.seatgrabbackend.SeatGrabBackendApplication` and it runs on port `8080` by default.

## Project shape

- `src/main/java/com/daas/seatgrabbackend/` holds the application code.
- `controller/` contains HTTP handlers and request boundary logic.
- `service/` contains business logic and response construction.
- `repo/` contains repository interfaces for Mongo persistence.
- `models/` contains MongoDB `@Document` entities.
- `dto/` contains request and response DTOs used at the API boundary.
- `security/` contains shared security configuration and password encoding.
- `src/main/resources/application.properties` contains runtime configuration; the Mongo connection is sourced from `${MONGO}`.

The current implemented vertical slice is owner authentication. Keep new work consistent with that architecture:

- `controller.ownerAuth.OwnerAuthController` exposes `/api/owner` endpoints.
- `service.ownerService.OwnerAuthService` owns signup, sign-in, duplicate checks, email normalization, password hashing, and `ResponseEntity` construction.
- `repo.ownerRepo.OwnerRepo` persists `Owner` documents.
- DTOs in `dto.ownerDto` should not expose password values.
- `security.SecurityConfig` currently disables CSRF, enables HTTP Basic, and allows `POST /api/owner/**` while keeping other routes authenticated.

## Domain and persistence conventions

- Persist Mongo documents with `@Id` and use string IDs for document references.
- Follow the existing string-reference pattern for cross-document links (`ownerId`, `theaterId`, `movieId`, `showtimeId`, `bookingId`, etc.) instead of embedding full object graphs.
- Keep the owner email normalization rule unchanged: `trim().toLowerCase(Locale.ROOT)` before duplicate checks and persistence.
- Hash passwords using the injected `PasswordEncoder` (BCrypt) before storing them.
- Use Lombok patterns already established in the codebase (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) unless a model needs custom logic.
- Keep the project’s Mongo document style consistent when adding models or references.

## API and validation conventions

- Use constructor injection in controllers and services.
- Add validation to request DTO fields and use `@Valid` at the controller boundary.
- Keep service methods responsible for business logic and response construction.
- Preserve the current response-shape conventions in owner-related APIs, including explicit HTTP statuses such as `201`, `404`, `409`, and `400`.
- When adding a new public endpoint, update security configuration deliberately rather than assuming the route is exposed.

## Build, test, and run

Use the Maven wrapper from the repository root:

```powershell
.\mvnw.cmd clean verify
.\mvnw.cmd test
.\mvnw.cmd -Dtest=SeatGrabBackendApplicationTests test
$env:MONGO = "mongodb://localhost:27017/seatgrab"
.\mvnw.cmd spring-boot:run
```

Notes:
- There is no dedicated lint or formatter plugin configured in `pom.xml`.
- The repo’s main smoke test is `SeatGrabBackendApplicationTests` and it boots the Spring context.
- Integration tests that start the application need a reachable MongoDB instance.

## Guidance for future work

- Prefer small, incremental feature slices over broad abstractions.
- Keep the architecture consistent with the current controller/service/repository structure.
- Treat security-sensitive code as a cross-layer concern: validation belongs at the boundary, while auth and response behavior belong in the service layer.
- Keep secrets and environment-specific configuration out of source control; use `${MONGO}` and similar environment-based values.
- If a feature changes owner auth, preserve email normalization, BCrypt hashing, and explicit status-code behavior.
- Do not add nested document object references unless the project explicitly shifts away from the string-ID pattern already used here.

## Teaching and review expectations

When helping with implementation or review:

- Explain the why before the how.
- Prefer targeted design reasoning, small snippets, and debugging guidance over full-file solutions.
- Challenge weak abstractions or unnecessary complexity.
- Keep the discussion focused on the current milestone and the project’s architecture.
- Call out trade-offs, edge cases, and likely failure modes before suggesting a fix.
