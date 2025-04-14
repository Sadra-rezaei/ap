package ap.exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

    class Book2 {
        private String name;
        private String author;
        private String pages;
        private String year;

        public Book2(String name, String author, String pages, String year) {
            this.name = name;
            this.author = author;
            this.pages = pages;
            this.year = year;
        }

    }

    class Student2 {
        private String firstName;
        private String lastName;
        private String studentNumber;
        private String course;


        public Student2(String firstName, String lastName, String studentNumber, String course) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.studentNumber = studentNumber;
            this.course = course;
        }

        public String getFullName() {
            return firstName + "\t" + lastName + "\t" + studentNumber + "\t" + course;
        }

    }


public class EX3_LM_1_2_B {
    public static void main(String[] args) throws FileNotFoundException {
            Scanner studentScanner = new Scanner(new File("Students.txt"));
            ArrayList<Student2> studentsList = new ArrayList<>();

            while (studentScanner.hasNext()){
                String line = studentScanner.nextLine();
                String[] split = line.split("\t");
                studentsList.add(new Student2(split[0],split[1],split[2],split[3]));
                }

            Scanner bookScanner = new Scanner(new File("Books.txt"));
            ArrayList<Book2> booksList = new ArrayList<>();

            while (bookScanner.hasNext()){
                String line = bookScanner.nextLine();
                String[] split = line.split("\t");
                booksList.add(new Book2(split[0],split[1],split[2],split[3]));
            }



    }
}
