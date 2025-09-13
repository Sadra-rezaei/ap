package ap.projects.finalproject.models;

import java.io.Serializable;

public class Employee extends User implements Serializable {


    public Employee(String username, String password){
        super.username = username; super.password = password;
    }

    @Override
    public String toString(){
        return "Employee("+username+")";
    }
}

