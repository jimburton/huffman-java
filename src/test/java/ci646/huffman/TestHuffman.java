package ci646.huffman;

import ci646.huffman.tree.Branch;
import ci646.huffman.tree.Leaf;
import ci646.huffman.tree.Node;
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
    public void testBuildFreqTable() {
        assertNull(Huffman.buildFreqTable(null));
        assertNull(Huffman.buildFreqTable(""));
        Map<Character, Integer> hc = Huffman.buildFreqTable(input);
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
    }

    @Test
    public void testTreeFromFreqTable() {
        assertNull(Huffman.treeFromFreqTable(Huffman.buildFreqTable("")));
        // get a list of the unique chars in the input string
        Node t = Huffman.treeFromFreqTable(Huffman.buildFreqTable("a"));
        assertEquals(t.getFreq(), 1);
        assertTrue(t instanceof Leaf);

        t = Huffman.treeFromFreqTable(Huffman.buildFreqTable("aaaabaac"));
        assertEquals(t.getFreq(), 8);
        assertEquals(((Branch)t).getLeft().getFreq(), 2); // 'b' and 'c'
        assertEquals(((Branch)t).getRight().getFreq(), 6); // 'a'
    }

    @Test
    public void testBuildCode() {
        Map<Character, Integer> freqTable = Huffman.buildFreqTable(input);
        Node tree = Huffman.treeFromFreqTable(freqTable);
        Map<Character, List<Boolean>> code = Huffman.buildCode(tree, new HashMap<>());
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
            int codeSize = hc.getData().size() / 8;
            float comp = 100 - (((float) codeSize / inputSize) * 100);
            System.out.printf("[Compressed input from %d bytes to %d bytes, %4.2f%% compression]\n",
                    inputSize, codeSize, comp);
            assertTrue(comp < 75);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveAndRead() {
        try {
            String input = Files.readString(Path.of("etc/pg1459.txt"), StandardCharsets.ISO_8859_1);
            HuffmanCoding hc = Huffman.encode(input);
            String path = "etc/pg1459.hc";
            hc.save(path);

            Optional<HuffmanCoding> hcOpt = HuffmanCoding.read(path);
            assertTrue(hcOpt.isPresent());
            HuffmanCoding hc2 = hcOpt.get();
            assertEquals(input, Huffman.decode(hc2.getCode(), hc2.getData()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
