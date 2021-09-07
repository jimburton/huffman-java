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
    @Override
    public Map<Character, List<Boolean>> traverse(List<Boolean> list) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
