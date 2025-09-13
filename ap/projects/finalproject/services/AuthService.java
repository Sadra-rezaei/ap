package ap.projects.finalproject.services;

import ap.projects.finalproject.dataBase.DataStore;
import ap.projects.finalproject.models.Employee;
import ap.projects.finalproject.models.Student;

public class AuthService {
    private DataStore ds;
    public AuthService(DataStore ds){ this.ds = ds; }

    public boolean registerStudent(String user, String pass){
        if(ds.students.containsKey(user)) return false;
        ds.students.put(user, new Student(user, pass));
        ds.save();
        return true;
    }

    public Student loginStudent(String user, String pass){
        Student s = ds.students.get(user);
        if(s!=null && s.getPassword().equals(pass)) return s;
        return null;
    }

    public Employee loginEmployee(String user, String pass){
        Employee e = ds.employees.get(user);
        if(e!=null && e.getPassword().equals(pass)) return e;
        return null;
    }
}

