package ci646.huffman.tree;

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

    public void traverse(Map<Character, List<Boolean>> map, List<Boolean> list) {
        ArrayList<Boolean> leftList = new ArrayList(list);
        leftList.add(false);
        List<Boolean> rightList = new ArrayList(list);
        rightList.add(true);
        getLeft().traverse(map, leftList);
        getRight().traverse(map, rightList);
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
