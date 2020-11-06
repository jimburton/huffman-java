package ci646.huffman.tree;
/**
 * A leaf in a Huffman tree. The label is a char and the path to this leaf is the code for that char.
 */

import java.util.List;
import java.util.Map;

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
     * and binary codes (representing the paths from the root to the leaves). In the branch class this method
     * should be called recursively on the left and right children of the branch, adding `false` to the list
     * of booleans passed to the left child and `true` to the list of booleans passed to the right. In the Leaf class
     * the method should store the pair of the character (the label of the leaf) and the list of booleans (the path
     * to that leaf) in the map.
     * @param map
     * @param list
     */
    @Override
    public void traverse(Map<Character, List<Boolean>> map, List<Boolean> list) {
        map.put(label, list);
    }
}
