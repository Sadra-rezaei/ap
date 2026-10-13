package ap.projects.finalproject.database;

/**
 * Thrown when a database operation fails. Wraps the checked
 * {@link java.sql.SQLException} so callers stay readable; the original
 * cause (with the driver's message, e.g. an authentication failure)
 * is always attached.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause){
        super(message, cause);
    }
}
