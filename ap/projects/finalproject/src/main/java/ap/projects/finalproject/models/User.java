package ap.projects.finalproject.models;

/**
 * Base class of every library account (Student, Employee, Admin).
 * Holds the shared identity fields; subclasses add role-specific behavior.
 */
public abstract class User {
    protected String username;
    protected String password;

    protected User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
