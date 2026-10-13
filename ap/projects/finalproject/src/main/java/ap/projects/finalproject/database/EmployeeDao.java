package ap.projects.finalproject.database;

import ap.projects.finalproject.models.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC data access for the {@code employees} table: login lookup and
 * account creation by the admin (seeding is done in {@link Database}).
 */
public class EmployeeDao {
    private final Database db;

    public EmployeeDao(Database db){
        this.db = db;
    }

    /** @return the employee with the given username, or {@code null} if absent */
    public Employee find(String username){
        String sql = "SELECT username, password FROM employees WHERE username = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return new Employee(rs.getString("username"), rs.getString("password"));
                }
                return null;
            }
        } catch (SQLException e){
            throw new DataAccessException("find employee failed", e);
        }
    }

    /** @return {@code true} if the username is taken */
    public boolean exists(String username){
        String sql = "SELECT 1 FROM employees WHERE username = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e){
            throw new DataAccessException("employee exists check failed", e);
        }
    }

    public void insert(Employee e){
        String sql = "INSERT INTO employees (username, password) VALUES (?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, e.getUsername());
            ps.setString(2, e.getPassword());
            ps.executeUpdate();
        } catch (SQLException e2){
            throw new DataAccessException("insert employee failed", e2);
        }
    }
}
