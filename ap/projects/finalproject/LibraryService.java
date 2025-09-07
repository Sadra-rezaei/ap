package ap.projects.finalproject;

import ap.projects.finalproject.models.Book;
import ap.projects.finalproject.models.Employee;
import ap.projects.finalproject.models.Loan;
import ap.projects.finalproject.models.Student;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private DataStore ds;
    public LibraryService(DataStore ds){ this.ds = ds; }

    public void addBook(String id, String title, String author, int year){
        ds.books.put(id, new Book(id,title,author,year));
        ds.save();
    }

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

    public List<Book> guestSearchByTitle(String title){
        return ds.books.values().stream()
                .filter(b -> b.title.toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

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

    public boolean approveLoan(String loanId, String employeeUsername){
        Loan loan = ds.loans.get(loanId);
        if(loan==null) return false;
        LocalDate today = LocalDate.now();
        if(!loan.startDate.equals(today) && !loan.startDate.equals(today.minusDays(1))) return false;
        loan.approved = true;
        Book b = ds.books.get(loan.bookId);
        if(b!=null) b.available = false;
        ds.save();
        return true;
    }

    public boolean receiveBook(String loanId){
        Loan loan = ds.loans.get(loanId);
        if(loan==null) return false;
        loan.receivedDate = LocalDate.now();
        ds.save();
        return true;
    }

    public boolean returnBook(String loanId){
        Loan loan = ds.loans.get(loanId);
        if(loan==null) return false;
        loan.returnedDate = LocalDate.now();
        Book b = ds.books.get(loan.bookId);
        if(b!=null) b.available = true;
        ds.save();
        return true;
    }

    public String studentLoanReport(String studentUsername){
        Student s = ds.students.get(studentUsername);
        if(s==null) return "Student not found";
        StringBuilder sb = new StringBuilder();
        sb.append("History for ").append(studentUsername).append("\n");
        int total = s.loanIds.size();
        int notReturned = 0;
        int delayed = 0;
        for(String id : s.loanIds){
            Loan loan = ds.loans.get(id);
            sb.append(loan).append("\n");
            if(loan.returnedDate==null) notReturned++;
            if(loan.returnedDate!=null && loan.returnedDate.isAfter(loan.endDate)) delayed++;
        }
        sb.append(String.format("Total=%d notReturned=%d delayed=%d\n", total, notReturned, delayed));
        return sb.toString();
    }

    public String guestStats(){
        long students = ds.students.size();
        long books = ds.books.size();
        long loans = ds.loans.size();
        long currentlyLoaned = ds.books.values().stream().filter(b->!b.available).count();
        return String.format("students=%d books=%d loans=%d currentlyLoaned=%d", students, books, loans, currentlyLoaned);
    }

    public boolean addEmployee(String username, String password){
        if(ds.employees.containsKey(username)) return false;
        ds.employees.put(username, new Employee(username, password));
        ds.save();
        return true;
    }

//    public String employeeReport(String empUsername){
//        long addedBooks = ds.books.values().stream()
//                .filter(b -> b.id.startsWith(empUsername+"_")).count();
//        long loansApproved = ds.loans.values().stream()
//                .filter(l -> l.approved).count();
//        long loansReturned = ds.loans.values().stream()
//                .filter(l -> l.returnedDate != null).count();
//        return String.format("Employee %s: added=%d approved=%d returned=%d",
//                empUsername, addedBooks, loansApproved, loansReturned);
//    }
//
//    public String loanStats(){
//        int totalRequests = ds.loans.size();
//        long approved = ds.loans.values().stream().filter(l->l.approved).count();
//        double avgDays = ds.loans.values().stream()
//                .filter(l->l.returnedDate!=null)
//                .mapToLong(l->java.time.temporal.ChronoUnit.DAYS.between(l.receivedDate, l.returnedDate))
//                .average().orElse(0.0);
//        return String.format("Total requests=%d, Approved=%d, AvgDays=%.2f", totalRequests, approved, avgDays);
//    }
//
//    public String studentStats(){
//        StringBuilder sb = new StringBuilder();
//        for(Student s : ds.students.values()){
//            int total = s.loanIds.size();
//            int notReturned=0, delayed=0;
//            for(String lid : s.loanIds){
//                Loan l = ds.loans.get(lid);
//                if(l.returnedDate==null) notReturned++;
//                else if(l.returnedDate.isAfter(l.endDate)) delayed++;
//            }
//            sb.append(String.format("%s: total=%d notReturned=%d delayed=%d\n", s.username, total, notReturned, delayed));
//        }
//
//        List<Student> sorted = new java.util.ArrayList<>(ds.students.values());
//        sorted.sort((a,b)->{
//            int da = (int) a.loanIds.stream().map(lid->ds.loans.get(lid))
//                    .filter(l->l!=null && l.returnedDate!=null && l.returnedDate.isAfter(l.endDate)).count();
//            int db = (int) b.loanIds.stream().map(lid->ds.loans.get(lid))
//                    .filter(l->l!=null && l.returnedDate!=null && l.returnedDate.isAfter(l.endDate)).count();
//            return Integer.compare(db, da);
//        });
//        sb.append("Top 10 delayed:\n");
//        sorted.stream().limit(10).forEach(s->sb.append(s.username).append("\n"));
//        return sb.toString();
//    }

}

