package com.example.texttwist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static String LOG_IN = "type=log_in";
    public static String USERNAME = "&username=";
    public static final String STATUS = "status";
    public static final String OK = "ok";
    public static final String TOKEN = "token";
    public static final String HOST = "host";

    public static final String EXTRA_TOKEN = "com.example.texttwist.TOKEN";
    public static final String EXTRA_HOST = "com.example.texttwist.HOST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_prompt);
    }

    public void logIn(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Socket clientSocket = new Socket(Helpers.IP, Helpers.PORT);
                    BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                    clientOutput.writeBytes(LOG_IN + USERNAME + username.getText().toString() + Helpers.NEWLINE);
                    statusCheck = Helpers.stringToMap(clientInput.readLine(), Helpers.MESSAGE_DELIMITER, Helpers.KEY_VALUE_DELIMITER);
                    clientSocket.close();
                } catch (IOException ignored) {}
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {}

        if (statusCheck.get(STATUS).equals(OK)) {
            String token = statusCheck.get(TOKEN);
            String host = statusCheck.get(HOST);
            Intent intent = new Intent(this, WaitingActivity.class);
            intent.putExtra(EXTRA_TOKEN, token);
            intent.putExtra(EXTRA_HOST, host);
            startActivity(intent);
        } else {
            TextView logInFailText = findViewById(R.id.logInFail);
            logInFailText.setVisibility(View.VISIBLE);
        }
    }

    private EditText username;
    private HashMap<String, String> statusCheck;
}