import java.io.*;

/**
 * Writes logError messages to a log file ("errors.log") for tracking and debugging purposes.
 * This class ensures errors are recorded during program execution.
 */


public class ErrorLogger {
    public void logError(String message) {
        try {
            File errorFile = new File("errors.log");
            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(errorFile, true)));
            writer.write(message);
            writer.println();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: Could not write to error log:");
        }
    }
}
