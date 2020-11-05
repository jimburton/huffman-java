package ci646.huffman.tree;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

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

    public abstract void traverse(Map<Character, List<Boolean>> map, List<Boolean> list);

    /*
    Helper methods for printing trees from https://www.baeldung.com/java-print-binary-tree-diagram
     */

    public String toString() {
        return traversePreOrder(this);
    }

    public static String traversePreOrder(Node root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.getFreq());

        if (root instanceof Branch) {
            String pointerRight = "└──";
            String pointerLeft = (((Branch) root).getRight() != null) ? "├──" : "└──";

            traverseNodes(sb, "", pointerLeft, ((Branch) root).getLeft(), ((Branch) root).getRight() != null);
            traverseNodes(sb, "", pointerRight, ((Branch) root).getRight(), false);
        }
        sb.append("\n");
        return sb.toString();
    }

    public static void traverseNodes(StringBuilder sb, String padding, String pointer, Node node,
                                     boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getFreq());
            if (node instanceof Leaf) {
                sb.append(":"+((Leaf) node).getLabel());
            }

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            boolean hasRight = !(node instanceof Leaf) && ((Branch) node).getRight() != null;
            String pointerLeft = hasRight ? "├──" : "└──";

            if (!(node instanceof Leaf)) {
                traverseNodes(sb, paddingForBoth, pointerLeft, ((Branch) node).getLeft(), ((Branch) node).getRight() != null);
                traverseNodes(sb, paddingForBoth, pointerRight, ((Branch) node).getRight(), false);
            }
        }
    }

    public static void printTree(PrintStream os, Node tree) {
        os.print(traversePreOrder(tree));
    }

    /*
    Two BST trees are equal if they have the same contents in the same structure.
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Node)) {
            return false;
        }
        Node that = (Node) o;
        if (this.getFreq() != that.getFreq()) {
            return false;
        }
        if (this instanceof Leaf && that instanceof Leaf && ((Leaf) this).getLabel() != ((Leaf) that).getLabel())
            return false;
        if (this instanceof Branch && that instanceof Branch) {
            if (((Branch) this).getLeft() != null && !((Branch) this).getLeft().equals(((Branch) that).getLeft())) {
                return false;
            }
            if (((Branch) this).getRight() != null && !((Branch) this).getRight().equals(((Branch) that).getRight())) {
                return false;
            }
        }
        return true;
    }
}
