package ap.projects.finalproject.database;

import ap.projects.finalproject.models.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC data access for the {@code books} table: CRUD plus the small
 * aggregate queries used by the reports.
 */
public class BookDao {
    private final Database db;

    public BookDao(Database db){
        this.db = db;
    }

    /** @return the book with the given id, or {@code null} if absent */
    public Book find(String id){
        String sql = "SELECT id, title, author, year, available, added_by FROM books WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e){
            throw new DataAccessException("find book failed", e);
        }
    }

    /** @return every book, ordered by id (stable output for listings) */
    public List<Book> findAll(){
        String sql = "SELECT id, title, author, year, available, added_by FROM books ORDER BY id";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<Book> books = new ArrayList<>();
            while(rs.next()){
                books.add(mapRow(rs));
            }
            return books;
        } catch (SQLException e){
            throw new DataAccessException("list books failed", e);
        }
    }

    /** @return {@code true} if a book with this id exists (id-uniqueness check) */
    public boolean exists(String id){
        String sql = "SELECT 1 FROM books WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e){
            throw new DataAccessException("book exists check failed", e);
        }
    }

    public long count(){
        String sql = "SELECT COUNT(*) FROM books";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e){
            throw new DataAccessException("book count failed", e);
        }
    }

    /** Number of books credited to one employee (for employeeReport). */
    public long countByAddedBy(String employeeUsername){
        String sql = "SELECT COUNT(*) FROM books WHERE added_by = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, employeeUsername);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e){
            throw new DataAccessException("book count by employee failed", e);
        }
    }

    public void insert(Book b){
        String sql = "INSERT INTO books (id, title, author, year, available, added_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, b.getId());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getAuthor());
            ps.setInt(4, b.getYear());
            ps.setBoolean(5, b.isAvailable());
            ps.setString(6, b.getAddedBy());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("insert book failed", e);
        }
    }

    /** Persists mutable fields only (availability and who added the book). */
    public void update(Book b){
        String sql = "UPDATE books SET available = ?, added_by = ? WHERE id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setBoolean(1, b.isAvailable());
            ps.setString(2, b.getAddedBy());
            ps.setString(3, b.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("update book failed", e);
        }
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        Book b = new Book(rs.getString("id"), rs.getString("title"),
                rs.getString("author"), rs.getInt("year"));
        b.setAvailable(rs.getBoolean("available"));
        b.setAddedBy(rs.getString("added_by"));
        return b;
    }
}
