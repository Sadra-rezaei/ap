package ap.projects.finalproject;

import ap.projects.finalproject.models.Book;
import ap.projects.finalproject.models.Employee;
import ap.projects.finalproject.models.Loan;
import ap.projects.finalproject.models.Student;

import java.io.*;
import java.util.*;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<String, Book> books = new HashMap<>();
    public Map<String, Student> students = new HashMap<>();
    public Map<String, Employee> employees = new HashMap<>();
    public Map<String, Loan> loans = new HashMap<>();

    private static final String DATA_FILE = "library_data.bin";

    public static DataStore load(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))){
            return (DataStore) ois.readObject();
        } catch (Exception e){
            DataStore ds = new DataStore();
            ds.employees.put("emp1", new Employee("emp1","1234"));
            ds.employees.put("emp2", new Employee("emp2","1234"));
            ds.employees.put("emp3", new Employee("emp3","1234"));
            return ds;
        }
    }

    public void save(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))){
            oos.writeObject(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

