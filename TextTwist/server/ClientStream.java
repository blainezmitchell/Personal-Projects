import java.io.*;
import java.net.*;

public class ClientStream {

    public static final int PORT = 6789;
    public static final String IP = "10.0.0.142";

    public static String NEWLINE = "\n";

    public static String LOG_IN = "type=log_in";
    public static String USERNAME = "&username=";

    public static String START_GAME = "type=start_game";
    public static String TOKEN = "&token=";

    public static String GUESS = "type=guess";
    public static String ACTUAL_GUESS = "&guess=";

    public static String END_GAME = "type=end_game";
    
    public static void constructClient() throws IOException {
        clientSocket = new Socket(IP, PORT);
        clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientOutput = new DataOutputStream(clientSocket.getOutputStream());
    }

    public static void destroyClient() throws IOException {
        clientSocket.close();
    }

    public static String logIn(String username) throws IOException {
        clientOutput.writeBytes(LOG_IN + USERNAME + username + NEWLINE);
        return clientInput.readLine();
    }

    public static String startGame(String token) throws IOException {
        clientOutput.writeBytes(START_GAME + TOKEN + token + NEWLINE);
        return clientInput.readLine();
    }

    public static void guess(String guess, String token) throws IOException {
        clientOutput.writeBytes(GUESS + ACTUAL_GUESS + guess + TOKEN + token + NEWLINE);
        clientInput.readLine();
    }

    public static String endGame(String token) throws IOException {
        clientOutput.writeBytes(END_GAME + TOKEN + token + NEWLINE);
        return clientInput.readLine();
    }

    private static Socket clientSocket;
    private static BufferedReader clientInput;
    private static DataOutputStream clientOutput;
}
