package ap.pojects.Library.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private String title;
    private String author;
    private int pages;
    private LocalDate publishYear;
    private boolean borrowed;
    private String isbn;

    public Book(String title, String author, int pages, LocalDate publishYear, String isbn) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.publishYear = publishYear;
        borrowed = false;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", publishYear='" + publishYear + '\'' +
                ", borrowed=" + borrowed +  '\'' +
                ", isbn='" + isbn +
                "}\n";
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void changeBorrowed(){
        borrowed = !borrowed;
    }
}
