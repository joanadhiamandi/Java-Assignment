import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

/**
 * Attempts to create a connection to a MySQL database server running on localhost.
 */

public class DbConnector {
    public static Connection establishConnection() {
        ErrorLogger errorLogger = new ErrorLogger();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JAssignment_quiz_game", "root", "");
            return con;
        } catch (ClassNotFoundException e) {
            errorLogger.logError(new Date() + "," + DbConnector.class.getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Database driver not found.");
        } catch (SQLException e) {
            errorLogger.logError(new Date() + "," + DbConnector.class.getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Could not connect to database.");
        }
        return null;
    }
}
