package ap.pojects.Library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    final String NUMBER_VALID = "^[0-9]+";
    final String STRING_VALID = "[a-zA-Z ]+";
//    final String DATE_VALID = "^[0-9-]+";


    public String userStringInput(Scanner scanner, String prompt, String regex) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.matches(regex))
                return input;

            else
                System.out.println("Invalid input");
        }
    }

    public Student signUp(ArrayList<Student> students) {
        Scanner scanner = new Scanner(System.in);
        Student selectedStudent;

        String firstname = userStringInput(scanner, "Enter your First Name: ", STRING_VALID);
        String lastname = userStringInput(scanner, "Enter your Last Name: ", STRING_VALID);
        String course = userStringInput(scanner, "Enter your Course: ", STRING_VALID);
        String studentId = userStringInput(scanner, "Enter your Student ID: ", NUMBER_VALID);

        LocalDate date = LocalDate.now();

        for (Student student : students) {
            if (student.getStudentID().equals(studentId)) {
                System.out.println("Student already exists!");
                scanner.close();
                return student;
            }
        }

        selectedStudent = new Student(firstname, lastname, course, studentId, date);
        students.add(selectedStudent);
        System.out.println("==== Welcome! ====\n");
        scanner.close();
        return selectedStudent;
    }


    //=====================================================================================
    public Student studentSignIn(ArrayList<Student> students) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String studentID = userStringInput(scanner, "Enter your Student ID: ", NUMBER_VALID);
            for (Student student : students) {
                if (student.getStudentID().equals(studentID)) {
                    System.out.println("==== Welcome! ====\n");
                    scanner.close();
                    return student;
                }
            }

            System.out.println("User not found");
        }
    }

    public Operator operatorSignIn(ArrayList<Operator> operators) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== 1 or 2 ===");
            String operatorID = userStringInput(scanner, "Enter your Operator ID: ", NUMBER_VALID);
            for (Operator operator : operators) {
                if (operator.getOperatorID().equals(operatorID)) {
                    System.out.println("==== Welcome! ====\n");
                    scanner.close();
                    return operator;
                }
            }
            System.out.println("Operator not found\n");
        }
    }

    public void creatOperator(ArrayList<Operator> operators) {
        Scanner scanner = new Scanner(System.in);

        String firstName = userStringInput(scanner, "Enter your First Name: ", STRING_VALID);
        String lastName = userStringInput(scanner, "Enter your Last Name: ", STRING_VALID);
        String operatorID = userStringInput(scanner, "Enter your Operator ID: ", NUMBER_VALID);

        for (Operator operator : operators) {
            if (operator.getOperatorID().equals(operatorID)) {
                System.out.println("Operator already exists!\n");
                break;
            }
        }

        operators.add(new Operator(firstName, lastName, operatorID));
        System.out.println("=== Operator created! ====\n");
    }

    public Book createBook() {
        Scanner scanner = new Scanner(System.in);

        String title = userStringInput(scanner, "Enter your Title: ", STRING_VALID);
        String author = userStringInput(scanner, "Enter your Author: ", STRING_VALID);
        int pages = Integer.parseInt(userStringInput(scanner, "Enter your Pages: ", NUMBER_VALID));
        LocalDate date = LocalDate.now();
        String isbn = userStringInput(scanner, "Enter your ISBN: ", NUMBER_VALID);

        Book book = new Book(title, author, pages, date, isbn);

        return book;
    }
}
