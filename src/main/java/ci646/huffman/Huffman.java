package ci646.huffman;
/**
 * The class implementing the Huffman coding algorithm.
 */

import ci646.huffman.tree.Branch;
import ci646.huffman.tree.Leaf;
import ci646.huffman.tree.Node;

import java.util.*;

public class Huffman {

    /**
     * Build the frequency table containing the unique characters from the String `input' and the number of times
     * that character occurs.
     *
     * @param input
     * @return
     */
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

    /**
     * Given a frequency table, construct a Huffman tree.
     *
     * First, create an empty priority queue.
     *
     * Then make every entry in the frequency table into a leaf node and add it to the queue.
     *
     * Then, take the first two nodes from the queue and combine them in a branch node that is
     * labelled by the combined frequency of the nodes and put it back in the queue.
     *
     * Do this repeatedly until there is a single node in the queue, which is the Huffman tree.
     *
     * @param freqTable
     * @return
     */
    public static Node treeFromFreqTable(Map<Character, Integer> freqTable) {
        if (freqTable == null) return null;
        Set<Character> chars = freqTable.keySet();
        PQueue queue = new PQueue();
        // add a leaf node to the queue for every character
        for(Character c: chars) {
            queue.enqueue(new Leaf(c, freqTable.get(c)));
        }
        // keep removing the first two nodes from the queue, combining them and
        // putting them back in the queue until there is only node left.
        Node n1, n2;
        while (queue.size()>1) {
            n1 = queue.dequeue();
            n2 = queue.dequeue();
            queue.enqueue(new Branch(n1.getFreq()+n2.getFreq(), n1, n2));
        }
        // the remaining node is the Huffman tree
        return queue.dequeue();
    }

    /**
     * Reconstruct a Huffman tree from the map of characters and their codes. Only the structure of this tree
     * is required and frequency labels of all nodes can be set to zero.
     *
     * Your tree will start as a single Branch node with null children.
     *
     * Then for each character key in the code, c, take the list of booleans, bs, corresponding to c. Make
     * a local variable referring to the root of the tree. For every boolean, b, in bs, if b is false you want to "go
     * left" in the tree, otherwise "go right".
     *
     * Presume b is false, so you want to go left. So long as you are not at the end of the code so you should set the
     * current node to be the left-hand child of the node you are currently on. If that child does not
     * yet exist (i.e. it is null) you need to add a new branch node there first. Then carry on with the next entry in
     * bs. Reverse the logic of this if b is true.
     *
     * When you have reached the end of this code (i.e. b is the final element in bs), add a leaf node
     * labelled by c as the left-hand child of the current node (right-hand if b is true). Then take the next char from
     * the code and repeat the process, starting again at the root of the tree.
     *
     * @param code
     * @return
     */
    public static Node treeFromCode(Map<Character, List<Boolean>> code) {
        Node root = new Branch(0, null, null);
        Node n;
        for(char c: code.keySet()) {
            n = root;
            List<Boolean> bs = code.get(c);
            for (int i=0;i<bs.size();i++) {
                boolean b = bs.get(i);
                // are we on the last element of this code?
                boolean isLeaf = i == bs.size()-1;
                if (b) { // if b is true go right
                    if (isLeaf) {
                        // add a leaf to the tree
                        ((Branch) n).setRight(new Leaf(c, 0));
                    } else if (((Branch) n).getRight() == null) {
                        // add a branch to the tree
                        ((Branch) n).setRight(new Branch(0, null, null));
                    }
                    // go right
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

    /**
     * Construct the map of characters and codes from a tree. Just pass the empty code map to the traverse
     * method of the tree along with an empty list, then return the populated code map.
     *
     * @param tree
     * @param code
     * @return
     */
    public static Map<Character, List<Boolean>> buildCode(Node tree, Map<Character, List<Boolean>> code) {
        tree.traverse(code, new ArrayList<>());
        return code;
    }

    /**
     * Create the huffman coding for an input string by calling the various methods written above. I.e. create the
     * frequency table, use that to create the Huffman tree then extract the code map of characters and their codes
     * from the tree. Then to encode the input data, loop through the input looking each character in the map and add
     * the code for that character to a list representing the data.
     *
     * @param input
     * @return
     */
    public static HuffmanCoding encode(String input) {
        Map<Character, Integer> ft = freqTable(input);
        Node tree = treeFromFreqTable(ft);
        Map<Character, List<Boolean>> code = buildCode(tree, new HashMap<>());
        // build the data
        List<Boolean> data = new ArrayList<>();
        for(int i=0;i<input.length();i++) {
            data.addAll(code.get(input.charAt(i)));
        }
        return new HuffmanCoding(code, data);
    }

    /**
     * Decode some data using a map of characters and their codes. To do this you need to reconstruct the tree from the
     * map. Take one boolean at a time from the data and use it to traverse the tree
     * by going left for false, right for true. Every time you reach a leaf you have decoded a single character (the
     * label of the leaf). Add it to the result and return to the root of the tree.
     *
     * @param map
     * @param data
     * @return
     */
    public static String decode(Map<Character, List<Boolean>> map, List<Boolean> data) {
        StringBuilder sb = new StringBuilder();
        Node tree = treeFromCode(map);
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
