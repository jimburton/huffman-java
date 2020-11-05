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
        for(; i< getQueue().size(); i++) {
            Node m = getQueue().get(i);
            if (m.getFreq()>=n.getFreq()) break;
        }
        getQueue().add(i,n);
    }

    public Node dequeue() {
        if (getQueue().size()==0) return null;
        return getQueue().remove(0);
    }

    public int size() {
        return getQueue().size();
    }

    public List<Node> getQueue() {
        return queue;
    }
}
