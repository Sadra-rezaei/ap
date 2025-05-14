package ap.pojects.Library;

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
    }


    public String getStudentID() {
        return studentID;
    }

    @Override
    public String getName() {
        return super.getName();
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
