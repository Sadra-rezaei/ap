package ap.pojects.Library.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String firstname;
    protected String lastname;

    public abstract String getName();

    public abstract String getID();







}
