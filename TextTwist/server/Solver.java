import java.io.FileNotFoundException;
import java.util.Arrays;

public class Solver {
    public static final int NUM_LETTERS = 8;
    public static final int MIN_WORD_SIZE = 2;
    public static final String FILENAME = "server/ospd.txt";

    public static Trie solve(String letters) throws FileNotFoundException {
        Trie acceptableWords = new Trie();
        acceptableWords.loadFromFile(FILENAME);

        Trie validWords = new Trie();
        letters = sort(letters);
        findAllWords(acceptableWords, validWords, "", letters);

        return validWords;
    }

    private static String sort(String letters) {
        char temp[] = letters.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }

    private static void findAllWords(Trie acceptableWords, Trie validWords, String currWord, String lettersRemaining) {
        if (acceptableWords.isPrefix(currWord)) {
            if ((currWord.length() > MIN_WORD_SIZE) && (acceptableWords.isWord(currWord))) {
                validWords.insert(currWord);
            }

            if (!lettersRemaining.isEmpty()) {
                findAllWords(acceptableWords, validWords, currWord + lettersRemaining.charAt(0), lettersRemaining.substring(1));

                for (int i = 1; i < lettersRemaining.length(); ++i) {
                    if (lettersRemaining.charAt(i) != lettersRemaining.charAt(i - 1)) {
                        findAllWords(acceptableWords, validWords, currWord + lettersRemaining.charAt(i), lettersRemaining.substring(0, i) + lettersRemaining.substring(i + 1));
                    }
                }
            }
        }
    }
}