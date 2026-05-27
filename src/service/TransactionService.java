package service;

import db.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionService {

    public static void processLoan(
            int bookId,
            int memberId
    ) {

        Connection conn = null;

        try {

            conn = ConnectionManager.getConnection();

            conn.setAutoCommit(false);

            String checkBook =
                    "SELECT available FROM Books WHERE book_id=?";

            try (PreparedStatement ps =
                         conn.prepareStatement(checkBook)) {

                ps.setInt(1, bookId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    boolean available =
                            rs.getBoolean("available");

                    if (!available) {

                        throw new SQLException(
                                "Book unavailable"
                        );
                    }

                } else {

                    throw new SQLException(
                            "Book not found"
                    );
                }
            }

            String updateBook =
                    "UPDATE Books SET available=false WHERE book_id=?";

            try (PreparedStatement ps =
                         conn.prepareStatement(updateBook)) {

                ps.setInt(1, bookId);

                ps.executeUpdate();
            }

            String insertLoan =
                    "INSERT INTO Loans(member_id,book_id) VALUES(?,?)";

            try (PreparedStatement ps =
                         conn.prepareStatement(insertLoan)) {

                ps.setInt(1, memberId);
                ps.setInt(2, bookId);

                ps.executeUpdate();
            }

            String updateMember =
                    "UPDATE Members " +
                    "SET active_loans = active_loans + 1 " +
                    "WHERE member_id=?";

            try (PreparedStatement ps =
                         conn.prepareStatement(updateMember)) {

                ps.setInt(1, memberId);

                ps.executeUpdate();
            }

            conn.commit();

            System.out.println(
                    "Loan processed successfully."
            );

        } catch (SQLException e) {

            try {

                if (conn != null) {

                    conn.rollback();
                }

            } catch (SQLException ex) {

                System.out.println(
                        "Rollback failed: " +
                        ex.getMessage()
                );
            }

            System.out.println(
                    "Transaction failed: " +
                    e.getMessage()
            );

        } finally {

            try {

                if (conn != null) {

                    conn.setAutoCommit(true);

                    conn.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    public static void returnBook(
            int loanId,
            int bookId,
            int memberId
    ) {

        Connection conn = null;

        try {

            conn = ConnectionManager.getConnection();

            conn.setAutoCommit(false);

            String returnLoan =
                    "UPDATE Loans " +
                    "SET return_date=CURRENT_TIMESTAMP " +
                    "WHERE loan_id=?";

            try (PreparedStatement ps =
                         conn.prepareStatement(returnLoan)) {

                ps.setInt(1, loanId);

                ps.executeUpdate();
            }

            String updateBook =
                    "UPDATE Books SET available=true WHERE book_id=?";

            try (PreparedStatement ps =
                         conn.prepareStatement(updateBook)) {

                ps.setInt(1, bookId);

                ps.executeUpdate();
            }

            String updateMember =
                    "UPDATE Members " +
                    "SET active_loans = active_loans - 1 " +
                    "WHERE member_id=?";

            try (PreparedStatement ps =
                         conn.prepareStatement(updateMember)) {

                ps.setInt(1, memberId);

                ps.executeUpdate();
            }

            conn.commit();

            System.out.println(
                    "Book returned successfully."
            );

        } catch (SQLException e) {

            try {

                if (conn != null) {

                    conn.rollback();
                }

            } catch (SQLException ex) {

                System.out.println(
                        "Rollback failed: " +
                        ex.getMessage()
                );
            }

            System.out.println(
                    "Transaction failed: " +
                    e.getMessage()
            );

        } finally {

            try {

                if (conn != null) {

                    conn.setAutoCommit(true);

                    conn.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
}