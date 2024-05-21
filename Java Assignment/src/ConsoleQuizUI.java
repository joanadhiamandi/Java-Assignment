import java.util.List;
import java.util.Scanner;

/**
 * Provides a text-based interface for interacting with the quiz game.
 */

public class ConsoleQuizUI {
    //reads user input from the command line
    private Scanner scanner;
    //reference to the game logic object
    private Game game;
    //reference to the currently playing player object
    private UserProfile player;

    /**
     * constructor
     * Initializes a new ConsoleQuizUI instance. This constructor creates objects
     * for user input handling (Scanner) and references the game logic (Game) and
     * currently playing player (UserProfile)
     */
    public ConsoleQuizUI() {
        game = new Game();
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the authenticateUser menu, giving player the choice to log in to an existing account
     * or register a new account.
     * @return The user's choice (1 for login, 2 for register, 0 for exit).
     */
    public int displayLoginMenu() {
        System.out.println("\n1. LOGIN");
        System.out.println("2. REGISTER");
        System.out.println("0. EXIT");
        System.out.print("\nCHOSE: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    /**
     * Displays the game menu, providing the player
     * the option to start the game or view his or other player's stats
     * @return the user's choice
     */
    public int displayPlayerMenu() {
        System.out.println("\n1. PLAY");
        System.out.println("2. GAME HISTORY");
        System.out.println("3. SCOREBOARDS");
        System.out.println("0. LOG OUT");
        System.out.print("\nCHOSE: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    /**
     * Presents the statistics menu to the user. This menu allows them to explore
     * their quizzing prowess by checking either the highest score achieved or
     * their most consistent performance across multiple attempts.
     *
     * @return the user's chosen option from the statistics menu.
     */
    public int displayStatisticsMenu() {
        System.out.println("\n1. BEST SCORES");
        System.out.println("2. THE MOST CONSISTENT PLAYERS");
        System.out.println("0. GO BACK");
        System.out.print("\nCHOSE: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
    /**
     * The game history of the player will be shown
     */
    public void displayHistory() {
        System.out.println("\n# || PLAYER ||  DATE  ||  TIME  ||  SCORE || ");
        int index = 1;
        if (player.fetchPlayerHistory().isEmpty())
            System.out.println("EMPTY");
        else {
            for (String item : player.fetchPlayerHistory()) {
                System.out.println(index + ". " + item);
                index++;
            }
        }
    }





    /**
     * Presents the current quiz question to the user. This method retrieves the question from the game object,
     * displays it, and then handles different question types:
     *   - Multiple Choice: Lists the available answer choices for the user to select from.
     *   - True or False: Prompts the user to enter "TRUE" or "FALSE" as their answer.
     * The user's score is also displayed, and their answer is collected and sent to the game object for checking.
     */
    public void presentCurrentQuestion() {
        System.out.println("\n" + game.getCurrentQuestion());
        if (game.checkType()) {
            System.out.print("\n");
            int index = 1;
            for (String item : game.getChoices()) {
                System.out.println(index + ") " + item);
                index++;
            }
        } else {
            System.out.println("\nTRUE  ||  FALSE ");
        }
        System.out.println("\nYOUR SCORE CURRENTLY: " + game.getScore());
        System.out.print("\nANSWER: ");
        String answer = scanner.nextLine();
        game.checkCurrentAnswer(answer);
    }

    /**
     * Prompts the player if they want to play another round of the quiz.
     * This method displays a message asking the player's name and their decision
     * (yes or no) for another round. It then reads the user's input as a string
     * and returns it.
     *
     * @return the user's response ("y" for yes, "n" for no) as a String.
     */

    public String promptForAnotherRound() {
        System.out.print("\nANOTHER ROUND (y or n): ");
        String string = scanner.nextLine();
        return string;
    }


    /**
     * Prompts the user to create a new account for the quiz game.
     * This method asks for a unique username and password,
     * then creates a new UserProfile object with those credentials.
     * Finally, it attempts to save the new player information.
     */
    public void createUserAccount() {
        System.out.print("\nUSERNAME: ");
        String username = scanner.nextLine();
        System.out.print("PASSWORD: ");
        String password = scanner.next();
        player = new UserProfile(username, password);
        player.saveNewPlayer();
    }


    /**
     * Attempts to authenticate a user by verifying their login credentials.
     * This method prompts the user for their username and password,
     * then creates a UserProfile object with those credentials.
     * It then checks if a matching player exists and their credentials are correct.
     *
     * @return true if the user credentials are valid, false otherwise.
     */
    public boolean authenticateUser() {
        System.out.print("\nUSERNAME: ");
        String username = scanner.nextLine();
        System.out.print("PASSWORD: ");
        String password = scanner.nextLine();
        player = new UserProfile(username, password);
        boolean loadPlayer = player.loadPlayer();
        game.setPlayer(player);
        player.setGame(game);
        return loadPlayer;
    }


    /**
     * It guides the user through the overall game flow, including:
     *  - Welcoming message and login/register options
     *  - Handling user login attempts and displaying the main menu
     *  - Processing user choices for playing the game, viewing history, or statistics
     *  - Prompting for another round after each game session
     *  - Ending the game with a farewell message
     */
    public void run() {
        int choice = 0;
        System.out.println("WELCOME! \n");
        System.out.println("LOGIN / REGISTER \n");
        do {
            choice = displayLoginMenu();
            switch (choice) {
                case 1:
                    if (authenticateUser()) {
                        int playerMenuChoice;
                        do {
                            playerMenuChoice = displayPlayerMenu();
                            switch (playerMenuChoice) {
                                case 1:
                                    DisplayCategory();
                                    String palyAgainChoice;
                                    do {
                                        game.startGame();
                                        for (int i = 0; i < 10; i++) {
                                            presentCurrentQuestion();
                                        }
                                        System.out.println("\n SCORE: " + game.getScore());
                                        player.updateScore();
                                        player.saveGameHistory();
                                        player.loadPlayer();
                                        game.resetScore();
                                        palyAgainChoice = promptForAnotherRound();
                                    } while (palyAgainChoice.equals("y"));
                                    break;
                                case 2:
                                    displayHistory();
                                    break;
                                case 3:
                                    int statsMenuChoice;
                                    do {
                                        statsMenuChoice = displayStatisticsMenu();
                                        switch (statsMenuChoice) {
                                            case 1:
                                                System.out.println("\n     SCOREBOARD");
                                                System.out.println("\n#  PLAYERS AND THEIR SCORE");
                                                int po1 = 1;
                                                if (player.getLeaderboard().isEmpty())
                                                    System.out.println("EMPTY");
                                                else {
                                                    for (String item : player.getLeaderboard()) {
                                                        System.out.println(po1 + ". " + item);
                                                        po1++;
                                                    }
                                                }
                                                break;
                                            case 2:
                                                System.out.println("\n   THE MOST CONSISTENT");
                                                System.out.println("\n#  PLAYER AND AVERAGE SCORE");
                                                int po2 = 1;
                                                for (String item : player.getAverageScores()) {
                                                    System.out.println(po2 + ". " + item);
                                                    po2++;
                                                }
                                                break;
                                        }
                                    } while (statsMenuChoice != 0);
                            }
                        } while (playerMenuChoice != 0);
                    } else {
                        System.out.println("\nINCORRECT ");
                        break;
                    }
                    break;
                case 2:
                    createUserAccount();
                    break;
            }
        } while (choice != 0);
        System.out.println("\nHOPE YOU HAD FUN");
        System.out.println("\nSEE YOU NEXT TIME!!!!!!!");
    }
    /**
     * Displays the available categories and asks the player
     * to choose, it then calls the selectCategory method from
     * player class providing with the list of categories and
     * the player's choice
     */
    public void DisplayCategory() {
        System.out.println();
        List<QuestionCategories> categories = player.getCategories();
        for (QuestionCategories aCategory : categories) {
            System.out.println(aCategory.getCategoryId() + ") " + aCategory.getCategoryName());
        }
        System.out.print("\nSelect: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        game.selectCategory(categories, choice);
    }

}
