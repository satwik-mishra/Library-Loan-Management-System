package service;

import db.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessLogic {

    // ADD MEMBER

    public static void addMember(
            String name,
            String email
    ) {

        String sql =
                "INSERT INTO Members(name,email) VALUES(?,?)";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            ps.setString(1, name);

            ps.setString(2, email);

            ps.executeUpdate();

            System.out.println(
                    "Member added successfully."
            );

        } catch (SQLException e) {

            if (e.getSQLState().equals("23505")) {

                System.out.println(
                        "Email already exists."
                );

            } else {

                e.printStackTrace();
            }
        }
    }

    // ADD BOOK

    public static void addBook(
            String title,
            String isbn
    ) {

        String sql =
                "INSERT INTO Books(title,isbn) VALUES(?,?)";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            ps.setString(1, title);

            ps.setString(2, isbn);

            ps.executeUpdate();

            System.out.println(
                    "Book added successfully."
            );

        } catch (SQLException e) {

            if (e.getSQLState().equals("23505")) {

                System.out.println(
                        "ISBN already exists."
                );

            } else {

                e.printStackTrace();
            }
        }
    }

    // VIEW ACTIVE LOANS

    public static void viewActiveLoans() {

        String sql =
                "SELECT * FROM Loans " +
                "WHERE return_date IS NULL";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println(
                    "\n===== ACTIVE LOANS ====="
            );

            while (rs.next()) {

                System.out.println(
                        "Loan ID: " +
                        rs.getInt("loan_id") +

                        " | Member ID: " +
                        rs.getInt("member_id") +

                        " | Book ID: " +
                        rs.getInt("book_id") +

                        " | Loan Date: " +
                        rs.getTimestamp("loan_date")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // VIEW BOOKS

    public static void viewBooks() {

        String sql =
                "SELECT * FROM Books";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println(
                    "\n===== BOOK LIST ====="
            );

            while (rs.next()) {

                System.out.println(
                        "Book ID: " +
                        rs.getInt("book_id") +

                        " | Title: " +
                        rs.getString("title") +

                        " | ISBN: " +
                        rs.getString("isbn") +

                        " | Available: " +
                        rs.getBoolean("available")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // VIEW MEMBERS

    public static void viewMembers() {

        String sql =
                "SELECT * FROM Members";

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println(
                    "\n===== MEMBER LIST ====="
            );

            while (rs.next()) {

                System.out.println(
                        "Member ID: " +
                        rs.getInt("member_id") +

                        " | Name: " +
                        rs.getString("name") +

                        " | Email: " +
                        rs.getString("email") +

                        " | Active Loans: " +
                        rs.getInt("active_loans")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}