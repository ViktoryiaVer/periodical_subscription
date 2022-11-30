# Periodical subscription project

## Backend: Java 11, Spring Data (DB: PostgreSQL), Spring AOP, Spring MVC, Spring Boot, Spring Security
## Frontend: HTML, CSS, JS, Bootstrap

Main functionality:
An anonymous user can:
- view a catalog of periodicals
- filter and search for a specific periodical by keyword
- view periodical detail
- add periodical to cart and modify data in the cart
- login/logout or sign up
- change language of the app (EN, DE, RU)

A user with the role "READER" can additionally:
- view their profile and update it
- view their subscriptions and cancel them
- register subscriptions after adding them to the cart

A user with the role "ADMIN" can additionally:
- manage the periodical catalog: add a new one, update and delete a periodical
- mark a periodical as temporarily unavailable
- view (also search by a keyword), update and delete all users
- view (also filter by status and search by a keyword), confirm or reject, mark as completed all subscriptions
- view (also filter and search by a keyword), register and update payments for subscriptions

Some additional libraries, technologies and tools used in the project:
- Maven, JSP (JSTL, EL), Mapstruct, Bean Validation API, Log4j2, JUnit, Mockito, Flyway, Docker, Swagger
