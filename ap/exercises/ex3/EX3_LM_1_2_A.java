package ap.exercises.ex3;

import java.io.*;
import java.util.ArrayList;



//class WriteToFile {
//    public static void writeToFile(String fileName, String content) {
//        try {
//            PrintWriter myWriter = new PrintWriter(fileName);
//            myWriter.println(content + "\n");
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//}

    class Book1 {
        private String name;
        private String author;
        private int pages;
        private int year;

        public Book1(String name, String author, int pages, int year) {
            this.name = name;
            this.author = author;
            this.pages = pages;
            this.year = year;
        }

        public String getFullName() {
            return name + " author: " + author + " with " + pages + " pages. publish year: " + year;
        }
    }

    class Student1 {
        private String firstName;
        private String lastName;
//        private int studentNumber;
//        private String course;
        private String fullName;


        public Student1(String firstName, String lastName, int studentNumber, String course) {
            this.firstName = firstName;
            this.lastName = lastName;
//            this.studentNumber = studentNumber;
//            this.course = course;
            this.fullName = firstName + " " + lastName + " with student number: " + studentNumber;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFullName() {
            return fullName;
        }
    }
public class EX3_LM_1_2_A {
    public static void main(String[] args) throws IOException {

        ArrayList<Student1> studentsList = new ArrayList<Student1>();

        PrintWriter studentWriter = new PrintWriter("Students.txt");
        for (int i = 0; i <= 2; i++) {
            studentsList.add(new Student1("Ali", "Rezaei",i,"Computer Science"));
            studentWriter.println(studentsList.get(i).getFullName());
        }
        studentWriter.close();



        ArrayList<Book1> booksList = new ArrayList<>(3);

        PrintWriter bookWriter = new PrintWriter("Books.txt");
        for (int i = 0; i <= 3; i++) {
            booksList.add(new Book1("Book","Ali",i,i));
            bookWriter.println(booksList.get(i).getFullName());
        }
        bookWriter.close();



    }
}
