package ci646.huffman;
/**
 * A priority queue of Node objects. Each node has a frequency -- the queue orders objects
 * in descending order of frequency, i.e. lowest first.
 */

import ci646.huffman.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class PQueue {

    private List<Node> queue;

    public PQueue() {
        queue = new ArrayList<>();
    }

    /**
     * Add a node to the queue. The new node should be inserted at the point where the frequency of next node is
     * greater than or equal to that of the new one.
     * @param n
     */
    public void enqueue(Node n) {
        int i=0;
        for(; i< queue.size(); i++) {
            Node m = queue.get(i);
            if (m.getFreq()>=n.getFreq()) break;
        }
        queue.add(i,n);
    }

    /**
     * Remove a node from the queue.
     * @return
     */
    public Node dequeue() {
        if (queue.size()==0) return null;
        return queue.remove(0);
    }

    /**
     * Return the size of the queue.
     * @return
     */
    public int size() {
        return queue.size();
    }
}
