package com.example.texttwist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import static com.example.texttwist.MainActivity.EXTRA_TOKEN;
import static com.example.texttwist.MainActivity.EXTRA_HOST;

public class WaitingActivity extends AppCompatActivity {

    public static String HOST = "true";
    public static String START_GAME = "type=start_game";
    public static String TOKEN = "&token=";
    public static String LETTERS = "letters";

    public static final String EXTRA_LETTERS = "com.example.texttwist.LETTERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        Intent intent = getIntent();
        String host = intent.getStringExtra(EXTRA_HOST);
        if (host.equals(HOST)) {
            TextView startGameButton = findViewById(R.id.startGameButton);
            startGameButton.setVisibility(View.VISIBLE);
        } else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket clientSocket = new Socket(Helpers.IP, Helpers.PORT);
                        BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        letters = Helpers.stringToMap(clientInput.readLine(), Helpers.MESSAGE_DELIMITER, Helpers.KEY_VALUE_DELIMITER);
                        clientSocket.close();
                    } catch (IOException ignored) {}
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ignored) {}

            String token = intent.getStringExtra(EXTRA_TOKEN);
            String gameLetters = letters.get(LETTERS);
            intent = new Intent(this, GameActivity.class);
            intent.putExtra(EXTRA_TOKEN, token);
            intent.putExtra(EXTRA_LETTERS, gameLetters);
            startActivity(intent);
        }
    }

    public void startGame(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = getIntent();
                    token = intent.getStringExtra(EXTRA_TOKEN);
                    Socket clientSocket = new Socket(Helpers.IP, Helpers.PORT);
                    BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                    clientOutput.writeBytes(START_GAME + TOKEN + token + Helpers.NEWLINE);
                    letters = Helpers.stringToMap(clientInput.readLine(), Helpers.MESSAGE_DELIMITER, Helpers.KEY_VALUE_DELIMITER);
                    clientSocket.close();
                } catch (IOException ignored) {}
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {}

        String gameLetters = letters.get(LETTERS);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_TOKEN, token);
        intent.putExtra(EXTRA_LETTERS, gameLetters);
        startActivity(intent);
    }

    private String token;
    private HashMap<String, String> letters;
}