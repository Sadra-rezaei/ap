package ap.pojects.Library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Borrow {
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Book book;
    private boolean isReturned;
    private boolean isLate;

    public Borrow(LocalDate borrowDate, Book book) {
        this.borrowDate = borrowDate;
        this.book = book;
        if (!this.book.isBorrowed())
            this.book.changeBorrowed();
        this.isReturned = false;

    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isLate() {
        return isLate;
    }

    public void returnBook() {
        if (!this.isReturned) {
            isReturned = true;
            this.returnDate = LocalDate.now();
            this.book.changeBorrowed();
            if (ChronoUnit.DAYS.between(this.borrowDate, this.returnDate) > 60)
                isLate = true;
        }
        else
            System.out.println("Book is already returned");
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", book=" + book +
                ", isReturned=" + isReturned +
                ", isLate=" + isLate +
                '}';
    }
}
