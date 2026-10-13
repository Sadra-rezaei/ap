package ap.projects.finalproject;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import ap.projects.finalproject.database.Database;
import ap.projects.finalproject.models.Admin;
import ap.projects.finalproject.models.Book;
import ap.projects.finalproject.services.AuthService;
import ap.projects.finalproject.services.LibraryService;
import ap.projects.finalproject.ui.Menu;

/**
 * Console entry point of the Library Management System.
 *
 * <p>Wire-up order: connect to PostgreSQL (db.properties), make sure tables
 * exist, then build the service layer on top of the database and run the
 * main menu loop.</p>
 *
 * <p>The main loop dispatches to four sub-menus (guest, student, employee,
 * admin). Each sub-menu is its own labeled loop so "99/5 = back" returns to
 * the main menu without killing the program.</p>
 */
public class Main {
    /** Single shared scanner for all console input. */
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Data layer: load connection settings, connect and create tables on first run.
        Database db = Database.fromProperties();
        db.initDatabase();

        // Service layer: authentication and library business logic.
        AuthService auth = new AuthService(db);
        LibraryService lib = new LibraryService(db);
        Menu menu = new Menu();

        // Hard-coded admin account (per project spec, admin is not stored in DB).
        Admin admin = new Admin("Sadra", "1234");

