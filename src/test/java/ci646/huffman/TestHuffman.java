package ci646.huffman;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHuffman {

    @Before
    public void setup() {

    }

    @Test
    public void testCodec() {
        Huffman h = new Huffman();
        String input = "Oh I do like to be beside the seaside, I do like to be beside the sea";
        HuffmanCoding hc = h.encode(input);
        String decode = h.decode(hc.getCode(), hc.getData());
        assertEquals(input, decode);
    }

    @Test
    public void testCompressBook() {
        Huffman h = new Huffman();
        try {
            String input = Files.readString(Path.of("etc/pg1459.txt"), StandardCharsets.ISO_8859_1);
            HuffmanCoding hc = h.encode(input);
            String decode = h.decode(hc.getCode(), hc.getData());
            assertEquals(input, decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void compressionBenchmark() {
        Huffman h = new Huffman();
        try {
            String input = Files.readString(Path.of("etc/pg1459.txt"), StandardCharsets.ISO_8859_1);
            HuffmanCoding hc = h.encode(input);
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
        Huffman h = new Huffman();
        try {
            String input = Files.readString(Path.of("etc/pg1459.txt"), StandardCharsets.ISO_8859_1);
            HuffmanCoding hc = h.encode(input);
            String path = "etc/pg1459.hc";
            hc.save(path);

            Optional<HuffmanCoding> hcOpt = HuffmanCoding.read(path);
            assertTrue(hcOpt.isPresent());
            HuffmanCoding hc2 = hcOpt.get();
            assertEquals(input, h.decode(hc2.getCode(), hc2.getData()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
