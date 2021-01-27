public class TrieNode {
    public final int ALPHABET_SIZE = 26;

    TrieNode(char c, boolean b) {
        myLetter = c;
        myWord = b;
        for (int i = 0; i < ALPHABET_SIZE; ++i) {
            myArr[i] = null;
        }
    }

    void insert(String str) {
        if (str.isEmpty()) {
            myWord = true;
        } else {
            int index = str.charAt(0) - 'a';
            if (myArr[index] == null) {
                myArr[index] = new TrieNode(str.charAt(0), false);
            }
            myArr[index].insert(str.substring(1));
        }
    }

    boolean isWord(String str) {
        if (str.isEmpty()) {
            return myWord;
        }
        int index = str.charAt(0) - 'a';
        if (myArr[index] == null) {
            return false;
        } else {
            return myArr[index].isWord(str.substring(1));
        }
    }

    boolean isPrefix(String pre) {
        if (pre.isEmpty()) {
            return true;
        }
        int index = pre.charAt(0) - 'a';
        if (myArr[index] == null) {
            return false;
        } else {
            return myArr[index].isPrefix(pre.substring(1));
        }
    }

    void print(String str) {
        if (myWord) {
            System.out.println(str + myLetter);
        }
        for (int i = 0; i < myArr.length; ++i) {
            if (myArr[i] != null) {
                myArr[i].print(str + myLetter);
            }
        }
    }

    int wordCount() {
        int count = 0;
        if (myWord) {
            ++count;
        }
        for (int i = 0; i < myArr.length; ++i) {
            if (myArr[i] != null) {
                count += myArr[i].wordCount();
            }
        }
        return count;
    }

    char myLetter;
    boolean myWord;
    TrieNode[] myArr = new TrieNode[ALPHABET_SIZE];
}
