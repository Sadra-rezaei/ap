package ap.projects.finalproject.models;

/**
 * A book in the catalog. {@code id} is the primary key chosen by the
 * employee who adds the book; {@code available} flips to {@code false}
 * while an approved loan is out and back to {@code true} on return.
 * {@code addedBy} records which employee added the book (for reports).
 */
public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final int year;
    private boolean available = true;
    private String addedBy;

    public Book(String id, String title, String author, int year){
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public String toString(){
        return String.format("[%s] %s — %s (%d) — %s", id, title, author, year, available ? "available" : "borrowed");
    }
}
