package com.example.texttwist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import static com.example.texttwist.MainActivity.EXTRA_TOKEN;
import static com.example.texttwist.WaitingActivity.EXTRA_LETTERS;

public class GameActivity extends AppCompatActivity {

    public static String GUESS = "type=guess";
    public static String ACTUAL_GUESS = "&guess=";
    public static String TOKEN = "&token=";
    public static String GAME_OVER = "game_over";
    public static String OVER = "true";
    public static String END_GAME = "type=end_game";
    public static String SCORE = "score";

    public static final String EXTRA_SCORE = "com.example.texttwist.SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView letters = findViewById(R.id.gameLetters);
        Intent intent = getIntent();
        letters.setText(intent.getStringExtra(EXTRA_LETTERS));
    }

    public void submitGuess(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Socket clientSocket = new Socket(Helpers.IP, Helpers.PORT);
                    BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                    EditText guess = findViewById(R.id.guessText);
                    Intent intent = getIntent();
                    clientOutput.writeBytes(GUESS + ACTUAL_GUESS + guess.getText() + TOKEN + intent.getStringExtra(EXTRA_TOKEN) + Helpers.NEWLINE);
                    guessMap = Helpers.stringToMap(clientInput.readLine(), Helpers.MESSAGE_DELIMITER, Helpers.KEY_VALUE_DELIMITER);
                    clientSocket.close();
                } catch (IOException ignored) {}
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {}

        if (guessMap.get(GAME_OVER).equals(OVER)) {
            endGame(view);
        }
    }

    public void endGame(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Socket clientSocket = new Socket(Helpers.IP, Helpers.PORT);
                    BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                    Intent intent = getIntent();
                    clientOutput.writeBytes(END_GAME + TOKEN + intent.getStringExtra(EXTRA_TOKEN) + Helpers.NEWLINE);
                    guessMap = Helpers.stringToMap(clientInput.readLine(), Helpers.MESSAGE_DELIMITER, Helpers.KEY_VALUE_DELIMITER);
                    clientSocket.close();
                } catch (IOException ignored) {}
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {}

        String score = guessMap.get(SCORE);
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra(EXTRA_SCORE, score);
        startActivity(intent);
    }

    private HashMap<String, String> guessMap;
}