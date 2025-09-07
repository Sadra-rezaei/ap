package ap.projects.finalTest.model;

import ap.projects.finalTest.Enums.ProductName;

public class Book extends Product {
    private String author;


    public Book(String name, Integer price) {
        super(name, price);
        this.author = author;
        super.productName = ProductName.Book;
    }

    public ProductName getType() {
        return super.productName;
    }
}
