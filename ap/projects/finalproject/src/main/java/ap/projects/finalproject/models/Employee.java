package ap.projects.finalproject.models;

/**
 * A library staff member. Employees log in to add books and to
 * approve/receive loans; their username is recorded as the approver
 * of a loan and as the adder of a book for the reports.
 */
public class Employee extends User {

    public Employee(String username, String password){
        super(username, password);
    }

    @Override
    public String toString(){
        return "Employee(" + username + ")";
    }
}
