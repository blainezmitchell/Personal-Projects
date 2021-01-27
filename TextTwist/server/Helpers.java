import java.util.HashMap;

public class Helpers {

    public static final int MIN_LENGTH = 1;
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    public static HashMap<String, String> stringToMap(String message, String messageDelimiter, String keyValueDelimiter) {
        
        // we first split by the first delimiter and initialize a map
        String arrayOfMessages[] = message.split(messageDelimiter);
        HashMap<String, String> result = new HashMap<String, String>();

        // loop through those messages and split again to add them to the map
        for (String i : arrayOfMessages) {
            String arrayOfKeyValues[] = i.split(keyValueDelimiter);
            if (arrayOfKeyValues.length > MIN_LENGTH) {
                result.put(arrayOfKeyValues[KEY_INDEX], arrayOfKeyValues[VALUE_INDEX]);
            } else {
                result.put(arrayOfKeyValues[KEY_INDEX], "");
            }
        }

        // return the final String
        return result;
    }

}
