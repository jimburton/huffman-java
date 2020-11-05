package ci646.huffman;

import ci646.huffman.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class PQueue {

    private List<Node> queue;

    public PQueue() {
        queue = new ArrayList<>();
    }

    public void enqueue(Node n) {
        int i=0;
        for(; i< queue.size(); i++) {
            Node m = queue.get(i);
            if (m.getFreq()>=n.getFreq()) break;
        }
        queue.add(i,n);
    }

    public Node dequeue() {
        if (queue.size()==0) return null;
        return queue.remove(0);
    }

    public int size() {
        return queue.size();
    }
}
