package ap.projects.finalproject.database;

import ap.projects.finalproject.models.Loan;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC data access for the {@code loans} table: the loan lifecycle writes
 * (insert on request, update on approve/receive/return), lookups by id and
 * by student, and the aggregate counters used by the reports. Also owns
 * the loan-id generator ({@code "L" + max + 1}).
 */
public class LoanDao {
    private final Database db;

    public LoanDao(Database db){
        this.db = db;
    }

    /** @return the loan with the given id, or {@code null} if absent */
    public Loan find(String id){
        String sql = "SELECT id, book_id, student_username, start_date, end_date, approved, approved_by, " +
                "received_date, returned_date FROM loans WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e){
            throw new DataAccessException("find loan failed", e);
        }
    }

    /** @return every loan, ordered by id */
    public List<Loan> findAll(){
        String sql = "SELECT id, book_id, student_username, start_date, end_date, approved, approved_by, " +
                "received_date, returned_date FROM loans ORDER BY id";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<Loan> loans = new ArrayList<>();
            while(rs.next()){
                loans.add(mapRow(rs));
            }
            return loans;
        } catch (SQLException e){
            throw new DataAccessException("list loans failed", e);
        }
    }

    /** @return all loans of one student (their history) */
    public List<Loan> findByStudent(String studentUsername){
        String sql = "SELECT id, book_id, student_username, start_date, end_date, approved, approved_by, " +
                "received_date, returned_date FROM loans WHERE student_username = ? ORDER BY id";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, studentUsername);
            try (ResultSet rs = ps.executeQuery()) {
                List<Loan> loans = new ArrayList<>();
                while(rs.next()){
                    loans.add(mapRow(rs));
                }
                return loans;
            }
        } catch (SQLException e){
            throw new DataAccessException("list loans by student failed", e);
        }
    }

    public long count(){
        String sql = "SELECT COUNT(*) FROM loans";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e){
            throw new DataAccessException("loan count failed", e);
        }
    }

    /** Total number of approved loans (global statistic). */
    public long countApproved(){
        String sql = "SELECT COUNT(*) FROM loans WHERE approved = TRUE";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e){
            throw new DataAccessException("approved loan count failed", e);
        }
    }

    /** Number of loans approved by one employee (for employeeReport). */
    public long countApprovedBy(String employeeUsername){
        String sql = "SELECT COUNT(*) FROM loans WHERE approved_by = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, employeeUsername);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e){
            throw new DataAccessException("approved loan count by employee failed", e);
        }
    }

    /** Number of loans approved by one employee that have come back. */
    public long countReturnedApprovedBy(String employeeUsername){
        String sql = "SELECT COUNT(*) FROM loans WHERE approved_by = ? AND returned_date IS NOT NULL";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, employeeUsername);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e){
            throw new DataAccessException("returned loan count by employee failed", e);
        }
    }

    /**
     * Generates the next loan id as {@code "L<n>"} where {@code n} is the
     * highest existing numeric suffix + 1 — collision-safe even if loans
     * were ever deleted.
     */
    public String nextId(){
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(id FROM 2) AS INTEGER)), 0) FROM loans";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return "L" + (rs.getLong(1) + 1);
        } catch (SQLException e){
            throw new DataAccessException("next loan id failed", e);
        }
    }

    /** Inserts a new loan request (approved=false, dates still null). */
    public void insert(Loan l){
        String sql = "INSERT INTO loans (id, book_id, student_username, start_date, end_date, approved, " +
                "approved_by, received_date, returned_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, l.getId());
            ps.setString(2, l.getBookId());
            ps.setString(3, l.getStudentUsername());
            ps.setDate(4, Date.valueOf(l.getStartDate()));
            ps.setDate(5, Date.valueOf(l.getEndDate()));
            ps.setBoolean(6, l.isApproved());
            ps.setString(7, l.getApprovedBy());
            ps.setDate(8, l.getReceivedDate() != null ? Date.valueOf(l.getReceivedDate()) : null);
            ps.setDate(9, l.getReturnedDate() != null ? Date.valueOf(l.getReturnedDate()) : null);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("insert loan failed", e);
        }
    }

    /** Persists the lifecycle fields (approval, approver, receive/return dates). */
    public void update(Loan l){
        String sql = "UPDATE loans SET approved = ?, approved_by = ?, received_date = ?, returned_date = ? WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setBoolean(1, l.isApproved());
            ps.setString(2, l.getApprovedBy());
            ps.setDate(3, l.getReceivedDate() != null ? Date.valueOf(l.getReceivedDate()) : null);
            ps.setDate(4, l.getReturnedDate() != null ? Date.valueOf(l.getReturnedDate()) : null);
            ps.setString(5, l.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("update loan failed", e);
        }
    }

    private Loan mapRow(ResultSet rs) throws SQLException {
        Loan l = new Loan(rs.getString("id"), rs.getString("book_id"), rs.getString("student_username"),
                rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate());
        l.setApproved(rs.getBoolean("approved"));
        l.setApprovedBy(rs.getString("approved_by"));
        Date received = rs.getDate("received_date");
        if(received != null) l.setReceivedDate(received.toLocalDate());
        Date returned = rs.getDate("returned_date");
        if(returned != null) l.setReturnedDate(returned.toLocalDate());
        return l;
    }
}
