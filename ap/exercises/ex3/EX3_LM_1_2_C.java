package ap.exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Book3 {
    private String name;
    private String author;
    private String pages;
    private String year;

    public Book3(String name, String author, String pages, String year) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.year = year;
    }

    public String getFullName() {
        return name + "\t" + author + "\t" + pages + "\t" + year;
    }
}

class Student3 {
    private String firstName;
    private String lastName;
    private String studentNumber;
    private String course;


    public Student3(String firstName, String lastName, String studentNumber, String course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.course = course;
    }

    public String getFullName() {
        return firstName + "\t" + lastName + "\t" + studentNumber + "\t" + course;
    }
}

public class EX3_LM_1_2_C {
    public static void writeStudentsFile(String fileName, ArrayList<Student3> studentsList) throws FileNotFoundException {

        PrintWriter studentWriter = new PrintWriter(fileName);
        for (Student3 student : studentsList) {
            studentWriter.println(student.getFullName());
        }
        studentWriter.close();
    }

    public static void writeBooksFile(String fileName, ArrayList<Book3> booksList) throws FileNotFoundException {
        PrintWriter bookWriter = new PrintWriter(fileName);
        for (Book3 book : booksList) {
            bookWriter.println(book.getFullName());
        }
        bookWriter.close();
    }

    public static void readStudentsFile(String fileName, ArrayList<Student3> studentsList) throws FileNotFoundException {
        Scanner studentScanner = new Scanner(new File(fileName));

        while (studentScanner.hasNext()){
            String line = studentScanner.nextLine();
            String[] split = line.split("\t");
            studentsList.add(new Student3(split[0],split[1],split[2],split[3]));
        }
        studentScanner.close();
    }

    public static void readBooksFile(String fileName, ArrayList<Book3> booksList) throws FileNotFoundException {
        Scanner bookScanner = new Scanner(new File(fileName));

        while (bookScanner.hasNext()){
            String line = bookScanner.nextLine();
            String[] split = line.split("\t");
            booksList.add(new Book3(split[0],split[1],split[2],split[3]));
        }
        bookScanner.close();
    }

    public static void main(String[] args) {
        ArrayList<Book3> booksList = new ArrayList<Book3>();
        ArrayList<Student3> studentsList = new ArrayList<Student3>();

        for (int i = 0; i <= 2; i++) {
            studentsList.add(new Student3("Ali", "Rezaei","123456789","Computer Science"));
        }

        for (int i = 0; i <= 3; i++) {
            booksList.add(new Book3("Book","Ali","12","1945"));
        }

        try {

            writeStudentsFile("StudentsTemp.txt",studentsList);
            writeBooksFile("BooksTemp.txt",booksList);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Student3> newStudentsList = new ArrayList<Student3>();
        ArrayList<Book3> newBooksList = new ArrayList<Book3>();

        try {
            readStudentsFile("StudentsTemp.txt", newStudentsList);
            readBooksFile("BooksTemp.txt",newBooksList);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < newStudentsList.size(); i++) {
            System.out.println(newStudentsList.get(i).getFullName());
        }
        System.out.println();

        for (int i = 0; i < newBooksList.size(); i++) {
            System.out.println(newBooksList.get(i).getFullName());
        }


    }
}
