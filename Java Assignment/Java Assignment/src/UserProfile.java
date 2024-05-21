import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class manages user accounts, including registering new users,
 * logging in existing users, and storing player data like scores and game history.
 * It also provides methods to retrieve the scoreboard and player statistics.
 */

public class UserProfile {
    private Connection connection = DbConnector.establishConnection();
    private ErrorLogger logger = new ErrorLogger();
    private Game game;
    private int id;
    private String username;
    private String password;
    private int score;

    
    /**
     * Constructor for a currently logged-in user (creates a UserProfile based on credentials)
     * @param username The username of the player.
     * @param password The user's password.
     */
    public UserProfile(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for a new user (creates a UserProfile in the database)
     * @param id gives new player's id
     * @param username gives new player's username
     * @param password gives new player's password
     * @param score gives new player's score
     */
    public UserProfile(int id, String username, String password, int score) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.score = score;
    }

    /**
     * Saves the updated score of the current user to the database.
     */
    public void saveNewPlayer() {
        String query = "INSERT INTO players (username, password, score) VALUES (?, ?, 0)";
        try {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            ps.setString(2, hashedPassword);
            ps.execute();
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nUsername already exists. Please choose another.");
        }
    }

    /**
     * Checks if the player provided exists and or if the credentials are correct
     * @return True if the player exists and the provided credentials are correct or
     * false if the player exists but its credentials are wrong
     */
    public boolean loadPlayer() {
        String query = "SELECT * FROM players WHERE username=?";
        try {
            assert connection != null;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                boolean match = BCrypt.checkpw(password, rs.getString("password"));
                if(match) {
                    id = rs.getInt("id");
                    score = rs.getInt("score");
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Login failed.");
        }
        return false;
    }

    /**
     * Saves the updated score of the current user to the database.
     */
    public void updateScore() {
        int newScore = game.updateScore();
        String query = "UPDATE players SET score=? WHERE id=?";
        try {
            assert connection != null;
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, newScore);
            st.setInt(2, id);
            st.execute();
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Saving score failed.");
        }
    }

    /**
     * Saves the results of the current game for the logged-in user.
     */
    public void saveGameHistory() {
        String query = "INSERT INTO gamehistory (username, date, playerScore, playerID) VALUES (?, NOW(), ?, ?)";
        try {
            assert connection != null;
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, username);
            st.setInt(2, game.getScore());
            st.setInt(3, id);
            st.execute();
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Saving game history failed.");
        }
    }

    /**
     * Retrieves the game history of the current user.
     * @return A list of strings representing the user's game history entries.
     */
    public List<String> fetchPlayerHistory() {
        List<String> history = new ArrayList<>();
        String query = "SELECT * FROM gamehistory WHERE playerID=?";
        try {
            assert connection != null;
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                history.add(rs.getString("username") + "   " + rs.getString("date") + "       " + rs.getString("playerScore"));
            }
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Retrieving user history failed.");
        }
        return history;
    }

    /**
     * Retrieves the leaderboard showing players with the highest total scores.
     * @return A list of strings representing the leaderboard entries.
     */
    public List<String> getLeaderboard() {
        List<String> topScores = new ArrayList<>();
        String query = "SELECT username, score FROM players ORDER BY score DESC";
        try {
            assert connection != null;
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet results = st.executeQuery();
            while(results.next()) {
                topScores.add(results.getString("username") + "         " + results.getString("score"));
            }
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Retrieving leaderboard failed.");
        }
        return topScores;
    }


    /**
     * Retrieves the leaderboard showing players with the most consistent average scores.
     * @return A list of strings representing the average score leaderboard entries.
     */
    public List<String> getAverageScores() {
        List<String> averageScores = new ArrayList<>();
        String query = "SELECT username, AVG(score) AS Average FROM gamehistory GROUP BY playerID ORDER BY Average DESC";
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                averageScores.add(results.getString("username") + "        " + results.getString("Average"));
            }
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: Retrieving average score leaderboard failed.");
        }
        return averageScores;
    }

    /** Sets the current game instance for this user profile. */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Retrieves the user's total score from the database.
     * @return The user's total score.
     */
    public int getTotalScore() {
        return score;
    }

    /**
     * Gets the available categories of quizzes and puts them in a list
     * @return The list of categories
     */
    public List<QuestionCategories> getCategories() {
        List<QuestionCategories> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                int category_id = rs.getInt("category_id");
                String category = rs.getString("category");
                String api_url = rs.getString("api_url");

                QuestionCategories aCategory = new QuestionCategories(category_id, category, api_url);
                categories.add(aCategory);
            }
        } catch (SQLException e) {
            logger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nError: loading categories");
        }
        return categories;
    }

}
