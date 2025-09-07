package ap.projects.finalproject.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Loan implements Serializable {
    public String id;
    public String bookId;
    public String studentUsername;
    public LocalDate startDate;
    public LocalDate endDate;
    public boolean approved = false;
    public LocalDate receivedDate = null;
    public LocalDate returnedDate = null;

    public Loan(String id, String bookId, String studentUsername, LocalDate start, LocalDate end){
        this.id = id; this.bookId = bookId; this.studentUsername = studentUsername;
        this.startDate = start; this.endDate = end;
    }

    @Override
    public String toString(){
        return String.format("Loan[%s] book=%s student=%s %s->%s approved=%s received=%s returned=%s",
                id, bookId, studentUsername, startDate, endDate, approved, receivedDate, returnedDate);
    }
}

