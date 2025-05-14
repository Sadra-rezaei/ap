package ap.pojects.Library;

import java.io.Serializable;

public class Operator extends User implements Serializable{
    private String operatorID;

    public Operator(String firstName, String lastName, String operatorID) {
        super.firstname = firstName;
        super.lastname = lastName;
        this.operatorID = operatorID;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public void setNewFirstName(String newFirstName) {
        super.firstname = newFirstName;
    }

    public void setNewLastName(String newLastName) {
        super.lastname = newLastName;
    }

}
