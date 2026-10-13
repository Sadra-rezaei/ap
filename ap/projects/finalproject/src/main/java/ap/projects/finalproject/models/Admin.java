package ap.projects.finalproject.models;

/**
 * The system administrator. Per project spec the admin is hard-coded in
 * {@code Main} (not stored in the database) and only signs in to manage
 * employees and view statistics.
 */
public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

}
