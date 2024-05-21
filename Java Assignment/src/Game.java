import java.util.List;

/**
 * Manages the logic and state of a quiz game session.
 *   - Provides a set of 10 quizzes for each game instance.
 *   - Checks user answers and updates their score for the current game.
 *   - Integrates the current game score into the player's overall score.
 *   - Resets the current game score after each round.
 */

public class Game {
    // Holds a list of Quiz objects that represent the questions for the game
    private List<Quiz> questions;
    //UserProfile currently playing
    private UserProfile player;
    // The score of the current game
    private int gameScore;
    // The current quiz to be asked (index in the questions list)
    private int currentQuestionIndex;

    /**
     * Sets the current player for this game session.
     *
     * @param player The UserProfile object representing the user playing the game.
     */
    public void setPlayer(UserProfile player) {
        this.player = player;
    }

    /**
     * Initializes a new quiz game session by:
     *   - Fetching a set of quiz questions using a QuizReader object.
     *   - Resetting the game score and current question index.
     */
    public void startGame() {
        QuizReader api = new QuizReader();
        questions = api.quizApiReader();
        gameScore = 0;
        currentQuestionIndex = 0;
    }

    /**
     * Determines if the current question is a multiple choice question.
     *
     * @return True if the current question type is "multiple", False otherwise.
     */
    public boolean checkType() {
        return "multiple".equals(questions.get(currentQuestionIndex).getQuestionType());
    }

    /**
     * Retrieves the list of possible answers for the current question.
     * @return A list of possible answer strings.
     */
    public List<String> getChoices() {
        List<String> choices = questions.get(currentQuestionIndex).getPossibleAnswers();
        return choices;
    }

    /**
     * Retrieves and formats the text of the current question to be displayed.
     *
     * @return A String containing the formatted current question.
     */
    public String getCurrentQuestion() {
        return "Q" + (currentQuestionIndex +1) + ": " + questions.get(currentQuestionIndex).getQuestionText();
    }


    /**
     * Evaluates the user's answer for the current question.
     *   - Checks if the provided answer is correct.
     *   - Provides feedback to the user (correct/incorrect and correct answer if applicable).
     *   - Updates the score if the answer is correct.
     *   - Increments the current question index to move to the next question.
     *
     * @param answer The user's answer to the current question.
     */
    public void checkCurrentAnswer(String answer) {
        if(questions.get(currentQuestionIndex).isAnswerCorrect(answer)) {
            System.out.println("\nCorrect");
            increaseScore();
        } else {
            System.out.println("\nIncorrect");
            System.out.println("\nCorrect Answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());
        }
        currentQuestionIndex++;
    }


    public void increaseScore() {
        gameScore++;
    }

    /**
     * Returns the current score for the game session.
     *
     * @return The player's score for the current game.
     */
    public int getScore() {
        return gameScore;
    }


    public void resetScore() {
        gameScore = 0;
    }


    /**
     * Calculates and returns the player's updated total score by:
     *  1. Retrieving the player's current score.
     *  2. Adding the score earned in the current game session.
     *
     * @return The player's new total score after including the current game's results.
     */
    public int updateScore() {
        return player.getTotalScore() + gameScore;
    }
}


