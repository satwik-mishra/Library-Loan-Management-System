package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                Statement statement =
                        conn.createStatement()
        ) {

            // MEMBERS TABLE

            String membersTable =

                    "CREATE TABLE Members (" +

                    "member_id INT GENERATED ALWAYS " +
                    "AS IDENTITY PRIMARY KEY, " +

                    "name VARCHAR(100), " +

                    "email VARCHAR(100) UNIQUE, " +

                    "active_loans INT DEFAULT 0" +

                    ")";

            // BOOKS TABLE

            String booksTable =

                    "CREATE TABLE Books (" +

                    "book_id INT GENERATED ALWAYS " +
                    "AS IDENTITY PRIMARY KEY, " +

                    "title VARCHAR(255), " +

                    "isbn VARCHAR(50) UNIQUE, " +

                    "available BOOLEAN DEFAULT TRUE" +

                    ")";

            // LOANS TABLE

            String loansTable =

                    "CREATE TABLE Loans (" +

                    "loan_id INT GENERATED ALWAYS " +
                    "AS IDENTITY PRIMARY KEY, " +

                    "member_id INT REFERENCES Members(member_id), " +

                    "book_id INT REFERENCES Books(book_id), " +

                    "loan_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +

                    "return_date TIMESTAMP" +

                    ")";

            // BENCHMARK BOOKS TABLE

            String benchmarkBooksTable =

                    "CREATE TABLE BenchmarkBooks (" +

                    "book_id INT GENERATED ALWAYS " +
                    "AS IDENTITY PRIMARY KEY, " +

                    "title VARCHAR(255), " +

                    "isbn VARCHAR(255) UNIQUE" +

                    ")";

            // CREATE TABLES

            statement.executeUpdate(
                    membersTable
            );

            statement.executeUpdate(
                    booksTable
            );

            statement.executeUpdate(
                    loansTable
            );

            statement.executeUpdate(
                    benchmarkBooksTable
            );

            // INDEXES
            
            statement.executeUpdate(

                    "CREATE INDEX idx_member " +
                    "ON Loans(member_id)"
            );

            statement.executeUpdate(

                    "CREATE INDEX idx_book " +
                    "ON Loans(book_id)"
            );

            System.out.println(
                    "Database initialized successfully."
            );

        } catch (SQLException e) {

            if (
                    e.getSQLState()
                            .equals("X0Y32")
            ) {

                System.out.println(
                        "Tables already exist."
                );

            } else {

                e.printStackTrace();
            }
        }
    }
}