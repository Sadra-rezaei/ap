package ap.projects.finalproject.models;

/**
 * A student member of the library. Students register through the console,
 * browse/search the catalog, request loans and return them.
 * Loan history is not stored here — it is derived from the
 * {@code loans} table by the student's username.
 */
public class Student extends User {

    public Student(String username, String password){
        super(username, password);
    }

    @Override
    public String toString(){
        return "Student(" + username + ")";
    }
}
