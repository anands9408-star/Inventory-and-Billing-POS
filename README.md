# Inventory and Billing (POS) System

Java Swing + JDBC + MySQL starter project based on the supplied project report.

## Included
- Product listing, searching, and product registration
- Product validation and basic Swing GUI
- JDBC persistence using prepared statements
- MySQL schema for products, sales, and sale items
- Separate model, DAO, service, and UI packages

**Status:** This is a starter implementation, not a fully completed version of every feature in the report. Checkout/cart processing, transactional stock deduction, sales history, receipt export, and actual test results remain to be completed and tested.

## Requirements
- JDK 21+
- Maven 3.9+
- MySQL 8.x

## Setup
1. Run `database/schema.sql` in MySQL.
2. Set `POS_DB_URL` (default `jdbc:mysql://localhost:3306/pos_db`), `POS_DB_USER` (default `root`), and `POS_DB_PASSWORD` in your environment.
3. Run `mvn clean package`, then `mvn exec:java`.

Do not commit database credentials.

## Layout
- `src/main/java/com/college/pos/model` — domain classes
- `src/main/java/com/college/pos/dao` — JDBC access
- `src/main/java/com/college/pos/service` — validation/business logic
- `src/main/java/com/college/pos/ui` — Swing UI
- `database/schema.sql` — database setup
- `docs/project-report.pdf` — supplied report, if included
