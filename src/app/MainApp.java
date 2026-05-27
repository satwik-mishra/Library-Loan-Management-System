package app;

import benchmark.PerformanceEvaluator;
import db.ConnectionManager;
import db.DatabaseInitializer;
import service.BusinessLogic;
import service.TransactionService;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println(
                    "\n===== Library Loan System ====="
            );

            System.out.println("1. Add Member");
            System.out.println("2. Add Book");
            System.out.println("3. Process Loan");
            System.out.println("4. Return Book");
            System.out.println("5. View Active Loans");
            System.out.println("6. View Books");
            System.out.println("7. View Members");
            System.out.println("8. Benchmark Insert Strategies");
            System.out.println("9. Statement vs PreparedStatement");
            System.out.println("10. Transaction Granularity Benchmark");
            System.out.println("11. Indexed Lookup Benchmark");
            System.out.println("12. Exit");

            System.out.print(
                    "Choose option: "
            );

            if (!sc.hasNextInt()) {

                System.out.println(
                        "Please enter a valid number."
                );

                sc.nextLine();

                continue;
            }

            int choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {

                // ADD MEMBER

                case 1 -> {

                    System.out.print(
                            "Enter member name: "
                    );

                    String name = sc.nextLine();

                    System.out.print(
                            "Enter email: "
                    );

                    String email = sc.nextLine();

                    BusinessLogic.addMember(
                            name,
                            email
                    );
                }

                // ADD BOOK

                case 2 -> {

                    System.out.print(
                            "Enter book title: "
                    );

                    String title = sc.nextLine();

                    System.out.print(
                            "Enter ISBN: "
                    );

                    String isbn = sc.nextLine();

                    BusinessLogic.addBook(
                            title,
                            isbn
                    );
                }

                // PROCESS LOAN

                case 3 -> {

                    System.out.print(
                            "Enter book ID: "
                    );

                    int bookId = sc.nextInt();

                    System.out.print(
                            "Enter member ID: "
                    );

                    int memberId = sc.nextInt();

                    TransactionService.processLoan(
                            bookId,
                            memberId
                    );
                }

                // RETURN BOOK

                case 4 -> {

                    System.out.print(
                            "Enter loan ID: "
                    );

                    int loanId = sc.nextInt();

                    System.out.print(
                            "Enter book ID: "
                    );

                    int bookId = sc.nextInt();

                    System.out.print(
                            "Enter member ID: "
                    );

                    int memberId = sc.nextInt();

                    TransactionService.returnBook(
                            loanId,
                            bookId,
                            memberId
                    );
                }

                // VIEW ACTIVE LOANS

                case 5 -> {

                    BusinessLogic.viewActiveLoans();
                }

                // VIEW BOOKS

                case 6 -> {

                    BusinessLogic.viewBooks();
                }

                // VIEW MEMBERS

                case 7 -> {

                    BusinessLogic.viewMembers();
                }

                // INSERT STRATEGY BENCHMARK

                case 8 -> {

                    System.out.print(
                            "Enter record count: "
                    );

                    int count = sc.nextInt();

                    PerformanceEvaluator
                            .benchmarkInsertStrategies(count);
                }

                // STATEMENT VS PREPARED STATEMENT

                case 9 -> {

                    PerformanceEvaluator
                            .statementVsPreparedStatement();
                }

                // TRANSACTION GRANULARITY

                case 10 -> {

                    PerformanceEvaluator
                            .benchmarkTransactionGranularity();
                }

                // INDEXED LOOKUP BENCHMARK
                
                case 11 -> {

                    System.out.print(
                            "Enter member ID: "
                    );

                    int memberId = sc.nextInt();

                    PerformanceEvaluator
                            .indexedLookupBenchmark(memberId);
                }

                // EXIT

                case 12 -> {

                    ConnectionManager.shutdown();

                    System.out.println(
                            "Application closed."
                    );

                    System.exit(0);
                }

                // INVALID INPUT

                default -> {

                    System.out.println(
                            "Invalid choice."
                    );
                }
            }
        }
    }
}