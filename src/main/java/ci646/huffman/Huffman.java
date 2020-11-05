package ci646.huffman;

import ci646.huffman.tree.Branch;
import ci646.huffman.tree.Leaf;
import ci646.huffman.tree.Node;

import java.util.*;

public class Huffman {

    private Map<Character, Integer> freqTable;
    private PQueue queue;
    private Node tree;
    private Map<Character, List<Boolean>> code;

    public HuffmanCoding encode(String input) {
        freqTable = new HashMap<>();
        queue = new PQueue();
        code = new HashMap<>();
        freqTable = buildFreqTable(input);
        tree = buildTree(freqTable);
        buildCode(tree);
        List<Boolean> result = new ArrayList<>();
        for(int i=0;i<input.length();i++) {
            result.addAll(code.get(input.charAt(i)));
        }
        return new HuffmanCoding(code, result);
    }

    public String decode(List<Boolean> cs) {
        StringBuffer sb = new StringBuffer();
        Node n = tree;
        for (Boolean b: cs) {
            if (n instanceof Branch) {
                n = b ? ((Branch) n).getRight() : ((Branch) n).getLeft();
            }
            if (n instanceof Leaf) {
                sb.append(((Leaf) n).getLabel());
                n = tree;
            }
        }
        return sb.toString();
    }

    private Map<Character, Integer> buildFreqTable(String input) {
        Map<Character, Integer> f = new HashMap<>();
        char[] strArray = input.toCharArray();
        for (char c : strArray) {
            if (f.containsKey(c)) {
                f.put(c, f.get(c) + 1);
            }
            else {
                f.put(c, 1);
            }
        }
        return f;
    }

    private Node buildTree(Map<Character, Integer> freqTable) {
        Set<Character> chars = freqTable.keySet();
        Node n;
        for(Character c: chars) {
            queue.enqueue(new Leaf(c, freqTable.get(c)));
        }

        Node n1, n2;
        while (queue.size()>1) {
            n1 = queue.dequeue();
            n2 = queue.dequeue();
            queue.enqueue(new Branch(n1.getFreq()+n2.getFreq(), n1, n2));
        }
        Node t = queue.dequeue();
        return t;
    }

    private void buildCode(Node tree) {
        tree.traverse(code, new ArrayList<Boolean>());
    }

    public Node getTree() {
        return tree;
    }

    public Map<Character, List<Boolean>> getCode() {
        return code;
    }
}
