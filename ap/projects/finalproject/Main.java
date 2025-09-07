package ap.projects.finalproject;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ap.projects.finalproject.models.Admin;
import ap.projects.finalproject.models.Book;
import ap.projects.finalproject.models.Employee;
import ap.projects.finalproject.models.Student;

public class Main {
    public static void main(String[] args){
        DataStore ds = DataStore.load();
        AuthService auth = new AuthService(ds);
        LibraryService lib = new LibraryService(ds);
        Scanner sc = new Scanner(System.in);
        Admin admin = new Admin("Sadra", "1234");

        while(true){
            System.out.println("\n--- Library System ---");
            System.out.println("1) Guest");
            System.out.println("2) Student register/login");
            System.out.println("3) Employee login");
            System.out.println("4) Admin login");
            System.out.println("5) Exit");
            System.out.print("> ");
            String c = sc.nextLine();


            if(c.equals("1")){
                System.out.println(lib.guestStats());
                System.out.print("search title: ");
                String t = sc.nextLine();
                List<Book> res = lib.guestSearchByTitle(t);
                res.forEach(System.out::println);


            } else if(c.equals("2")){
                System.out.print("""
                        1-register 
                        2-login: """);
                String r = sc.nextLine();

                if(r.equals("1")){
                    System.out.print("user: "); String u=sc.nextLine();
                    System.out.print("pass: "); String p=sc.nextLine();
                    boolean ok = auth.registerStudent(u,p);
                    System.out.println(ok ? "registered" : "exists");
                } else {
                    System.out.print("user: "); String u=sc.nextLine();
                    System.out.print("pass: "); String p=sc.nextLine();
                    Student s = auth.loginStudent(u,p);
                    if(s==null) { System.out.println("invalid"); continue; }
                    System.out.println("welcome "+u);
                    System.out.print("search title? "); String tq=sc.nextLine();
                    List<Book> res = lib.searchBooks(tq,"",null);
                    res.forEach(System.out::println);
                    System.out.print("want request loan? bookId or empty: ");
                    String bid = sc.nextLine();
                    if(!bid.trim().isEmpty()){
                        LocalDate st = LocalDate.now();
                        System.out.print("return date yyyy-mm-dd: "); LocalDate en = LocalDate.parse(sc.nextLine());
                        String loanId = lib.createLoan(bid, u, st, en);
                        System.out.println("loan created: "+loanId);
                    }
                }


            } else if(c.equals("3")){
                System.out.print("user: "); String u=sc.nextLine();
                System.out.print("pass: "); String p=sc.nextLine();
                Employee e = auth.loginEmployee(u,p);
                if(e==null){ System.out.println("bad"); continue; }
                System.out.println("emp:"+u);
                System.out.println("""
                        1-add book
                        2-approve loan 
                        3-receive 
                        4-return 
                        5-student report""");
                String cmd = sc.nextLine();

                if(cmd.equals("1")){
                    System.out.print("id: "); String id=sc.nextLine();
                    System.out.print("title: "); String t=sc.nextLine();
                    System.out.print("author: "); String a=sc.nextLine();
                    System.out.print("year: "); int y=Integer.parseInt(sc.nextLine());
                    lib.addBook(id,t,a,y);
                    System.out.println("added");
                } else if(cmd.equals("2")){
                    System.out.print("loanId: "); String lid = sc.nextLine();
                    boolean ok = lib.approveLoan(lid, u);
                    System.out.println("approved? "+ok);
                } else if(cmd.equals("3")){
                    System.out.print("loanId: "); String lid = sc.nextLine();
                    System.out.println(lib.receiveBook(lid));
                } else if(cmd.equals("4")){
                    System.out.print("loanId: "); String lid = sc.nextLine();
                    System.out.println(lib.returnBook(lid));
                } else if(cmd.equals("5")){
                    System.out.print("student username: "); String sname=sc.nextLine();
                    System.out.println(lib.studentLoanReport(sname));
                }


            } else if (c.equals("4")){
                System.out.print("Hi Sadra\npassword: "); String p=sc.nextLine();
                if(!p.equals(admin.getPassword())){
                    System.out.println("wrong password");
                    break;
                }
                System.out.println("""
                        1.add Employee
                        2.report Employee
                        3.loan Stats
                        4.student Stats
                        """);
                String cmd = sc.nextLine();

                if(cmd.equals("1")){
                    System.out.print("user: "); String u=sc.nextLine();
                    System.out.print("password: ");  String s=sc.nextLine();
                    lib.addEmployee(u,s);
                    System.out.println("added");
                } else if (cmd.equals("2")){
                    System.out.print("loanId: "); String id = sc.nextLine();
                    System.out.println(lib.employeeReport(id));
                } else if(cmd.equals("3")){
                    System.out.println(lib.loanStats());
                }


            } else break;
        }
    }
}

