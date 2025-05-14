package ap.pojects.Library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Backup backup = new Backup();
        Student selectedStudent;
        Operator selectedOperator;
        Login login = new Login();
        Library library = new Library(login);
        library.setStudents(backup.loadStudents());
        library.setOperators(backup.loadOperators());
        Scanner scanner = new Scanner(System.in);
        int userChoice;

        do {
            System.out.println("Welcome to the Library App");
            library.getMenu().showUserSelection();
            System.out.print("Please select who you would like to login as: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    selectedStudent = library.loginStudentService();
                    if (selectedStudent != null)
                        library.studentService(selectedStudent);
                    userChoice = 99;
                    break;

                case 2:
                    selectedOperator = login.operatorSignIn(library.getOperators());
                    library.operatorService(selectedOperator);
                    userChoice = 99;
                    break;

                case 3:
                    library.adminService();
                    userChoice = 99;
                    break;

                case 99:
                    System.out.println("Goodbye!");
            }
        } while (userChoice != 99);
        backup.saveStudents(library.getStudents());
        backup.saveOperators(library.getOperators());

    }
}
