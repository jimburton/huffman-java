package huffman.tree;

import java.util.List;
import java.util.Map;
/**
 * A branch node in a Huffman tree.
 */
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
    public Map<Character, List<Boolean>> traverse(List<Boolean> list) {
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
