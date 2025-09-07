package ap.projects.finalproject.models;

import java.io.Serializable;

public class Book implements Serializable {
    public String id;
    public String title;
    public String author;
    public int year;
    public boolean available = true;

    public Book(String id, String title, String author, int year){
        this.id = id; this.title = title; this.author = author; this.year = year;
    }

    @Override
    public String toString(){
        return String.format("[%s] %s — %s (%d) — %s", id, title, author, year, available ? "available" : "borrowed");
    }
}

