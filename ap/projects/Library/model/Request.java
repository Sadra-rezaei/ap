package ap.projects.Library.model;

import ap.projects.Library.service.Borrow;
import ap.projects.Library.enums.RequestType;

import java.io.Serializable;
import java.time.LocalDate;

public class Request implements Serializable {
    private Student student;
    private Borrow borrow;
    private Operator borrowApprover;
    private Operator returnApprover;
    private Book book;
    private RequestType borrowApproved;
    private RequestType returnApproved;

    public Request(Student student, Book book) {
        this.student = student;
        this.book = book;
        this.borrowApproved = RequestType.UNDER_REVIEW;
        this.returnApproved = RequestType.REQUEST_NOT_MADE;
    }

    public Book getBook() {
        return book;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public RequestType getBorrowApproved() {
        return borrowApproved;
    }

    public RequestType getReturnApproved() {
        return returnApproved;
    }

    public Operator getBorrowApprover() {
        return borrowApprover;
    }

    public Operator getReturnApprover() {
        return returnApprover;
    }

    public void setReturnApproved(RequestType returnApproved) {
        this.returnApproved = returnApproved;
    }

    public void approveBorrow(Operator operator) {
        this.borrowApprover = operator;
        this.borrowApproved = RequestType.APPROVED;
        this.borrow = new Borrow(LocalDate.now(), this.book);
        this.student.getBorrowedBooks().add(borrow);
    }

    public void approveReturn(Operator operator) {
        this.returnApprover = operator;
        this.returnApproved = RequestType.APPROVED;
        this.borrow.returnBook();
        this.student.getBorrowedBooks().remove(borrow);
    }



    @Override
    public String toString() {
        return "Student: " + this.student.toString() + "\n\tBook: " + this.book.toString();
    }
}
