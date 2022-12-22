import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children; // 'a' -> 0 'b' -> 1
    boolean isLeaf;
    String word;

    public TrieNode() {
        this.children = new HashMap<>();
    }
}

class Trie {
    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void addWord(String word) {
        if(word.length() == 0)
            return;
        TrieNode p = root;
        String lowerCased = word.trim().toLowerCase();
        for(int i = 0; i < lowerCased.length(); i++) {
            char c = lowerCased.charAt(i);
            if(!p.children.containsKey(c)) {
                p.children.put(c, new TrieNode());
            }
            p = p.children.get(c);
        }
        p.isLeaf = true;
        p.word = lowerCased;
    }

    public List<String> getWordsWithPrefix(String prefix) {
        List<String> res = new ArrayList<>();
        if(prefix.length() == 0)
            return res;

        String lowerCased = prefix.toLowerCase();
        TrieNode p = root;

        for(int i = 0; i < lowerCased.length(); i++) {
            char c = lowerCased.charAt(i);
            if(!p.children.containsKey(c)){
                return res;
            }
            p = p.children.get(c);
        }

        helper(res, p);
        return res;
    }

    private void helper(List<String> res, TrieNode node) {
        if(node == null)
            return;

        if(node.isLeaf)
            res.add(node.word);

        for(Character c: node.children.keySet()) {
            helper(res, node.children.get(c));
        }
    }
}

public class TypeAhead {
    Trie trie;

    private static final String FILE_PATH = "src/words.txt";
    private static final String TEST_PATH = "src/fixture.txt";

    public TypeAhead() {
        this.trie = new Trie();
    }

    public void initialize(String filePath) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                trie.addWord(data.trim());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public List<String> getWordsWithPrefix(String prefix) {
        return this.trie.getWordsWithPrefix(prefix);
    }


    public static void main(String[] args) {
        TypeAhead typeAhead = new TypeAhead();
        typeAhead.initialize(TEST_PATH);
        List<String> res = typeAhead.getWordsWithPrefix(" ");
        for(String str: res)
            System.out.println(str);
    }
}

