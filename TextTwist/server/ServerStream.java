import java.io.*;
import java.net.*;

public class ServerStream {

    public static final int PORT = 6789;
    public static final String IP = "10.0.0.142";

    public static final String NEWLINE = "\n";

    public static final String LOG_IN_ACK = "type=log_in_ack";
    public static final String STATUS = "&status=";
    public static final String OK = "ok";
    public static final String TOKEN = "&token=";
    public static final String FAILED = "failed";
    public static final String HOST = "&host=";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final String START_GAME_ACK = "type=start_game_ack";
    public static final String LETTERS = "&letters=";
    public static final String INVALID_TOKEN = "invalid_token";

    public static final String GUESS_ACK = "type=guess_ack";
    public static final String GAME_OVER = "&game_over=";

    public static final String END_GAME_ACK = "type=end_game_ack";
    public static final String SCORE = "&score=";

    public static void constructServerSocket() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public void acceptClient() throws IOException {
        clientSocketForServer = serverSocket.accept();
    }

    public void constructServer() throws IOException {
        serverInput = new BufferedReader(new InputStreamReader(clientSocketForServer.getInputStream()));
        serverOutput = new DataOutputStream(clientSocketForServer.getOutputStream());
    }

    public void destroyServer() throws IOException {
        clientSocketForServer.close();
    }
    
    public String serverReadLine() throws IOException {
        return serverInput.readLine();
    }

    public void logInAck(boolean ok, String token, boolean host) throws IOException {
        serverOutput.writeBytes(LOG_IN_ACK + STATUS);  
        if (ok) {
            serverOutput.writeBytes(OK + TOKEN + token);
        } else {
            serverOutput.writeBytes(FAILED);
        }
        serverOutput.writeBytes(HOST);
        if (host) {
            serverOutput.writeBytes(TRUE);
        } else {
            serverOutput.writeBytes(FALSE);
        }
        serverOutput.writeBytes(NEWLINE);
    }

    public void startGameAck(boolean ok, String letters) throws IOException {
        serverOutput.writeBytes(START_GAME_ACK + STATUS);
        if (ok) {
            serverOutput.writeBytes(OK + LETTERS + letters);
        } else {
            serverOutput.writeBytes(INVALID_TOKEN);
        }
        serverOutput.writeBytes(NEWLINE);
    }

    public void guessAck(boolean ok, boolean gameOver) throws IOException {
        serverOutput.writeBytes(GUESS_ACK + STATUS);
        if (ok) {
            serverOutput.writeBytes(OK);
        } else {
            serverOutput.writeBytes(INVALID_TOKEN);
        }
        serverOutput.writeBytes(GAME_OVER);
        if (gameOver) {
            serverOutput.writeBytes(TRUE);
        } else {
            serverOutput.writeBytes(FALSE);
        }
        serverOutput.writeBytes(NEWLINE);
    }

    public void endGameAck(boolean ok, String score) throws IOException {
        serverOutput.writeBytes(END_GAME_ACK + STATUS);
        if (ok) {
            serverOutput.writeBytes(OK + SCORE + score);
        } else {
            serverOutput.writeBytes(INVALID_TOKEN);
        }
        serverOutput.writeBytes(NEWLINE);
    }

    private static ServerSocket serverSocket;
    private BufferedReader serverInput;
    private DataOutputStream serverOutput;
    private Socket clientSocketForServer;
}
