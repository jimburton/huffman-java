package ci646.huffman.tree;

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

    @Override
    public void traverse(Map<Character, List<Boolean>> map, List<Boolean> list) {
        map.put(label, list);
    }
}
