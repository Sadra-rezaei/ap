package ap.projects.finalproject.services;

import ap.projects.finalproject.database.BookDao;
import ap.projects.finalproject.database.Database;
import ap.projects.finalproject.database.EmployeeDao;
import ap.projects.finalproject.database.LoanDao;
import ap.projects.finalproject.database.StudentDao;
import ap.projects.finalproject.models.Book;
import ap.projects.finalproject.models.Employee;
import ap.projects.finalproject.models.Loan;
import ap.projects.finalproject.models.Student;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All library business logic on top of the DAO layer: catalog management,
 * the loan lifecycle (request → approve → receive → return) and the
 * various statistics/reports shown in the menus.
 *
 * <p>Integrity rules enforced here:</p>
 * <ul>
 *   <li>a book can be requested only while it is {@code available};</li>
 *   <li>at approval time the book is re-checked, so only one active loan
 *       per book can exist (multiple pending requests may queue up);</li>
 *   <li>a loan can be approved only on its start date or the day after;</li>
 *   <li>a student can return only their own loan, and only once;</li>
 *   <li>receiving requires an approved, not-yet-received loan.</li>
 * </ul>
 */
public class LibraryService {
    private final BookDao bookDao;
    private final StudentDao studentDao;
    private final EmployeeDao employeeDao;
    private final LoanDao loanDao;

    public LibraryService(Database db){
        this.bookDao = new BookDao(db);
        this.studentDao = new StudentDao(db);
        this.employeeDao = new EmployeeDao(db);
        this.loanDao = new LoanDao(db);
    }

    /**
     * Adds a book to the catalog, credited to the given employee.
     * @return {@code false} if a book with the same id already exists
     */
    public boolean addBook(String id, String title, String author, int year, String addedBy){
        if(bookDao.exists(id)) return false;
        Book book = new Book(id, title, author, year);
        book.setAddedBy(addedBy);
        bookDao.insert(book);
        return true;
    }

    /** @return every book in the catalog (used by the "show books" options) */
    public List<Book> allBooks(){
        return bookDao.findAll();
    }

    /**
     * Filters the catalog by the given criteria; {@code null} or blank
     * criteria are ignored. Title/author matching is case-insensitive
     * "contains".
     */
    public List<Book> searchBooks(String titleQuery, String authorQuery, Integer year){
        return bookDao.findAll().stream().filter(b -> {
            boolean ok = true;
            if(titleQuery != null && !titleQuery.trim().isEmpty()){
                ok &= b.getTitle().toLowerCase().contains(titleQuery.toLowerCase());
            }
            if(authorQuery != null && !authorQuery.trim().isEmpty()){
                ok &= b.getAuthor().toLowerCase().contains(authorQuery.toLowerCase());
            }
            if(year != null){
                ok &= b.getYear() == year;
            }
            return ok;
        }).collect(Collectors.toList());
    }

    /** Guest search: filter by title only. */
    public List<Book> guestSearchByTitle(String title){
        return searchBooks(title, null, null);
    }

    /**
     * Creates an unapproved loan request that starts now.
     * @return the generated loan id, or {@code null} when the book/student
     *         does not exist, the book is not available, or the end date
     *         is not after the start date
     */
    public String createLoan(String bookId, String studentUsername, LocalDate start, LocalDate end){
        Book b = bookDao.find(bookId);
        Student s = studentDao.find(studentUsername);
        if(b == null || s == null) return null;
        if(!b.isAvailable()) return null;
        if(!end.isAfter(start)) return null;
        Loan loan = new Loan(loanDao.nextId(), bookId, studentUsername, start, end);
        loanDao.insert(loan);
        return loan.getId();
    }

    /**
     * Approves a pending loan on behalf of an employee and marks the book
     * as borrowed. Approval is only valid on the loan's start date (or the
     * day after) and only while the book is still available, so two queued
     * requests can never both become active loans.
     *
     * @return a human-readable result for the console
     */
    public String approveLoan(String loanId, String employeeUsername){
        Loan loan = loanDao.find(loanId);
        if(loan == null) return "loan not found";
        if(loan.isApproved()) return "loan already approved";
        LocalDate today = LocalDate.now();
        if(!loan.getStartDate().equals(today) && !loan.getStartDate().equals(today.minusDays(1)))
            return "loan can only be approved on its start date (or the day after)";
        Book b = bookDao.find(loan.getBookId());
        if(b == null) return "book not found";
        if(!b.isAvailable()) return "book is already borrowed";
        loan.setApproved(true);
        loan.setApprovedBy(employeeUsername);
        loanDao.update(loan);
        b.setAvailable(false);
        bookDao.update(b);
        return "approved";
    }