        // Main menu loop: 1=guest, 2=student, 3=employee, 4=admin, 5=exit.
        label: while (true) {
            menu.showLibraryMenu();
            String c = sc.nextLine();

            switch (c) {
                case "1": { // guest: read-only browsing and search
                    System.out.println(lib.guestStats());

                    lable1: while (true) {
                        menu.showGuestMenu();
                        String cmd = sc.nextLine();

                        switch (cmd) {
                            case "1" -> printAllBooks(lib);
                            case "2" -> {
                                System.out.print("search title: ");
                                String tempTitle = sc.nextLine();
                                lib.guestSearchByTitle(tempTitle).forEach(System.out::println);
                            }
                            case "99" -> {
                                break lable1;
                            } // Back
                            default -> System.out.println("invalid option");
                        }
                    }

                    break;
                }
                case "2": { // student: register or log in, then the student menu loop
                    menu.showStudentLoginMenu();
                    String r = sc.nextLine();

                    switch (r) {
                        case "1" -> {
                            System.out.print("user: ");
                            String u = sc.nextLine();
                            System.out.print("pass: ");
                            String p = sc.nextLine();
                            System.out.println(auth.registerStudent(u, p) ? "registered" : "exists");
                        }
                        case "2" -> {
                            System.out.print("user: ");
                            String u = sc.nextLine();
                            System.out.print("pass: ");
                            String p = sc.nextLine();
                            if (auth.loginStudent(u, p) == null) {
                                System.out.println("invalid");
                                break;
                            }

                            System.out.println("welcome " + u);

                            // Student session loop until "99 = back".
                            lable1: while (true) {
                                menu.showStudentMenu();
                                String cmd = sc.nextLine();

                                switch (cmd) {
                                    case "1" -> printAllBooks(lib);

                                    case "2" -> {
                                        // Search by title, then optionally request a loan for one of the hits.
                                        System.out.print("search title: ");
                                        String tq = sc.nextLine();
                                        List<Book> res = lib.searchBooks(tq, "", null);
                                        res.forEach(System.out::println);
                                        System.out.print("want request loan? bookId or empty: ");
                                        String bid = sc.nextLine();
                                        if (!bid.trim().isEmpty()) {
                                            System.out.print("return date yyyy-mm-dd: ");
                                            LocalDate en = readDate();
                                            // Loan always starts today; createLoan validates book + dates.
                                            String loanId = lib.createLoan(bid, u, LocalDate.now(), en);
                                            System.out.println(loanId != null
                                                    ? "loan created: " + loanId
                                                    : "loan request failed (check bookId, availability and dates)");
                                        }
                                    }
                                    case "3" -> {
                                        // Returning is ownership-checked inside the service.
                                        System.out.print("loanId: ");
                                        String lid = sc.nextLine();
                                        System.out.println(lib.returnBook(lid, u)
                                                ? "returned"
                                                : "return failed (check loanId, ownership and duplicate return)");
                                    }
                                    case "99" -> {
                                        break lable1;
                                    }
                                    default -> System.out.println("invalid option");
                                }
                            }
                        }
                        case "99" -> {
                        } // Back to main page
                        default -> System.out.println("invalid option");
                    }

                    break;
                }
                case "3": { // employee: log in, then manage books and loans
                    System.out.print("user: ");
                    String u = sc.nextLine();
                    System.out.print("pass: ");
                    String p = sc.nextLine();
                    if (auth.loginEmployee(u, p) == null) {
                        System.out.println("bad");
                        break;
                    }
                    System.out.println("emp:" + u);

                    // Employee session loop until "5 = back".
                    label1: while (true) {
                        menu.showEmployeeMenu();
                        String cmd = sc.nextLine();

                        switch (cmd) {
                            case "1":
                                System.out.print("id: ");
                                String tempId = sc.nextLine();
                                System.out.print("title: ");
                                String tempTitle = sc.nextLine();
                                System.out.print("author: ");
                                String author = sc.nextLine();
                                System.out.print("year: ");
                                int y = readInt();
                                // Book is credited to the employee who added it (used in reports).
                                System.out.println(lib.addBook(tempId, tempTitle, author, y, u)
                                        ? "added"
                                        : "book id already exists");
                                break;
                            case "2": {
                                System.out.print("loanId: ");
                                String lid = sc.nextLine();
                                System.out.println(lib.approveLoan(lid, u));
                                break;
                            }
                            case "3": {
                                System.out.print("loanId: ");
                                String lid = sc.nextLine();
                                System.out.println(lib.receiveBook(lid)
                                        ? "received"
                                        : "receive failed (check loanId, approval and duplicate receive)");
                                break;
                            }
                            case "4":
                                System.out.print("student username: ");
                                String sname = sc.nextLine();
                                System.out.println(lib.studentLoanReport(sname));
                                break;
                            case "5":
                                break label1;
                            default:
                                System.out.println("invalid option");
                        }
                    }

                    break;
                }
                case "4": { // admin: log in, then manage employees and view reports
                    System.out.print("Hi Sadra\npassword: ");
                    String p = sc.nextLine();
                    if (!p.equals(admin.getPassword())) {
                        System.out.println("wrong password");
                        break;
                    }

                    // Admin session loop until "5 = back".
                    label1: while (true) {
                        menu.showAdminMenu();
                        String cmd = sc.nextLine();

                        switch (cmd) {
                            case "1":
                                System.out.print("user: ");
                                String u = sc.nextLine();
                                System.out.print("password: ");
                                String s = sc.nextLine();
                                System.out.println(lib.addEmployee(u, s) ? "added" : "employee already exists");
                                break;
                            case "2":
                                System.out.print("employee username: ");
                                String tempId = sc.nextLine();
                                System.out.println(lib.employeeReport(tempId));
                                break;
                            case "3":
                                System.out.println(lib.loanStats());
                                break;
                            case "4":
                                System.out.println(lib.studentStats());
                                break;
                            case "5":
                                break label1;
                            default:
                                System.out.println("invalid option");
                        }
                    }

                    break;
                }
                case "5":
                    break label;
                default:
                    System.out.println("invalid option");
                    break;
            }
        }
        sc.close();
    }

    /** Prints every book in the catalog, one blank line between entries. */
    private static void printAllBooks(LibraryService lib) {
        lib.allBooks().forEach(b -> System.out.println(b + "\n"));
    }

    /**
     * Reads a whole line from the console and parses it as an int,
     * re-prompting until the input is a valid number.
     */
    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("enter a valid number: ");
            }
        }
    }

    /**
     * Reads a whole line from the console and parses it as an ISO date
     * (yyyy-mm-dd), re-prompting until the input is a valid date.
     */
    private static LocalDate readDate() {
        while (true) {
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.print("enter date as yyyy-mm-dd: ");
            }
        }
    }
}
