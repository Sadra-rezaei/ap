package ap.projects.finalproject.database;

import ap.projects.finalproject.models.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC data access for the {@code students} table: registration support
 * (exists/insert), login lookups and the full listing used by the
 * student statistics report.
 */
public class StudentDao {
    private final Database db;

    public StudentDao(Database db){
        this.db = db;
    }

    /** @return the student with the given username, or {@code null} if absent */
    public Student find(String username){
        String sql = "SELECT username, password FROM students WHERE username = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e){
            throw new DataAccessException("find student failed", e);
        }
    }

    /** @return every student, ordered by username (stable stats output) */
    public List<Student> findAll(){
        String sql = "SELECT username, password FROM students ORDER BY username";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<Student> students = new ArrayList<>();
            while(rs.next()){
                students.add(mapRow(rs));
            }
            return students;
        } catch (SQLException e){
            throw new DataAccessException("list students failed", e);
        }
    }

    /** @return {@code true} if the username is taken (registration check) */
    public boolean exists(String username){
        String sql = "SELECT 1 FROM students WHERE username = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e){
            throw new DataAccessException("student exists check failed", e);
        }
    }

    public long count(){
        String sql = "SELECT COUNT(*) FROM students";
        try (Statement st = db.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e){
            throw new DataAccessException("student count failed", e);
        }
    }

    public void insert(Student s){
        String sql = "INSERT INTO students (username, password) VALUES (?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, s.getUsername());
            ps.setString(2, s.getPassword());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("insert student failed", e);
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(rs.getString("username"), rs.getString("password"));
    }
}
