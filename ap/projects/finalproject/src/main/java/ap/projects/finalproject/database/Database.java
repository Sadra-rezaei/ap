package ap.projects.finalproject.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Owns the single JDBC connection to the PostgreSQL database and creates
 * the schema on first run.
 *
 * <p>Connection settings (host/port/name/user/password) come from
 * {@code db.properties}; if that file cannot be found, built-in defaults
 * are used. The schema (see {@code schema.sql}) is created idempotently
 * with {@code CREATE TABLE IF NOT EXISTS}, and the employee accounts are
 * seeded once when the table is empty.</p>
 */
public class Database {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public Database(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /** Builds the database settings from db.properties (with defaults). */
    public static Database fromProperties(){
        Properties props = new Properties();
        List<File> searched = new ArrayList<>();
        File found = findPropertiesFile(searched);
        if(found != null){
            System.out.println("db.properties: " + found.getAbsolutePath());
            try (FileInputStream in = new FileInputStream(found)) {
                props.load(in);
            } catch (IOException e){
                throw new DataAccessException("Could not read " + found.getAbsolutePath(), e);
            }
        } else {
            System.out.println("db.properties not found in any of: " + searched
                    + " — using defaults");
        }
        String host = props.getProperty("db.host", "localhost");
        String port = props.getProperty("db.port", "5432");
        String name = props.getProperty("db.name", "library_db");
        String user = props.getProperty("db.user", "postgres");
        String password = props.getProperty("db.password", "1234");
        return new Database(String.format("jdbc:postgresql://%s:%s/%s", host, port, name), user, password);
    }

    /**
     * Looks for db.properties next to the working directory and then walks up
     * from the compiled-classes folder a few levels (classes dir, target,
     * project root, ...) — so the app connects no matter which folder the IDE
     * launches it from.
     */
    private static File findPropertiesFile(List<File> searched){
        File inCwd = new File("db.properties").getAbsoluteFile();
        searched.add(inCwd);
        if(inCwd.exists()) return inCwd;
        try {
            File codeSource = new File(Database.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            File current = codeSource.isDirectory() ? codeSource : codeSource.getParentFile();
            for(int i = 0; i < 4 && current != null; i++){
                File candidate = new File(current, "db.properties");
                searched.add(candidate);
                if(candidate.exists()) return candidate;
                current = current.getParentFile();
            }
        } catch (Exception ignored) {
            // fall through: no db.properties found
        }
        return null;
    }

    /** Lazy singleton connection; all DAOs share it. */
    public Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e){
                throw new DataAccessException("Cannot connect to database (" + url + "): "
                        + e.getMessage() + " — check db.properties", e);
            }
        }
        return connection;
    }

    /**
     * Creates all tables if missing and seeds the employee accounts
     * (emp1..emp3) the first time the database is empty. Safe to call
     * on every startup.
     */
    public void initDatabase(){
        String[] ddl = {
            "CREATE TABLE IF NOT EXISTS employees (" +
                "username VARCHAR(50) PRIMARY KEY, " +
                "password VARCHAR(100) NOT NULL)",
            "CREATE TABLE IF NOT EXISTS students (" +
                "username VARCHAR(50) PRIMARY KEY, " +
                "password VARCHAR(100) NOT NULL)",
            "CREATE TABLE IF NOT EXISTS books (" +
                "id VARCHAR(50) PRIMARY KEY, " +
                "title VARCHAR(200) NOT NULL, " +
                "author VARCHAR(100), " +
                "year INT, " +
                "available BOOLEAN NOT NULL DEFAULT TRUE, " +
                "added_by VARCHAR(50))",
            "CREATE TABLE IF NOT EXISTS loans (" +
                "id VARCHAR(50) PRIMARY KEY, " +
                "book_id VARCHAR(50) NOT NULL REFERENCES books(id), " +
                "student_username VARCHAR(50) NOT NULL REFERENCES students(username), " +
                "start_date DATE NOT NULL, " +
                "end_date DATE NOT NULL, " +
                "approved BOOLEAN NOT NULL DEFAULT FALSE, " +
                "approved_by VARCHAR(50), " +
                "received_date DATE, " +
                "returned_date DATE)"
        };
        try (Statement st = getConnection().createStatement()) {
            for(String sql : ddl){
                st.execute(sql);
            }
        } catch (SQLException e){
            throw new DataAccessException("Could not create tables", e);
        }
        seedEmployees();
    }

    /** Inserts the default employee accounts once, when the table is empty. */
    private void seedEmployees(){
        String countSql = "SELECT COUNT(*) FROM employees";
        String insertSql = "INSERT INTO employees (username, password) VALUES (?, ?)";
        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(countSql)) {
            if(rs.next() && rs.getLong(1) == 0){
                try (PreparedStatement ps = getConnection().prepareStatement(insertSql)) {
                    for(String emp : new String[]{"emp1", "emp2", "emp3"}){
                        ps.setString(1, emp);
                        ps.setString(2, "1234");
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }
        } catch (SQLException e){
            throw new DataAccessException("Could not seed employees", e);
        }
    }
}
