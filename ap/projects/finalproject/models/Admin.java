package ap.projects.finalproject.models;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

}
