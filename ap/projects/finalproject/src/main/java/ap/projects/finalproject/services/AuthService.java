package ap.projects.finalproject.services;

import ap.projects.finalproject.database.Database;
import ap.projects.finalproject.database.EmployeeDao;
import ap.projects.finalproject.database.StudentDao;
import ap.projects.finalproject.models.Employee;
import ap.projects.finalproject.models.Student;

/**
 * Authentication service: student registration/login and employee login.
 * Passwords are compared as plain text (deliberate simplification of the
 * coursework; production systems would hash them).
 */
public class AuthService {
    private final StudentDao studentDao;
    private final EmployeeDao employeeDao;

    public AuthService(Database db){
        this.studentDao = new StudentDao(db);
        this.employeeDao = new EmployeeDao(db);
    }

    /**
     * Registers a new student account.
     * @return {@code true} on success, {@code false} if the username is taken
     */
    public boolean registerStudent(String user, String pass){
        if(studentDao.exists(user)) return false;
        studentDao.insert(new Student(user, pass));
        return true;
    }

    /** @return the logged-in student, or {@code null} on wrong credentials */
    public Student loginStudent(String user, String pass){
        Student s = studentDao.find(user);
        return (s != null && s.getPassword().equals(pass)) ? s : null;
    }

    /** @return the logged-in employee, or {@code null} on wrong credentials */
    public Employee loginEmployee(String user, String pass){
        Employee e = employeeDao.find(user);
        return (e != null && e.getPassword().equals(pass)) ? e : null;
    }
}
