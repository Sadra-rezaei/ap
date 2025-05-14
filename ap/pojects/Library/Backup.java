package ap.pojects.Library;

import java.io.*;
import java.util.ArrayList;

public class Backup {
    private static final String STUDENT_FILE = "students.dat";
    private static final String BOOK_FILE = "books.dat";
    private static final String OPERATOR_FILE = "students.dat";

    public void saveStudents(ArrayList<Student> students) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
            out.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Student> loadStudents() {
        File file = new File(STUDENT_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveOperators(ArrayList<Operator> operators) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OPERATOR_FILE))) {
            out.writeObject(operators);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Operator> loadOperators() {
        File file = new File(OPERATOR_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Operator>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
