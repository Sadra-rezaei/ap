package ap.projects.finalproject.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    public boolean active = true;
    public List<String> loanIds = new ArrayList<>();

    public Student(String username, String password){
        super.username = username; super.password = password;
    }

    @Override
    public String toString(){
        return String.format("Student(%s) active=%s loans=%d", username, active, loanIds.size());
    }
}
