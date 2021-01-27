package com.example.texttwist;

import java.util.HashMap;

public class Helpers {
    public static final int PORT = 6789;
    public static final String IP = "18.191.191.41";

    public static final int MIN_LENGTH = 1;
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    public static String NEWLINE = "\n";

    public static final String MESSAGE_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    public static HashMap<String, String> stringToMap(String message, String messageDelimiter, String keyValueDelimiter) {

        String[] arrayOfMessages = message.split(messageDelimiter);
        HashMap<String, String> result = new HashMap<>();

        for (String i : arrayOfMessages) {
            String[] arrayOfKeyValues = i.split(keyValueDelimiter);
            if (arrayOfKeyValues.length > MIN_LENGTH) {
                result.put(arrayOfKeyValues[KEY_INDEX], arrayOfKeyValues[VALUE_INDEX]);
            } else {
                result.put(arrayOfKeyValues[KEY_INDEX], "");
            }
        }

        return result;
    }

}