    /**
     * Records that the physical book was handed over / taken back in.
     * Only allowed for approved loans that were not received before.
     * @return {@code false} when the loan is missing, not approved or already received
     */
    public boolean receiveBook(String loanId){
        Loan loan = loanDao.find(loanId);
        if(loan == null) return false;
        if(!loan.isApproved()) return false;
        if(loan.getReceivedDate() != null) return false;
        loan.setReceivedDate(LocalDate.now());
        loanDao.update(loan);
        return true;
    }

    /**
     * Records the return of a loan: stamps the return date and, for loans
     * that were approved, frees the book again.
     *
     * @return {@code false} when the loan does not exist, does not belong
     *         to {@code studentUsername}, or was already returned
     */
    public boolean returnBook(String loanId, String studentUsername){
        Loan loan = loanDao.find(loanId);
        if(loan == null) return false;
        if(!loan.getStudentUsername().equals(studentUsername)) return false;
        if(loan.getReturnedDate() != null) return false;
        loan.setReturnedDate(LocalDate.now());
        loanDao.update(loan);
        if(loan.isApproved()){
            Book b = bookDao.find(loan.getBookId());
            if(b != null){
                b.setAvailable(true);
                bookDao.update(b);
            }
        }
        return true;
    }

    /** Full loan history of one student with not-returned/delayed counters. */
    public String studentLoanReport(String studentUsername){
        Student s = studentDao.find(studentUsername);
        if(s == null) return "Student not found";
        StringBuilder sb = new StringBuilder();
        sb.append("History for ").append(studentUsername).append("\n");
        List<Loan> loans = loanDao.findByStudent(studentUsername);
        int notReturned = 0;
        int delayed = 0;
        for(Loan loan : loans){
            sb.append(loan).append("\n");
            if(loan.getReturnedDate() == null) notReturned++;
            else if(loan.getReturnedDate().isAfter(loan.getEndDate())) delayed++;
        }
        sb.append(String.format("Total=%d notReturned=%d delayed=%d\n", loans.size(), notReturned, delayed));
        return sb.toString();
    }

    /** One-line summary shown to guests before entering the guest menu. */
    public String guestStats(){
        long currentlyLoaned = bookDao.findAll().stream().filter(b -> !b.isAvailable()).count();
        return String.format("students=%d books=%d loans=%d currentlyLoaned=%d",
                studentDao.count(), bookDao.count(), loanDao.count(), currentlyLoaned);
    }

    /**
     * Adds an employee account (used by the admin menu).
     * @return {@code false} if the username is taken
     */
    public boolean addEmployee(String username, String password){
        if(employeeDao.exists(username)) return false;
        employeeDao.insert(new Employee(username, password));
        return true;
    }

    /** Per-employee report: books added by and loans approved/returned under this employee. */
    public String employeeReport(String empUsername){
        long addedBooks = bookDao.countByAddedBy(empUsername);
        long loansApproved = loanDao.countApprovedBy(empUsername);
        long loansReturned = loanDao.countReturnedApprovedBy(empUsername);
        return String.format("Employee %s: added=%d approved=%d returned=%d",
                empUsername, addedBooks, loansApproved, loansReturned);
    }

    /** Global loan statistics: total requests, approved count, average days kept. */
    public String loanStats(){
        List<Loan> all = loanDao.findAll();
        long approved = all.stream().filter(Loan::isApproved).count();
        double avgDays = all.stream()
                .filter(l -> l.getReceivedDate() != null && l.getReturnedDate() != null)
                .mapToLong(l -> ChronoUnit.DAYS.between(l.getReceivedDate(), l.getReturnedDate()))
                .average().orElse(0.0);
        return String.format("Total requests=%d, Approved=%d, AvgDays=%.2f", all.size(), approved, avgDays);
    }

    /** Per-student loan counters plus the top-10 list of most-delayed students. */
    public String studentStats(){
        StringBuilder sb = new StringBuilder();
        List<Student> students = studentDao.findAll();
        for(Student s : students){
            List<Loan> loans = loanDao.findByStudent(s.getUsername());
            int notReturned = 0;
            int delayed = 0;
            for(Loan l : loans){
                if(l.getReturnedDate() == null) notReturned++;
                else if(l.getReturnedDate().isAfter(l.getEndDate())) delayed++;
            }
            sb.append(String.format("%s: total=%d notReturned=%d delayed=%d\n",
                    s.getUsername(), loans.size(), notReturned, delayed));
        }
        sb.append("Top 10 delayed:\n");
        students.stream()
                .sorted((a, b) -> Long.compare(delayCount(b), delayCount(a)))
                .limit(10)
                .forEach(s -> sb.append(s.getUsername()).append("\n"));
        return sb.toString();
    }

    /** Number of a student's returned-late loans (sort key for the top-10 list). */
    private long delayCount(Student s){
        return loanDao.findByStudent(s.getUsername()).stream()
                .filter(l -> l.getReturnedDate() != null && l.getReturnedDate().isAfter(l.getEndDate()))
                .count();
    }
}
