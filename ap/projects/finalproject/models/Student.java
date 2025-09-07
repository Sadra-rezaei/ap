package ap.projects.finalproject.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    public String username;
    public String password;
    public boolean active = true;
    public List<String> loanIds = new ArrayList<>();

    public Student(String username, String password){
        this.username = username; this.password = password;
    }

    @Override
    public String toString(){
        return String.format("Student(%s) active=%s loans=%d", username, active, loanIds.size());
    }
}
