package huffman.util;

import huffman.tree.Branch;
import huffman.tree.Leaf;
import huffman.tree.Node;

import java.io.PrintStream;

public class TreePrinter {
    /*
    Helper methods for printing trees from https://www.baeldung.com/java-print-binary-tree-diagram
     */

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

}
