package ap.projects.finalproject;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ap.projects.finalproject.UI.Menu;
import ap.projects.finalproject.dataBase.DataStore;
import ap.projects.finalproject.models.Admin;
import ap.projects.finalproject.models.Book;
import ap.projects.finalproject.models.Employee;
import ap.projects.finalproject.models.Student;
import ap.projects.finalproject.services.AuthService;
import ap.projects.finalproject.services.LibraryService;

public class Main {
    public static void main(String[] args){
        DataStore ds = DataStore.load();
        AuthService auth = new AuthService(ds);
        LibraryService lib = new LibraryService(ds);
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        Admin admin = new Admin("Sadra", "1234");

        label:
        while(true){
            menu.showLibraryMenu();
            String c = sc.nextLine();


            List<Book> res;
            String tempTitle;
            String tempId;
            String cmd;

            switch (c) {
                case "1":
                    System.out.println(lib.guestStats());

                    menu.showGuestMenu();
                    cmd = sc.nextLine();

                    switch (cmd) {
                        case "1" -> {
                            for (Book book: ds.getBooks().values()){
                                System.out.println(book+"\n");
                            }
                        }
                        case "99" -> {} //Back

                        default -> {
                            System.out.print("search title: ");
                            tempTitle = sc.nextLine();
                            res = lib.guestSearchByTitle(tempTitle);
                            res.forEach(System.out::println);
                        }
                    }


                    break;
                case "2":
                    menu.showStudentLoginMenu();
                    String r = sc.nextLine();

                    switch (r) {
                        case "1" -> {
                            System.out.print("user: ");
                            String u = sc.nextLine();
                            System.out.print("pass: ");
                            String p = sc.nextLine();
                            boolean ok = auth.registerStudent(u, p);
                            System.out.println(ok ? "registered" : "exists");
                        }
                        case "99" -> {} //Back to main page

                        default -> {
                            System.out.print("user: ");
                            String u = sc.nextLine();
                            System.out.print("pass: ");
                            String p = sc.nextLine();
                            Student s = auth.loginStudent(u, p);
                            if (s == null) {
                                System.out.println("invalid");
                                continue;
                            }

                            System.out.println("welcome " + u);
                            menu.showStudentMenu();
                            cmd = sc.nextLine();

                            switch (cmd) {
                                case "1" -> {
                                    for (Book book: ds.getBooks().values()){
                                        System.out.println(book+"\n");
                                    }
                                }

                                case "3" -> {
                                    System.out.print("loanId: ");
                                    String lid = sc.nextLine();
                                    System.out.println(lib.returnBook(lid));
                                }
                                case "99" -> {}

                                default -> {
                                    System.out.print("search title? ");
                                    String tq = sc.nextLine();
                                    res = lib.searchBooks(tq, "", null);
                                    res.forEach(System.out::println);
                                    System.out.print("want request loan? bookId or empty: ");
                                    String bid = sc.nextLine();
                                    if (!bid.trim().isEmpty()) {
                                        LocalDate st = LocalDate.now();
                                        System.out.print("return date yyyy-mm-dd: ");
                                        LocalDate en = LocalDate.parse(sc.nextLine());
                                        String loanId = lib.createLoan(bid, u, st, en);
                                        System.out.println("loan created: " + loanId);
                                    }
                                }
                            }


                        }
                    }


                    break;
                case "3": { //employee
                    System.out.print("user: ");
                    String u = sc.nextLine();
                    System.out.print("pass: ");
                    String p = sc.nextLine();
                    Employee e = auth.loginEmployee(u, p);
                    if (e == null) {
                        System.out.println("bad");
                        continue;
                    }
                    System.out.println("emp:" + u);
                    label1:
                    while (true) {
                        menu.showEmployeeMenu();
                        cmd = sc.nextLine();

                        switch (cmd) {
                            case "1":
                                System.out.print("id: ");
                                tempId = sc.nextLine();
                                System.out.print("title: ");
                                tempTitle = sc.nextLine();
                                System.out.print("author: ");
                                String author = sc.nextLine();
                                System.out.print("year: ");
                                int y = Integer.parseInt(sc.nextLine());
                                lib.addBook(tempId, tempTitle, author, y);
                                System.out.println("added");
                                break;
                            case "2": {
                                System.out.print("loanId: ");
                                String lid = sc.nextLine();
                                boolean ok = lib.approveLoan(lid, u);
                                System.out.println("approved? " + ok);
                                break;
                            }
                            case "3": {
                                System.out.print("loanId: ");
                                String lid = sc.nextLine();
                                System.out.println(lib.receiveBook(lid));
                                break;
                            }
                            case "4":
                                System.out.print("student username: ");
                                String sname = sc.nextLine();
                                System.out.println(lib.studentLoanReport(sname));
                                break;
                            case "5":
                                break label1;
                        }
                    }


                    break;
                }
                case "4": { //admin
                    System.out.print("Hi Sadra\npassword: ");
                    String p = sc.nextLine();
                    if (!p.equals(admin.getPassword())) {
                        System.out.println("wrong password");
                        break label;
                    }
                    label1:
                    while (true) {
                        menu.showAdminMenu();
                        cmd = sc.nextLine();

                        switch (cmd) {
                            case "1":
                                System.out.print("user: ");
                                String u = sc.nextLine();
                                System.out.print("password: ");
                                String s = sc.nextLine();
                                lib.addEmployee(u, s);
                                System.out.println("added");
                                break;
                            case "2":
                                System.out.print("loanId: ");
                                tempId = sc.nextLine();
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
                        }
                    }


                    break;
                }
                default:
                    break label;
            }
        }
    }
}

