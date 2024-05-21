import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class fetches quiz questions from Open Trivia DbConnector API
 * and converts them into Quiz objects.
 */



public class QuizReader {
    private ErrorLogger errorLogger;

    public QuizReader() {
        errorLogger = new ErrorLogger();
    }

    /**
     *Iterates through a list of JSON quiz objects and creates corresponding Quiz instances.
     * Each JSONObject within the provided data is used to construct a single Quiz object.
     * @param item A JSONObject containing an array of quiz objects.
     * @return A List containing the newly created Quiz objects.
      */

    private static List<Quiz> extractQuizzes(JSONObject item) {
        String type = (String) item.get("type");
        String difficulty = (String) item.get("difficulty");
        String category = (String) item.get("category");
        String question = (String) item.get("question");
        String correctAnswer = (String) item.get("correct_answer");
        JSONArray incorrectAnswersArray = (JSONArray) item.get("incorrect_answers");
        List<String> incorrectAnswers = new ArrayList<>();
        for (Object answer : incorrectAnswersArray) {
            incorrectAnswers.add((String) answer);
        }
        List<Quiz> quizList = new ArrayList<>();
        Quiz aQuiz = new Quiz(type, difficulty, category, question, correctAnswer, incorrectAnswers);
        quizList.add(aQuiz);
        return quizList;
    }



    /**
     * Fetches 10 quiz objects from the Open Trivia DbConnector API using the provided URL.
     * The retrieved JSON data is parsed to extract individual quiz information.
     * These quiz details are then used to create a List of corresponding Quiz objects.
     * @return A List containing the newly constructed Quiz objects.
    */

    public List<Quiz> quizApiReader(String category) {
        List<Quiz> quizArrayList = new ArrayList<>();
        try {
            URL url = new URL("https://opentdb.com/api.php?amount=10" + category);
            InputStreamReader isr = new InputStreamReader(url.openStream());
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(isr);
            JSONArray quizzes = (JSONArray) object.get("results");
            for(Object item : quizzes ) {
                quizArrayList.addAll(extractQuizzes((JSONObject) item));
            }
        } catch (MalformedURLException e) {
            errorLogger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nInvalid URL");
        } catch (IOException e) {
            errorLogger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\nUnreadable URL");
        } catch (ParseException e) {
            errorLogger.logError(new Date() + "," + getClass().getSimpleName() + ", " + e.getMessage());
            System.out.println("\n Parse Failure");
        }
        return quizArrayList;
    }
}