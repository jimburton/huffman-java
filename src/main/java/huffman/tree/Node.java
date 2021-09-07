package huffman.tree;

import huffman.util.TreePrinter;

import java.util.List;
import java.util.Map;
/**
 * The parent class of nodes in a Huffman tree.
 */
public abstract class Node {
    private int freq;

    public Node(int freq) {
        this.freq = freq;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    /**
     * The traverse method is used to construct the map of characters (stored in leaves of the tree)
     * and binary codes for each character. A binary code is a list of true and false values representing the path
     * from the root to a leaf, where `false` represents Left and `true` represents Right. In the Branch class this method
     * should be called recursively on the left and right children of the branch, adding `false` to the list
     * of booleans passed to the recursive call on the left child, and `true` to the list of booleans passed to
     * the recursive call on the right child. The return value should be the merged map containing the results of both
     * recursive calls. In the Leaf class the method should create a new map then store both the character (the label
     * of the leaf) and the list of booleans (the path to that leaf), and return the resulting map.
     *
     * @param list  The list representing paths through the tree, modified copies of which are passed to
     *              recursive invocations of the method.
     * @return the map of characters and paths
     */
    public abstract Map<Character, List<Boolean>> traverse(List<Boolean> list);

    public String toString() {
        return TreePrinter.traversePreOrder(this);
    }

}
