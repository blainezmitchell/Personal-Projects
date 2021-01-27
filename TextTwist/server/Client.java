import java.io.IOException;
import java.util.*;

public class Client {

    public static final String USERNAME_PROMPT = "What is your username? ";
    public static final String MESSAGE_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String STATUS = "status";
    public static final String OK = "ok";
    public static final String USERNAME_TAKEN = "Sorry. That username is taken.";
    public static final String TOKEN = "token";
    public static final String QUIT = "qqqqqqqq";
    public static final String LETTERS = "letters";
    public static final String SCORE = "score";

    public static void main(String[] args) throws IOException {

        Scanner scnr = new Scanner(System.in);
        ClientStream.constructClient();
        getUsername(scnr);
        ClientStream.destroyClient();
        System.out.print("Press enter to start.");
        scnr.nextLine();
        ClientStream.constructClient();
        String letters = ClientStream.startGame(token);
        ClientStream.destroyClient();
        HashMap<String, String> gameLetters = Helpers.stringToMap(letters, MESSAGE_DELIMITER, KEY_VALUE_DELIMITER);
        System.out.println(gameLetters.get(LETTERS) + " are the letters for this game!");
        System.out.println();
        String guess = "";
        boolean continueGame = true;
        while (continueGame) {
            System.out.print("Enter guess: ");
            guess = scnr.nextLine();
            if (guess.equals(QUIT)) {
                continueGame = false;
                ClientStream.constructClient();
                String score = ClientStream.endGame(token);
                ClientStream.destroyClient();
                HashMap<String, String> gameScore = Helpers.stringToMap(score, MESSAGE_DELIMITER, KEY_VALUE_DELIMITER);
                System.out.println("Your score is " + gameScore.get(SCORE));
            } else {
                ClientStream.constructClient();
                ClientStream.guess(guess, token);
                ClientStream.destroyClient();
            }
        }
    }

    public static void getUsername(Scanner scnr) throws IOException {
        System.out.println();
        System.out.print(USERNAME_PROMPT);
        String username = scnr.nextLine();
        System.out.println();
        String response = ClientStream.logIn(username);
        HashMap<String, String> tokenMap = Helpers.stringToMap(response, MESSAGE_DELIMITER, KEY_VALUE_DELIMITER);
        while (!tokenMap.get(STATUS).equals(OK)) {
            System.out.println(USERNAME_TAKEN);
            System.out.println();
            System.out.print(USERNAME_PROMPT);
            username = scnr.nextLine();
            response = ClientStream.logIn(username);
            tokenMap = Helpers.stringToMap(response, MESSAGE_DELIMITER, KEY_VALUE_DELIMITER);
        }
        token = tokenMap.get(TOKEN);
    }

    private static String token;
}
