package ap.projects.finalproject.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String username;
    protected String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}