package ci646.huffman;

import java.util.List;
import java.util.Map;

public class HuffmanCoding {

    private final Map<Character, List<Boolean>> code;
    private final List<Boolean> data;

    public HuffmanCoding(Map<Character, List<Boolean>> code, List<Boolean> data) {
        this.code = code;
        this.data = data;
    }

    public Map<Character, List<Boolean>> getCode() {
        return code;
    }

    public List<Boolean> getData() {
        return data;
    }
}
