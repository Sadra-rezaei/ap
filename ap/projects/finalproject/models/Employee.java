package ap.projects.finalproject.models;

import java.io.Serializable;

public class Employee implements Serializable {
    public String username;
    public String password;

    public Employee(String username, String password){
        this.username = username; this.password = password;
    }

    @Override
    public String toString(){
        return "Employee("+username+")";
    }
}

