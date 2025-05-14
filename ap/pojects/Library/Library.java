package ap.pojects.Library;

import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<Student> students;
    private ArrayList<Operator> operators;
    private Menu menu;
    private Login login;
    private Admin admin;

    public Library(Login login) {
        students = new ArrayList<>();
        books = new ArrayList<>();
        operators = new ArrayList<>();
        operators.add(new Operator("Ali", "Amin", "1"));
        operators.add(new Operator("Saleh", "Rezaei", "2"));
        menu = new Menu();
        this.login = login;
        admin = new Admin("Sadra", "Rezaei");
    }

    public void searchBooks() {
        Scanner scanner = new Scanner(System.in);
        boolean find = false;

        System.out.print("Search Book: ");
        String search = scanner.next();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(search.toLowerCase())) {
                System.out.println(book);
                find = true;

            } else if (book.getAuthor().toLowerCase().contains(search.toLowerCase())) {
                System.out.println(book);
                find = true;
            }
        }
        if (!find)
            System.out.println("Book not found\n");
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public Login getLogin() {
        return login;
    }

    public Admin getAdmin() {
        return admin;
    }

    public ArrayList<Operator> getOperators() {
        return operators;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setOperators(ArrayList<Operator> operators) {
        this.operators = operators;
    }

    public Menu getMenu() {
        return menu;
    }

    public void studentService(Student student) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean find = false;

        do {
            menu.showStudentMenu();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: //show books
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;

                case 2: //search book
                    searchBooks();
                    break;
            }


        } while (choice != 99);
    }

    public void operatorService(Operator operator) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            menu.showOperatorMenu();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: //show books
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;


                case 2:  //edit information ---> edit books
                    int indexOfBook = -1;
                    int operatorChoice;
                    boolean find = false;

                    System.out.println("\n=== Enter -ISBN- of the book for edit information: ");
                    String isbn = scanner.next();

                    for (Book book : books) {
                        if (book.getIsbn().equals(isbn)) {
                            indexOfBook = books.indexOf(book);
                            find = true;
                        }
                    }
                    if (!find) {
                        System.out.println("Book not found\n");
                        break;
                    }


                    do {
                        System.out.println(
                                """
                                        1. Edit title\
                                        
                                        2. Edit author\
                                        
                                        99. === Back\
                                        
                                        \nEnter your choice: \
                                        """
                        );

                        operatorChoice = scanner.nextInt();
                        switch (operatorChoice) {
                            case 1:
                                System.out.println("Enter new title: ");
                                String newTitle = scanner.next();
                                if (indexOfBook != -1)
                                    books.get(indexOfBook).setTitle(newTitle);
                                System.out.println("Edited title: " + books.get(indexOfBook).getTitle());
                                break;

                            case 2:
                                System.out.println("Enter new author: ");
                                String newAuthor = scanner.next();
                                if (indexOfBook != -1)
                                    books.get(indexOfBook).setAuthor(newAuthor);
                                System.out.println("Edited author: " + books.get(indexOfBook).getAuthor());
                                break;
                        }
                    } while (operatorChoice != 99);
                    break;

                case 3:
                    books.add(login.createBook());
                    break;

            }
        } while (choice != 99);
    }

    public void adminService() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            menu.showAdminMenu();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login.creatOperator(operators);
            }

        } while (choice != 99);
    }

//    public Student loginStudentService() {
//        Scanner scanner = new Scanner(System.in);
//        Student student = null;
//
//        while (true) {
//            menu.showLoginPage();
//            System.out.print("\nEnter your choice (99 to exit): ");
//
//            int choice;
//            try {
//                choice = Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.println("Please enter a valid number.");
//                continue;
//            }
//
//            switch (choice) {
//                case 1:
//                    student = login.signUp(students);
//                    if (student != null) return student;
//                    break;
//
//                case 2:
//                    student = login.studentSignIn(students);
//                    if (student != null) return student;
//                    else System.out.println("Login failed. Try again.");
//                    break;
//
//                case 99:
//                    return null;
//
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        }
//    }

    public Student loginStudentService() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Student student = null;


        do {
            menu.showLoginPage();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    student = login.signUp(students);
                    break;

                case 2:
                    student = login.studentSignIn(students);
            }
        } while (choice != 99);
        return student;
    }





}
