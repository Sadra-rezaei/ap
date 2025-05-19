package ap.pojects.Library;

import java.io.*;
import java.util.ArrayList;

public class Backup {
    private final String STUDENT_FILE = "students.dat";
    private final String BOOK_FILE = "books.dat";
    private final String OPERATOR_FILE = "operator.dat";
    private final String REQUEST_FILE = "requests.dat";

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

    public void saveBooks(ArrayList<Book> books) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BOOK_FILE))) {
            out.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Book> loadBooks() {
        File file = new File(BOOK_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Book>) in.readObject();
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

    public void saveRequests(ArrayList<Request> requests) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(REQUEST_FILE))) {
            out.writeObject(requests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Request> loadRequests() {
        File file = new File(REQUEST_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Request>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
