package huffman;

import huffman.tree.Branch;
import huffman.tree.Leaf;
import huffman.tree.Node;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.*;

public class TestHuffman {

    String input = "Oh I do like to be beside the seaside, I do like to be beside the sea";

    @Test
    public void testPQueue() {
        PQueue pq = new PQueue();
        assertNull(pq.dequeue());
        pq.enqueue(new Leaf('a', 42));
        pq.enqueue(new Leaf('b', 1));
        pq.enqueue(new Leaf('c', 101));
        pq.enqueue(new Leaf('d', -101));
        assertEquals(pq.size(), 4);
        assertEquals(pq.dequeue().getFreq(), -101);
        assertEquals(pq.dequeue().getFreq(), 1);
        assertEquals(pq.dequeue().getFreq(), 42);
        assertEquals(pq.dequeue().getFreq(), 101);
    }

    @Test
    public void testBuildFreqTable() {
        assertNull(Huffman.freqTable(null));
        assertNull(Huffman.freqTable(""));
        Map<Character, Integer> hc = Huffman.freqTable(input);
        // get a list of the unique chars in the input string
        List<Character> uniques = new ArrayList<>();
        for(int i=0;i<input.length();i++) {
            if (!uniques.contains(input.charAt(i))) uniques.add(input.charAt(i));
        }
        assertEquals(hc.keySet().size(), uniques.size());
        for(int i=0;i<input.length();i++) {
            assertTrue(hc.containsKey(input.charAt(i)));
        }
        assertEquals((long) hc.get('I'), 2);
        assertEquals((long) hc.get('d'), 5);
    }

    @Test
    public void testTreeFromFreqTable() {
        assertNull(Huffman.treeFromFreqTable(Huffman.freqTable("")));
        // get a list of the unique chars in the input string
        Node t = Huffman.treeFromFreqTable(Huffman.freqTable("a"));
        assertEquals(t.getFreq(), 1);
        assertTrue(t instanceof Leaf);

        t = Huffman.treeFromFreqTable(Huffman.freqTable("aaaabaac"));
        assertEquals(t.getFreq(), 8);
        // the frequency of the right child should be greater than or equal to the
        // frequency of the left child.
        assertEquals(((Branch)t).getLeft().getFreq(), 2); // 'b' and 'c'
        assertEquals(((Branch)t).getRight().getFreq(), 6); // 'a'
    }

    @Test
    public void testBuildCode() {
        Map<Character, Integer> freqTable = Huffman.freqTable(input);
        Node tree = Huffman.treeFromFreqTable(freqTable);
        Map<Character, List<Boolean>> code = Huffman.buildCode(tree);
        for (char c: freqTable.keySet()) {
            assertTrue(code.containsKey(c));
        }
        assertEquals(freqTable.keySet().size(), code.keySet().size());
    }

    @Test
    public void testCodec() {
        HuffmanCoding hc = Huffman.encode(input);
        String decode = Huffman.decode(hc.getCode(), hc.getData());
        assertEquals(input, decode);
    }

    @Test
    public void testCompressBook() {
        try {
            String input = Files.readString(Path.of("etc/pg1459.txt"), StandardCharsets.ISO_8859_1);
            HuffmanCoding hc = Huffman.encode(input);
            String decode = Huffman.decode(hc.getCode(), hc.getData());
            assertEquals(input, decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void compressionBenchmark() {
        try {
            String input = Files.readString(Path.of("etc/pg1459.txt"), StandardCharsets.ISO_8859_1);
            HuffmanCoding hc = Huffman.encode(input);
            hc.save("etc/pg1459.hc");

            // Java chars take up two bytes each
            int inputSize = input.length()*2;
            int codeSize = 0;
            // each entry in the Huffman code takes up one byte
            // for the char plus one byte for the length of the code plus
            // however many bits are needed to
            // accommodate the code
            for (char c: hc.getCode().keySet()) {
                codeSize += 2;
                codeSize += hc.getCode().get(c).size();
            }
            codeSize += hc.getData().size() / 8;
            float comp = 100 - (((float) codeSize / inputSize) * 100);
            System.out.printf("[Compressed input from %d bytes to %d bytes, %4.2f%% compression]\n",
                    inputSize, codeSize, comp);
            assertTrue(comp > 65);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveAndRead() {
        try {
            String inputPath = "etc/pg1459.txt";
            String input = Files.readString(Path.of(inputPath), StandardCharsets.ISO_8859_1);
            HuffmanCoding hc = Huffman.encode(input);
            String outputPath = "etc/pg1459.hc";
            hc.save(outputPath);

            Optional<HuffmanCoding> hcOpt = HuffmanCoding.read(outputPath);
            assertTrue(hcOpt.isPresent());
            HuffmanCoding hc2 = hcOpt.get();
            assertEquals(input, Huffman.decode(hc2.getCode(), hc2.getData()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
