package ap.exercises.trainingCodes.m2.d16;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Pen> pens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            books.add(new Book("Name",i));
            pens.add(new Pen("Red",i,"Brand"));
        }

        for (Book book : books) {
            System.out.println(book);
        }
        for (Pen pen : pens) {
            System.out.println(pen);
        }
    }
}
