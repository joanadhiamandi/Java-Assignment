import java.util.Collections;
import java.util.List;

/**
 * This class manages individual quiz questions.
 * It defines the structure of a question with its type (e.g., multiple choice),
 * difficulty level, category, question text, correct answer,
 * and a list of incorrect answer choices.
 * The class also provides methods to access this information
 * and verify if a user's answer matches the correct answer.
 */

public class Quiz {
    /** The type of question (e.g., multiple choice, true/false). */
    private String type;
    private String difficulty;

    private String category;
    private String question;

    private String correctAnswer;

    private List<String> incorrectAnswers;


    /**
     * Constructor to create a new Quiz object.
     *
     * @param type The type of question.
     * @param difficulty The difficulty level of the question.
     * @param category The category the question belongs to.
     * @param question The actual question text.
     * @param correctAnswer The correct answer to the question.
     * @param incorrectAnswers A list of incorrect answer choices.
     */
    public Quiz(String type, String difficulty, String category, String question, String correctAnswer, List<String> incorrectAnswers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    /**
     * Retrieves the type of question (e.g., multiple choice, true/false).
     *
     * @return The type of question.
     */
    public String getQuestionType() {
        return type;
    }

    /**
     * Retrieves the actual question text presented to the player.
     *
     * @return The question text.
     */
    public String getQuestionText() {
        return question;
    }

    /**
     * Retrieves the correct answer to the question.
     *
     * @return The correct answer.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Provides a shuffled list of all possible answers
     * (including correct and incorrect).
     *
     * @return A shuffled list of possible answers.
     */
    public List<String> getPossibleAnswers() {
        List<String> possibleAnswers = incorrectAnswers;
        possibleAnswers.add(correctAnswer);
        Collections.shuffle(possibleAnswers);
        return possibleAnswers;
    }

    /**
     * Checks if the provided player answer matches the correct answer (case-sensitive).
     *
     * @param answer The player's answer as a string.
     * @return True if the answer is correct, False otherwise.
     */
    public boolean isAnswerCorrect(String answer) {
        return this.correctAnswer.equals(answer);
    }
}
