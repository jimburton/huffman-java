package ci646.huffman;

import ci646.huffman.tree.Branch;
import ci646.huffman.tree.Leaf;
import ci646.huffman.tree.Node;

import java.util.*;

public class Huffman {

    public static Map<Character, Integer> freqTable (String input) {
        if (input == null || input.isEmpty()) return null;
        Map<Character, Integer> ft = new HashMap<>();
        char[] strArray = input.toCharArray();
        for (char c : strArray) {
            if (ft.containsKey(c)) {
                ft.put(c, ft.get(c) + 1);
            }
            else {
                ft.put(c, 1);
            }
        }
        return ft;
    }

    public static Node treeFromFreqTable(Map<Character, Integer> freqTable) {
        if (freqTable == null) return null;
        Set<Character> chars = freqTable.keySet();
        PQueue queue = new PQueue();

        for(Character c: chars) {
            queue.enqueue(new Leaf(c, freqTable.get(c)));
        }

        Node n1, n2;
        while (queue.size()>1) {
            n1 = queue.dequeue();
            n2 = queue.dequeue();
            queue.enqueue(new Branch(n1.getFreq()+n2.getFreq(), n1, n2));
        }
        return queue.dequeue();
    }

    public static Node treeFromCode(Map<Character, List<Boolean>> code) {
        Node root = new Branch(0, null, null);
        Node n;
        for(char c: code.keySet()) {
            n = root;
            List<Boolean> bs = code.get(c);
            for (int i=0;i<bs.size();i++) {
                boolean b = bs.get(i);
                boolean isLeaf = i == bs.size()-1;
                if (b) {
                    if (isLeaf) {
                        ((Branch) n).setRight(new Leaf(c, 0));
                    } else if (((Branch) n).getRight() == null) {
                        ((Branch) n).setRight(new Branch(0, null, null));
                    }
                    n = ((Branch) n).getRight();
                } else {
                    if (isLeaf) {
                        ((Branch) n).setLeft(new Leaf(c, 0));
                    } else if (((Branch) n).getLeft() == null) {
                        ((Branch) n).setLeft(new Branch(0, null, null));
                    }
                    n = ((Branch) n).getLeft();
                }
            }
        }
        return root;
    }

    public static Map<Character, List<Boolean>> buildCode(Node tree, Map<Character, List<Boolean>> code) {
        tree.traverse(code, new ArrayList<>());
        return code;
    }

    public static HuffmanCoding encode(String input) {
        Map<Character, Integer> ft = freqTable(input);
        Node tree = treeFromFreqTable(ft);
        Map<Character, List<Boolean>> code = buildCode(tree, new HashMap<>());
        List<Boolean> data = new ArrayList<>();
        for(int i=0;i<input.length();i++) {
            data.addAll(code.get(input.charAt(i)));
        }
        return new HuffmanCoding(code, data);
    }

    public static String decode(Map<Character, List<Boolean>> table, List<Boolean> data) {
        StringBuilder sb = new StringBuilder();
        Node tree = treeFromCode(table);
        Node n = tree;
        for (Boolean b: data) {
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
}
