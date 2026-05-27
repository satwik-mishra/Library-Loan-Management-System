# Library Loan System

A console-based Library Loan Management System developed using Java JDBC and Apache Derby database.

This project demonstrates practical implementation of database connectivity, transaction management, benchmarking, indexing, and query optimization using a layered Java application structure.

# Features

## Library Operations

- Add members
- Add books
- Issue books to members
- Return books
- View all books
- View all members
- View active loans

## Database Features

- JDBC connectivity
- Apache Derby embedded database
- Transactions using commit and rollback
- Foreign key constraints
- Unique constraints
- Indexed queries
- PreparedStatement usage
- Layered architecture

## Benchmarking Features

- Individual insert vs batch insert benchmarking
- Transaction granularity benchmark
- Indexed lookup benchmark
- Statement vs PreparedStatement comparison
- CSV performance report generation

# Technologies Used

- Java 21
- JDBC API
- Apache Derby 10.17
- Eclipse IDE

# Project Structure

```text
src/
 ├── app/
 │    └── MainApp.java
 │
 ├── benchmark/
 │    └── PerformanceEvaluator.java
 │
 ├── db/
 │    ├── ConnectionManager.java
 │    └── DatabaseInitializer.java
 │
 ├── model/
 │    ├── Book.java
 │    ├── Loan.java
 │    └── Member.java
 │
 └── service/
      ├── BusinessLogic.java
      └── TransactionService.java
```

# Database Design

## Members Table

Stores member information.

| Column       | Description          |
| ------------ | -------------------- |
| member_id    | Primary key          |
| name         | Member name          |
| email        | Unique email         |
| active_loans | Current active loans |

## Books Table

Stores book information.

| Column    | Description         |
| --------- | ------------------- |
| book_id   | Primary key         |
| title     | Book title          |
| isbn      | Unique ISBN         |
| available | Availability status |

## Loans Table

Stores loan transactions.

| Column      | Description      |
| ----------- | ---------------- |
| loan_id     | Primary key      |
| member_id   | Foreign key      |
| book_id     | Foreign key      |
| loan_date   | Loan timestamp   |
| return_date | Return timestamp |

## BenchmarkBooks Table

Used only for benchmarking so that benchmark data does not affect actual library records.

# Transaction Workflow

The loan process uses JDBC transactions to maintain consistency.

Steps performed during loan processing:

1. Check whether the book is available
2. Mark the book as unavailable
3. Insert a loan record
4. Update member active loan count
5. Commit transaction

If any operation fails, rollback is executed.

```java
conn.setAutoCommit(false);
conn.commit();
conn.rollback();
```

This demonstrates ACID transaction properties.

# Benchmarking Features

## Insert Strategy Benchmark

Compares:

- Individual inserts using `executeUpdate()`
- Batch inserts using `addBatch()` and `executeBatch()`

## Transaction Granularity Benchmark

Compares:

- Commit after every operation
- Single commit after multiple operations

## Indexed Lookup Benchmark

Measures query performance using indexed loan lookups.

## Statement vs PreparedStatement

Compares execution performance between:

- Statement
- PreparedStatement

PreparedStatement provides:

- better security
- reusable execution plans
- cleaner parameter handling

# Setup Instructions

## 1. Install Java

Install Java 21 or higher.

## 2. Download Apache Derby

Download Apache Derby binary distribution.

Extract the archive.

## 3. Add Derby JARs to Eclipse

Add these JAR files to the project build path:

- derby.jar
- derbytools.jar

## 4. Import Project into Eclipse

Open Eclipse and import the project.

# Running the Project

Run:

```text
MainApp.java
```

using:

```text
Run As → Java Application
```

The database will automatically initialize during first execution.

# Sample CLI Session

```text
===== Library Loan System =====

1. Add Member
2. Add Book
3. Process Loan
4. Return Book
5. View Active Loans
6. View Books
7. View Members
8. Benchmark Insert Strategies
9. Statement vs PreparedStatement
10. Transaction Granularity Benchmark
11. Indexed Lookup Benchmark
12. Exit
```

Example:

```text
Choose option: 1
Enter member name: Abhilash
Enter email: abhilash@example.com
```

# CSV Report Generation

Benchmark results are automatically exported to:

```text
benchmark_report.csv
```

Example:

```csv
Operation,Records,IndividualInsert(ms),BatchInsert(ms)
Insert Strategy,1000,520.12,95.44
```

# Performance Findings

- Batch inserts performed significantly faster than individual inserts.
- Single transaction commits performed better than committing after every operation.
- Indexed lookups improved query performance.
- PreparedStatement execution was more efficient and secure compared to regular Statement.

# Concepts Demonstrated

- JDBC programming
- Transaction management
- ACID properties
- Commit and rollback
- PreparedStatement
- Batch processing
- Query optimization
- Indexing
- Benchmarking
- CSV report generation
- Relational database design

# Limitations

- Console-based UI only
- No authentication system
- Embedded single-user database setup
- Savepoint-based partial rollback not implemented

# Future Improvements

- GUI implementation
- Multi-user support
- Authentication system
- REST API integration
- Savepoint support
- Advanced reporting dashboard

# Notes

- Apache Derby runs in embedded mode.
- The database is automatically created during first execution.
- If schema changes are made, delete the `libraryDB` folder and rerun the application.

# Author

Pratyush Mohanty

