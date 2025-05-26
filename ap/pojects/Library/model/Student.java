package ap.pojects.Library.model;

import ap.pojects.Library.service.Borrow;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends User implements Serializable {
    private String course;
    private String studentID;
    private LocalDate dateOfJoining;
    private ArrayList<Borrow> borrowedBooks;


    public Student(String firstname, String lastname, String course, String studentID, LocalDate dateOfJoining) {
        super.firstname = firstname;
        super.lastname = lastname;
        this.course = course;
        this.studentID = studentID;
        this.dateOfJoining = dateOfJoining;
        this.borrowedBooks = new ArrayList<>();
    }

    @Override
    public String getID() {
        return studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public ArrayList<Borrow> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String getName() {
        return firstname + " " + lastname;
    }

    @Override
    public String toString() {
        return "Student{" +
                "course='" + course + '\'' +
                ", studentID='" + studentID + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                '}';
    }
}
