package ap.pojects.Library.model;

public class Admin extends User{

    public Admin(String firstname, String lastname) {
        super.firstname = firstname;
        super.lastname = lastname;
    }

    @Override
    public String getName() {
        return firstname + " " + lastname;
    }

    @Override
    public String getID() {
        return "10";
    }

}
