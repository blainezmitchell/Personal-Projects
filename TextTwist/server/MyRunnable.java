import java.io.*;
import java.util.*;

public class MyRunnable implements Runnable {

    public static final String MESSAGE_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String MESSAGE_TYPE = "type";
    public static final String LOG_IN = "log_in";
    public static final String USERNAME = "username";
    public static final String START_GAME = "start_game";
    public static final String FILENAME = "server/ospd.txt";
    public static final int MAX_WORD_LENGTH = 8;
    public static final int NUM_MAX_WORDS = 28000;
    public static final String TOKEN = "token";
    public static final String GUESS = "guess";
    public static final String END_GAME = "end_game";

    public MyRunnable() throws IOException {
        myServerStream = new ServerStream();
        myServerStream.acceptClient();
    }

    public void run() {
        try {
            myServerStream.constructServer();
            
            String message = myServerStream.serverReadLine();
            System.out.println(message);
            HashMap<String, String> request = Helpers.stringToMap(message, MESSAGE_DELIMITER, KEY_VALUE_DELIMITER);
            boolean ok = true;

            if (request.get(MESSAGE_TYPE).equals(LOG_IN)) {
                gameOver = false;
                boolean host = usernames.isEmpty();
                if (usernames.contains(request.get(USERNAME))) {
                    ok = false;
                }
                String token = "";
                if (ok) {
                    token = generateToken();
                    scores.put(token, 0);
                    guessed.put(token, new ArrayList<>());
                }
                myServerStream.logInAck(ok, token, host);
            } else {
                ok = tokens.contains(request.get(TOKEN));
                if (request.get(MESSAGE_TYPE).equals(START_GAME)) {
                    String letters = "";
                    if (ok) {
                        letters = generateLetters();
                        validWords = Solver.solve(letters);
                        char temp[] = letters.toCharArray();
                        Arrays.sort(temp);
                        letters = new String(temp);
                    }
                    myServerStream.startGameAck(ok, letters);
                } else if (request.get(MESSAGE_TYPE).equals(GUESS)) {
                    if (ok) {
                        if ((validWords.isWord(request.get(GUESS))) && (!guessed.get(request.get(TOKEN)).contains(request.get(GUESS)))) {
                            updatePoints(request.get(TOKEN), request.get(GUESS));
                        }
                    }
                    myServerStream.guessAck(ok, gameOver);
                } else if (request.get(MESSAGE_TYPE).equals(END_GAME)) {
                    gameOver = true;
                    Integer score = 0;
                    if (ok) {
                        score = scores.get(request.get(TOKEN));
                    }
                    myServerStream.endGameAck(ok, score.toString());
                    tokenCount = 0;
                    usernames = new ArrayList<>();
                    tokens = new ArrayList<>();
                    scores = new HashMap<String, Integer>();
                    guessed = new HashMap<String, ArrayList<String>>();
                }
            }
        } catch (IOException e) {}
    }

    private String generateToken() {
        String token = tokenCount.toString();
        tokens.add(token);
        ++tokenCount;
        return token;
    }

    private String generateLetters() throws FileNotFoundException {
        File file = new File(FILENAME);
        Scanner scnr = new Scanner(file);
        Random random = new Random();
        boolean maxLength = false;
        String letters = "";
        int j = random.nextInt(NUM_MAX_WORDS);
        for (int i = 0; i < j; ++i) {
            while (!maxLength) {
                letters = scnr.nextLine();
                if (letters.length() == MAX_WORD_LENGTH) {
                    maxLength = true;
                }
            }
            maxLength = false;
        }
        scnr.close();
        return letters;
    }

    private void updatePoints(String token, String guess) {
        scores.put(token, scores.get(token) + guess.length());
        guessed.get(token).add(guess);
    }

    private ServerStream myServerStream;
    private static Integer tokenCount = 0;
    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> tokens = new ArrayList<>();
    private static Trie validWords;
    private static HashMap<String, Integer> scores = new HashMap<String, Integer>();
    private static HashMap<String, ArrayList<String>> guessed = new HashMap<String, ArrayList<String>>();
    private static boolean gameOver = false;
}
