package huffman.tree;

import java.util.List;
import java.util.Map;
/**
 * A leaf in a Huffman tree. A leaf has two labels: a char and an int.
 */
public class Leaf extends Node {
    private char label;

    public Leaf(char label, int freq) {
        super(freq);
        this.label = label;
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(char label) {
        this.label = label;
    }

    /**
     * The traverse method is used to construct the map of characters (stored in leaves of the tree)
     * and binary codes (representing the paths from the root to the leaves). In the Branch class this method
     * should be called recursively on the left and right children of the branch, adding `false` to the list
     * of booleans passed to the recursive call on the left child, and `true` to the list of booleans passed to
     * the recursive call on the right child. In the Leaf class
     * the method should store both the character (the label of the leaf) and the list of booleans (the path
     * to that leaf) in the map which is the first parameter to `traverse`.
     * @param map   The map of the tree, which is shared among recursive invocations of the method.
     * @param list  The list representing paths through the tree, modified copies of which are passed to
     *              recursive invocations of the method.
     */
    @Override
    public void traverse(Map<Character, List<Boolean>> map, List<Boolean> list) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
