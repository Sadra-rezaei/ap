package ap.projects.finalproject;

import ap.projects.finalproject.models.Book;
import ap.projects.finalproject.models.Loan;
import ap.projects.finalproject.models.Student;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private DataStore ds;
    public LibraryService(DataStore ds){ this.ds = ds; }

//    public void addBook(String id, String title, String author, int year){
//        ds.books.put(id, new Book(id,title,author,year));
//        ds.save();
//    }

    public List<Book> searchBooks(String titleQuery, String authorQuery, Integer year){
        return ds.books.values().stream().filter(b -> {
            boolean ok = true;
            if(titleQuery != null && !titleQuery.trim().isEmpty()){
                ok &= b.title.toLowerCase().contains(titleQuery.toLowerCase());
            }
            if(authorQuery != null && !authorQuery.trim().isEmpty()){
                ok &= b.author.toLowerCase().contains(authorQuery.toLowerCase());
            }
            if(year != null){
                ok &= b.year == year;
            }
            return ok;
        }).collect(Collectors.toList());
    }

//    public List<Book> guestSearchByTitle(String title){
//        return ds.books.values().stream()
//                .filter(b -> b.title.toLowerCase().contains(title.toLowerCase()))
//                .collect(Collectors.toList());
//    }
//
    public String createLoan(String bookId, String studentUsername, LocalDate start, LocalDate end){
        Book b = ds.books.get(bookId);
        Student s = ds.students.get(studentUsername);
        if(b==null || s==null) return null;
        String loanId = "L"+(ds.loans.size()+1);
        Loan loan = new Loan(loanId, bookId, studentUsername, start, end);
        ds.loans.put(loanId, loan);
        s.loanIds.add(loanId);
        ds.save();
        return loanId;
    }
//
//    public boolean approveLoan(String loanId, String employeeUsername){
//        Loan loan = ds.loans.get(loanId);
//        if(loan==null) return false;
//        LocalDate today = LocalDate.now();
//        if(!loan.startDate.equals(today) && !loan.startDate.equals(today.minusDays(1))) return false;
//        loan.approved = true;
//        Book b = ds.books.get(loan.bookId);
//        if(b!=null) b.available = false;
//        ds.save();
//        return true;
//    }
//
//    public boolean receiveBook(String loanId){
//        Loan loan = ds.loans.get(loanId);
//        if(loan==null) return false;
//        loan.receivedDate = LocalDate.now();
//        ds.save();
//        return true;
//    }
//
//    public boolean returnBook(String loanId){
//        Loan loan = ds.loans.get(loanId);
//        if(loan==null) return false;
//        loan.returnedDate = LocalDate.now();
//        Book b = ds.books.get(loan.bookId);
//        if(b!=null) b.available = true;
//        ds.save();
//        return true;
//    }
//
//    public String studentLoanReport(String studentUsername){
//        Student s = ds.students.get(studentUsername);
//        if(s==null) return "Student not found";
//        StringBuilder sb = new StringBuilder();
//        sb.append("History for ").append(studentUsername).append("\n");
//        int total = s.loanIds.size();
//        int notReturned = 0;
//        int delayed = 0;
//        for(String id : s.loanIds){
//            Loan loan = ds.loans.get(id);
//            sb.append(loan).append("\n");
//            if(loan.returnedDate==null) notReturned++;
//            if(loan.returnedDate!=null && loan.returnedDate.isAfter(loan.endDate)) delayed++;
//        }
//        sb.append(String.format("Total=%d notReturned=%d delayed=%d\n", total, notReturned, delayed));
//        return sb.toString();
//    }
//
//    public String guestStats(){
//        long students = ds.students.size();
//        long books = ds.books.size();
//        long loans = ds.loans.size();
//        long currentlyLoaned = ds.books.values().stream().filter(b->!b.available).count();
//        return String.format("students=%d books=%d loans=%d currentlyLoaned=%d", students, books, loans, currentlyLoaned);
//    }
}

