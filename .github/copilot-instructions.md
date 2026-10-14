# Copilot instructions for SeatGrab-backend

## Project snapshot

- Java 21, Spring Boot 4.1.1, Spring Data MongoDB, and Spring Security.
- Application entry point: `com.daas.seatgrabbackend.SeatGrabBackendApplication`.
- Default port: `8080`.
- MongoDB is configured with `spring.mongodb.uri=${MONGO}` in `src/main/resources/application.properties`.
- Owner authentication under `/api/owner` is the most complete vertical slice: owner lookup, signup, and sign-in DTOs/routes are present. User signup/sign-in/lookup code also exists under `/api/user`, but it is still evolving. JWT/email verification are not yet implemented.
- The MVP is a movie reservation system: owners manage theaters, movies, and showtimes; customers browse a movie, choose its showtime, reserve capacity, pay through a mock flow, and receive a ticket. Exact seat selection and later payment/recommendation/refund/search/production-email work are planned extensions.

## Build, test, and run

Run commands from the repository root with the checked-in Maven wrapper:

```powershell
.\mvnw.cmd clean verify
.\mvnw.cmd test
.\mvnw.cmd -Dtest=SeatGrabBackendApplicationTests test
$env:MONGO = "mongodb://localhost:27017/seatgrab"
.\mvnw.cmd spring-boot:run
```

There is no dedicated lint or formatter plugin in `pom.xml`. The existing test is a Spring context-load smoke test; context/integration tests require a reachable MongoDB instance and a valid `MONGO` value.

## High-level architecture

The backend is a feature-oriented Spring MVC application using controller/service/repository separation:

- `controller/` owns HTTP routes and request binding. Owner routes are in `controller.ownerAuth.OwnerAuthController`; user routes are in `controller.userAuth.UserAuthController`.
- `service/` owns business rules and currently builds `ResponseEntity` responses. `service.ownerService.OwnerAuthService` and `service.userService.UserAuthService` handle authentication-related persistence and DTO mapping.
- `repo/` contains Spring Data MongoDB repository interfaces. `OwnerRepo` and `UserRepo` persist authentication documents and expose derived lookup methods.
- `models/` contains MongoDB `@Document` records for owners, users, theaters, movies, showtimes, bookings, tickets, and payments. Cross-document relationships use ID fields rather than nested object graphs.
- `dto/` contains API request/response types. Owner DTOs live in `dto.ownerDto`; response DTOs must not expose password hashes or verification/access tokens.
- `security/` contains shared configuration. `PasswordConfig` exposes a `PasswordEncoder`; `SecurityConfig` currently disables CSRF, enables HTTP Basic, permits only `POST /api/owner/**`, and requires authentication for every other request. Do not describe HTTP Basic as JWT authentication until JWT is actually added.

The intended feature flow is owner setup -> movie catalog -> owner-managed showtimes -> movie-filtered showtimes -> quantity-based reservation/temporary holds -> mock payment -> ticket. Preserve this order when adding new slices.

## Codebase-specific conventions

- Preserve package naming: `controller.ownerAuth`, `service.ownerService`, `repo.ownerRepo`, `dto.ownerDto`, `security`, `models`, and `enums`.
- Use constructor injection for controllers and services; dependency fields should be `private final`.
- Use Lombok patterns already established (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) unless custom behavior requires otherwise.
- Mongo documents currently use `String` IDs and references such as `theaterId`, `movieId`, `showtimeId`, and `bookingId`. Keep the repository generic ID type, method parameters, DTOs, and model IDs aligned; do not reintroduce `Long` in one layer.
- Keep repository interfaces focused on persistence. Use Spring Data derived methods based on actual entity properties, such as `existsByEmail` and email lookup methods.
- Normalize owner emails with `trim().toLowerCase(Locale.ROOT)` before duplicate checks, lookup, and storage.
- Use the injected `PasswordEncoder` bean for password hashing and matching. Do not instantiate `BCryptPasswordEncoder` inside business methods.
- Do not compare a newly encoded raw password with a stored hash; use `PasswordEncoder.matches(rawPassword, storedHash)` for sign-in.
- Server-controlled fields must not come from signup requests: IDs, timestamps, verification state/tokens, roles, and password hashes are generated or changed by the server.
- Bind JSON request DTOs explicitly at controller boundaries and apply `@Valid` where DTO constraints exist.
- Keep API DTOs separate from Mongo documents. Never return `Owner` directly because it contains sensitive authentication fields.
- Owner email uniqueness needs both an application-level check and a MongoDB unique index; handle duplicate-key failures from concurrent signup requests.
- Use explicit response DTOs instead of `ResponseEntity<?>` when the success payload has a stable shape. Keep error mapping consistent with the current explicit status conventions (`201`, `404`, `409`, and `400`) until centralized exception handling is introduced.
- `Showtime.price` and `Payment.amount` use `BigDecimal`; keep monetary values consistent and carry currency information when implementing payment or pricing behavior.
- `PaymentStatus` models payment lifecycle states; do not replace booking/payment lifecycle state with a boolean.
- The current MVP tracks reservation capacity rather than exact seat identifiers. Do not add seat-level locking or assume `Booking.seatBooked` is authoritative without an explicit scope decision.
- When adding a public endpoint, update `SecurityConfig` deliberately and review both authentication and owner-resource authorization.
- Keep Mongo URI, security credentials, future JWT keys, and other environment-specific secrets out of source control; use environment variables.
- Preserve the current domain lifecycle fields: `PaymentStatus` represents payment state, `Booking` links a user to a showtime/payment, and `Ticket` links back to a booking. Do not collapse these relationships into a single boolean or nested Mongo object graph.

## Current implementation details to verify before extending

- Confirm that `OwnerRepo extends MongoRepository<Owner, String>` matches `Owner.id` and all owner service/controller parameters.
- Signup/sign-in are still evolving. Email verification and JWT access tokens are planned, not implemented.
- The current `/api/owner/me` route temporarily accepts an owner ID; the eventual authenticated implementation should resolve the owner from the security principal/token instead.
- The user controller currently exposes `/api/user/sigin` (spelled with one `n`); preserve or migrate this route deliberately because changing it is an API compatibility decision.
- User authentication currently accepts several server-owned fields from the signup DTO and has evolving response/status behavior; review the DTO and service before extending it rather than copying the owner flow mechanically.
- Review `pom.xml` before adding persistence code: MongoDB is the chosen persistence technology, while JPA is not part of the document model.
