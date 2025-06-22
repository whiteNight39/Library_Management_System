# Library Management System

A backend Java-based application for managing library operations such as book borrowing, returning, availability tracking, user credit scoring, and overdue penalties. Built with Spring Boot and JDBC.

---

## Features

- **Book Management**: Create, update, delete, and query books by ID, availability, or custom filters.
- **User Management**: Register users, update details, track credit scores, and handle bans.
- **Borrowing System**: Borrow and return books with automatic due date calculation.
- **Scheduled Tasks**:
  - Auto-mark overdue borrow records.
  - Daily credit penalty system based on days overdue.
- **Auto-ban System**: Users with credit <= -10 are automatically banned.
- **Trigger Logic**: SQL trigger auto-updates book status based on quantity.

---

## Tech Stack

- **Java** 21
- **Spring Boot** (Core, Web, Scheduling)
- **JDBC Template** (with Named Parameters)
- **MSSQL / PostgreSQL** (tested with MSSQL)
- **Lombok**
- **MapStruct** (for DTO <-> Entity mapping)

---

## Project Structure
src/
|----controller/ 	# REST controllers
|----service/ 		# Business logic layer
|----repository/ 	# DB interfaces + implementations
| 	|---query/ 		# SQL queries as string constants
|----- mapper/ 		# DTO <-> Entity mappers (MapStruct + RowMapper)
|----model/
|   |---entity/ 	# Entity classes
| 	|---requests/ 	# DTOs for incoming API requests
|----scheduler/ 	# Scheduled jobs (overdue check & penalties)
|----config/ 		# App-level configuration (if needed)
 
---

## Sample API Endpoints

| Method | Endpoint                       | Description            |
|--------|--------------------------------|------------------------|
| POST   | `/book/create-book`            | Add new book           |
| PATCH  | `/book/update-book`            | Update existing book   |
| DELETE | `/book/delete-book{bookId}`    | Remove a book          |
| GET    | `/book/get-book-by-id{bookId}` | Fetch book by ID       |
| GET    | `/user/get-all-users`          | List all users         |
| POST   | `/borrow/borrow-book`          | Borrow a book          |
| PATCH  | `/borrow/return-book`          | Return a borrowed book |

---

## Scheduled Jobs

- `OverdueStatusScheduler` - runs **daily at 1 AM**, updates `borrowStatus` to `OVERDUE` if due date has passed.
- `PenaltyDeductionScheduler` - runs **daily at 2 AM**, applies staged credit penalties for overdue books.

---

## Business Rules

- Users start with a neutral credit score.
- Credit deductions follow a **grace + staged penalty** model.
- Users are auto-banned when score drops to **-10 or lower**.
- Deleted or banned users cannot borrow books.

---

## Getting Started

1. Clone the repo
   ```bash
   git clone https://github.com/whiteNight39/Library_Management_System.git
2. Configure your DB and application properties
3. Run the Spring Boot app

## Author
Fredrick (aka whiteNight)
Backend Developer (Java) | Physics enthusiast | Builder of clean systems

## License
This project is licensed under the MIT License.
Feel free to fork and modify.

