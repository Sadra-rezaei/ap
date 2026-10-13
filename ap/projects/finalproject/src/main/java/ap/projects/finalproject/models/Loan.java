package ap.projects.finalproject.models;

import java.time.LocalDate;

/**
 * A loan record — one student's request to borrow one book.
 *
 * <p>Lifecycle: created as an unapproved request ({@code approved = false}),
 * then approved by an employee (which marks the book as borrowed and records
 * {@code approvedBy}), handed over ({@code receivedDate}) and finally brought
 * back ({@code returnedDate}, which frees the book again). A loan is
 * "delayed" when {@code returnedDate} falls after {@code endDate}.</p>
 */
public class Loan {
    private final String id;                // generated as "L<n>", unique across loans
    private final String bookId;            // FK to books.id
    private final String studentUsername;   // FK to students.username
    private final LocalDate startDate;      // day the request was made
    private final LocalDate endDate;        // agreed return deadline
    private boolean approved = false;
    private String approvedBy;              // employee who approved the loan
    private LocalDate receivedDate;         // day the book was handed to the student
    private LocalDate returnedDate;         // day the book came back

    public Loan(String id, String bookId, String studentUsername, LocalDate start, LocalDate end){
        this.id = id;
        this.bookId = bookId;
        this.studentUsername = studentUsername;
        this.startDate = start;
        this.endDate = end;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public String toString(){
        return String.format("Loan[%s] book=%s student=%s %s->%s approved=%s by=%s received=%s returned=%s",
                id, bookId, studentUsername, startDate, endDate, approved, approvedBy, receivedDate, returnedDate);
    }
}
