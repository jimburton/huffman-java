package ci646.huffman.tree;
/**
 * A branch node in a Huffman tree.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Branch extends Node {

    private Node left;
    private Node right;

    public Branch(int freq, Node left, Node right) {
        super(freq);
        this.left = left;
        this.right = right;
    }

    /**
     * The traverse method is used to construct the map of characters (stored in leaves of the tree)
     * and binary codes (representing the paths from the root to the leaves). In the branch class this method
     * should be called recursively on the left and right children of the branch, adding `false` to the list
     * of booleans passed to the left child and `true` to the list of booleans passed to the right. In the Leaf class
     * the method should store the pair of the character (the label of the leaf) and the list of booleans (the path
     * to that leaf) in the map.
     * @param map
     * @param list
     */
    public void traverse(Map<Character, List<Boolean>> map, List<Boolean> list) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
