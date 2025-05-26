package ap.pojects.Library.service;

import ap.pojects.Library.auth.Login;
import ap.pojects.Library.util.Menu;
import ap.pojects.Library.enums.RequestType;
import ap.pojects.Library.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<Student> students;
    private ArrayList<Operator> operators;
    private Menu menu;
    private Login login;
    private Admin admin;
    private ArrayList<Request> requests;


    public Library(Login login) {
        students = new ArrayList<>();
        books = new ArrayList<>();
        operators = new ArrayList<>();
        menu = new Menu();
        this.login = login;
        admin = new Admin("Sadra", "Rezaei");
        requests = new ArrayList<>();
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

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setOperators(ArrayList<Operator> operators) {
        this.operators = operators;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public Menu getMenu() {
        return menu;
    }

    public void studentService(Student student) {
        Scanner scanner = new Scanner(System.in);
        int choice;

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

                case 3: //borrow book
                    System.out.print("Enter the isbn of the book: ");
                    String isbn = scanner.next();
                    for (Book book : books) {
                        if (book.getIsbn().equals(isbn) && !book.isBorrowed()) {
                            Request request = new Request(student, book);
                            requests.add(request);
                            System.out.println("Your request has been sent\nWait for an operator to approve the request\n");
                            break;

                        } else if (book.getIsbn().equals(isbn) && book.isBorrowed()) {
                            System.out.println("Already borrowed\n");

                        }
                    }
                    break;

                case 4: //return book
                    int borrowIndex;
                    System.out.println("=== Borrowed Books ===");
                    for (int i = 0; i < student.getBorrowedBooks().size(); i++) {
                        System.out.println((i + 1) + ". " + student.getBorrowedBooks().get(i) + "\n");
                    }

                    System.out.print("Which book would you like to return? ");
                    borrowIndex = scanner.nextInt();
                    borrowIndex --;
                    for (Request request : requests) {
                        if (request.getBook().equals(student.getBorrowedBooks().get(borrowIndex).getBook()) && request.getBorrowApproved().equals(RequestType.APPROVED)) {
                            request.setReturnApproved(RequestType.UNDER_REVIEW);
                            System.out.println("Your request has been sent to the operator\n");
                            break;
                        }
                    }
                    break;

                case 5: //show borrowed books
                    for (Borrow borrowedBook : student.getBorrowedBooks()) {
                        System.out.println(borrowedBook);
                    }
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

                case 3: //add book
                    books.add(login.createBook());
                    break;

                case 4: //approve borrow
                    int borrowApproveIndex;

                    for (int i = 0; i < requests.size(); i++) {
                        System.out.println((i + 1) +". "+ requests.get(i) + "\n\tBorrow: " + requests.get(i).getBorrowApproved() + "\n");
                    }
                    System.out.print("Which request would you want to approve? ");
                    borrowApproveIndex = scanner.nextInt();
                    borrowApproveIndex --;

                    requests.get(borrowApproveIndex).approveBorrow(operator);
                    System.out.println("=== Approved! ===\n");
                    break;

                case 5: //approve return
                    int returnApproveIndex;

                    for (int i = 0; i < requests.size(); i++) {
                        System.out.println((i + 1) +". "+ requests.get(i) + "\n\tReturn: " + requests.get(i).getReturnApproved() + "\n\tBorrow Date: " + requests.get(i).getBorrow().getBorrowDate() + "\n");
                    }
                    System.out.print("Which request would you want to approve? ");
                    returnApproveIndex = scanner.nextInt();
                    returnApproveIndex --;

                    requests.get(returnApproveIndex).approveReturn(operator);
                    System.out.println("=== Approved! ===\n");
                    break;

            }
        } while (choice != 99);
    }

    public void adminService() {
        System.out.println("Welcome "+admin.getName());
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            menu.showAdminMenu();
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: // add a new operator
                    login.creatOperator(operators);
                    break;

                case 2: //list of borrowed books
                    for (Request request : requests) {
//                        if ((request.getReturnApproved().equals(RequestType.UNDER_REVIEW)) && (request.getReturnApproved().equals(RequestType.REQUEST_NOT_MADE))) {
                        if ((request.getBorrowApproved().equals(RequestType.APPROVED))) {
                            System.out.println(
                                    "=== Borrowed books ===\n\n" +
                                            request +
                                            "\n\tApproved by: " + request.getBorrowApprover() +
                                            "\n\tBorrow Date: " + request.getBorrow().getBorrowDate() +
                                            "\n\tReturn: " + request.getReturnApproved() +
                                            "\n");
                        }
                    }
                    break;

                case 3: //list of late returned books
                    for (Request request : requests) {
                        if (request.getReturnApproved().equals(RequestType.APPROVED) && request.getBorrow().isLate()) {
                            System.out.println(
                                    "=== Late returned books ===\n\n" +
                                            request +
                                            "\n\tApproved by: " + request.getBorrowApprover() +
                                            "\n\tBorrow Date: " + request.getBorrow().getBorrowDate() +
                                            "\n\tReturn Date: " + request.getBorrow().getReturnDate() +
                                            "\n\tDays between: " + ChronoUnit.DAYS.between(request.getBorrow().getBorrowDate() , request.getBorrow().getReturnDate()) +
                                            "\n");
                        }
                    }
                    break;

                case 4: //taken books from operators
                    String tempOperatorID;
                    System.out.print("\nEnter Operator ID: ");
                    tempOperatorID = scanner.next();

                    for (Request request : requests) {
                        if (!request.getReturnApproved().equals(RequestType.REQUEST_NOT_MADE)) {
                            if (request.getReturnApprover().getID().equals(tempOperatorID)) {
                                System.out.println(
                                        "=== Returned books by " + request.getReturnApprover().getName() + "===" +
                                                request +
                                                "\n\tBorrow Date: " + request.getBorrow().getBorrowDate() +
                                                "\n\tReturn Date: " + request.getBorrow().getReturnDate() +
                                                "\n"
                                );
                            }
                        }
                    }
                    break;

                case 5: //most borrowed books in this year
                    HashMap<Book ,Integer> borrowCounts = new HashMap<>();
                    int length = 10;

                    for (Request request : requests) {
                        if (request.getBorrow().getBorrowDate().isAfter(LocalDate.now().minusYears(1))) {
                                Book book = request.getBook();
                                borrowCounts.put(book, borrowCounts.getOrDefault(book, 0) + 1);
                        }
                    }

                    List<Map.Entry<Book, Integer>> sortedBooks = new ArrayList<>(borrowCounts.entrySet());
                    sortedBooks.sort((a, b) -> b.getValue() - a.getValue());

                    if (sortedBooks.size() < 10)
                        length = sortedBooks.size();

                    for (int i = 0; i < length; i++) {
                        System.out.println(sortedBooks.get(i).getKey() + "\nNumber of borrow: " + sortedBooks.get(i).getValue());
                    }


            }

        } while (choice != 99);
    }

    public int loginStudentService() {
        Scanner scanner = new Scanner(System.in);
        Student student = null;

        while (true) {
            int choice;
            int index;

            menu.showLoginPage();
            System.out.print("\nEnter your choice (99 to exit): ");


            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    index = login.signUp(students);
                    if (index != -1)
                        return index;
                    break;

                case 2:
                    index = login.signIn(students);
                    if (index != -1)
                        return index;
                    else System.out.println("Login failed. Try again.");
                    break;

                case 99:
                    return -1;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
