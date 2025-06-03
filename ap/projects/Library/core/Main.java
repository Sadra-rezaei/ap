package ap.projects.Library.core;

import ap.projects.Library.auth.Login;
import ap.projects.Library.model.Operator;
import ap.projects.Library.service.Backup;
import ap.projects.Library.service.Library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Backup backup = new Backup();
        Operator selectedOperator;
        Login login = new Login();
        Library library = new Library(login);
        Scanner scanner = new Scanner(System.in);
        int userChoice = 99;
        int indexOfOperator;
        int indexOfStudent;

        library.setStudents(backup.loadStudents());
        library.setOperators(backup.loadOperators());
        library.setBooks(backup.loadBooks());
        library.setRequests(backup.loadRequests());

        do {
            System.out.println("Welcome to the Library App");
            library.getMenu().showUserSelection();
            System.out.print("Please select who you would like to login as: ");

            String input = scanner.nextLine(); //this 6 line takes a valid input
            try {
                userChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }


            switch (userChoice) {
                case 1:
                    indexOfStudent = library.loginStudentService();
                    if (indexOfStudent != -1)
                        library.studentService(library.getStudents().get(indexOfStudent));
                    break;

                case 2:
                    indexOfOperator = login.signIn(library.getOperators());
                    if (indexOfOperator != -1) {
                        selectedOperator = library.getOperators().get(indexOfOperator);
                        library.operatorService(selectedOperator);
                    }
                    else {
                        System.out.println("You are not logged in");
                    }
                    break;

                case 3:
                    library.adminService();
                    break;

                case 99:
                    System.out.println("Goodbye!");
            }
        } while (userChoice != 99);

        backup.saveStudents(library.getStudents());
        backup.saveOperators(library.getOperators());
        backup.saveBooks(library.getBooks());
        backup.saveRequests(library.getRequests());

    }
}