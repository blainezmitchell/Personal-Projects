import java.io.*;
import java.util.Scanner;

public class Trie {
    Trie() {
        myRoot = new TrieNode(' ', false);
    }

    void insert(String word) {
        myRoot.insert(word);
    }

    void loadFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scnr = new Scanner(file);
        String words;
        while (scnr.hasNextLine()) {
            words = scnr.nextLine();
            myRoot.insert(words);
        }
        scnr.close();
    }

    boolean isWord(String word) {
        return myRoot.isWord(word);
    }

    boolean isPrefix(String word) {
        return myRoot.isPrefix(word);
    }

    void print() {
        myRoot.print("");
    }

    int wordCount() {
        return myRoot.wordCount();
    }

    TrieNode myRoot;
}
