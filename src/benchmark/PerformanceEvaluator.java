package benchmark;

import db.ConnectionManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class PerformanceEvaluator {

    // WARM UP

    private static void warmUp() {

        for (int i = 0; i < 1000; i++) {

            Math.sqrt(i);
        }
    }

    // INSERT STRATEGY BENCHMARK

    public static void benchmarkInsertStrategies(
            int recordCount
    ) {

        warmUp();

        double individual =
                averageIndividualInsert(recordCount);

        double batch =
                averageBatchInsert(recordCount);

        printReport(
                "Insert Strategy",
                recordCount,
                individual,
                batch
        );

        exportCSV(
                "Insert Strategy",
                recordCount,
                individual,
                batch
        );
    }

    // AVERAGE INDIVIDUAL INSERT

    private static double averageIndividualInsert(
            int recordCount
    ) {

        List<Double> timings =
                new ArrayList<>();

        for (int run = 0; run < 3; run++) {

            timings.add(
                    individualInsert(recordCount)
            );
        }

        return average(timings);
    }

    // AVERAGE BATCH INSERT

    private static double averageBatchInsert(
            int recordCount
    ) {

        List<Double> timings =
                new ArrayList<>();

        for (int run = 0; run < 3; run++) {

            timings.add(
                    batchInsert(recordCount)
            );
        }

        return average(timings);
    }

    // INDIVIDUAL INSERT

    private static double individualInsert(
            int recordCount
    ) {

        String sql =

                "INSERT INTO BenchmarkBooks(title,isbn) " +
                "VALUES(?,?)";

        long start = System.nanoTime();

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            for (int i = 0; i < recordCount; i++) {

                ps.setString(
                        1,
                        "Book_" + i
                );

                ps.setString(
                        2,
                        "ISBN_" +
                        i +
                        System.nanoTime()
                );

                ps.executeUpdate();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        long end = System.nanoTime();

        return (end - start) / 1_000_000.0;
    }

    // BATCH INSERT

    private static double batchInsert(
            int recordCount
    ) {

        String sql =

                "INSERT INTO BenchmarkBooks(title,isbn) " +
                "VALUES(?,?)";

        long start = System.nanoTime();

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            conn.setAutoCommit(false);

            for (int i = 0; i < recordCount; i++) {

                ps.setString(
                        1,
                        "BatchBook_" + i
                );

                ps.setString(
                        2,
                        "BATCH_" +
                        i +
                        System.nanoTime()
                );

                ps.addBatch();
            }

            ps.executeBatch();

            conn.commit();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        long end = System.nanoTime();

        return (end - start) / 1_000_000.0;
    }

    // TRANSACTION GRANULARITY

    public static void benchmarkTransactionGranularity() {

        benchmarkPerOperationCommit();

        benchmarkBatchCommit();
    }

    // PER OPERATION COMMIT

    private static void benchmarkPerOperationCommit() {

        String sql =

                "INSERT INTO BenchmarkBooks(title,isbn) " +
                "VALUES(?,?)";

        long start = System.nanoTime();

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            for (int i = 0; i < 100; i++) {

                conn.setAutoCommit(false);

                ps.setString(
                        1,
                        "CommitBook_" + i
                );

                ps.setString(
                        2,
                        "COMMIT_" +
                        i +
                        System.nanoTime()
                );

                ps.executeUpdate();

                conn.commit();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        long end = System.nanoTime();

        System.out.println(
                "\nPer-operation commit time: " +
                ((end - start) / 1_000_000.0) +
                " ms"
        );
    }

    // BATCH COMMIT

    private static void benchmarkBatchCommit() {

        String sql =

                "INSERT INTO BenchmarkBooks(title,isbn) " +
                "VALUES(?,?)";

        long start = System.nanoTime();

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            conn.setAutoCommit(false);

            for (int i = 0; i < 100; i++) {

                ps.setString(
                        1,
                        "BatchCommitBook_" + i
                );

                ps.setString(
                        2,
                        "BCOMMIT_" +
                        i +
                        System.nanoTime()
                );

                ps.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        long end = System.nanoTime();

        System.out.println(
                "Batch commit time: " +
                ((end - start) / 1_000_000.0) +
                " ms"
        );
    }

    // INDEXED LOOKUP BENCHMARK

    public static void indexedLookupBenchmark(
            int memberId
    ) {

        String query =

                "SELECT * FROM Loans " +
                "WHERE member_id=?";

        long start = System.nanoTime();

        try (
                Connection conn =
                        ConnectionManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            ps.setInt(1, memberId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        long end = System.nanoTime();

        System.out.println(
                "\nIndexed lookup time: " +
                ((end - start) / 1_000_000.0) +
                " ms"
        );
    }

    // STATEMENT VS PREPARED STATEMENT

    public static void statementVsPreparedStatement() {

        try (
                Connection conn =
                        ConnectionManager.getConnection()
        ) {

            long start1 = System.nanoTime();

            Statement st =
                    conn.createStatement();

            st.executeQuery(

                    "SELECT * FROM Books " +
                    "WHERE book_id=1"
            );

            long end1 = System.nanoTime();

            long start2 = System.nanoTime();

            PreparedStatement ps =

                    conn.prepareStatement(

                            "SELECT * FROM Books " +
                            "WHERE book_id=?"
                    );

            ps.setInt(1, 1);

            ps.executeQuery();

            long end2 = System.nanoTime();

            System.out.println(
                    "\nStatement Time: " +
                    ((end1 - start1) / 1_000_000.0) +
                    " ms"
            );

            System.out.println(
                    "PreparedStatement Time: " +
                    ((end2 - start2) / 1_000_000.0) +
                    " ms"
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // AVERAGE

    private static double average(
            List<Double> list
    ) {

        double sum = 0;

        for (double d : list) {

            sum += d;
        }

        return sum / list.size();
    }

    // PRINT REPORT

    private static void printReport(
            String operation,
            int records,
            double individual,
            double batch
    ) {

        System.out.println(
                "\n===== PERFORMANCE REPORT ====="
        );

        System.out.println(
                "Operation: " + operation
        );

        System.out.println(
                "Records: " + records
        );

        System.out.println(
                "Average Individual Insert: " +
                individual +
                " ms"
        );

        System.out.println(
                "Average Batch Insert: " +
                batch +
                " ms"
        );

        System.out.println(
                "Individual Throughput: " +
                (records / (individual / 1000.0)) +
                " ops/sec"
        );

        System.out.println(
                "Batch Throughput: " +
                (records / (batch / 1000.0)) +
                " ops/sec"
        );
    }

    // CSV EXPORT
    
    private static void exportCSV(
            String operation,
            int records,
            double individual,
            double batch
    ) {

        try {

            File file =
                    new File(
                            "benchmark_report.csv"
                    );

            boolean exists =
                    file.exists();

            FileWriter writer =
                    new FileWriter(file, true);

            if (!exists) {

                writer.write(
                        "Operation," +
                        "Records," +
                        "IndividualInsert(ms)," +
                        "BatchInsert(ms)\n"
                );
            }

            writer.write(
                    operation + "," +
                    records + "," +
                    individual + "," +
                    batch + "\n"
            );

            writer.close();

            System.out.println(
                    "\nCSV report generated successfully."
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}