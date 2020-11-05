package ci646.huffman;

import ci646.huffman.tree.Node;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestHuffman {

    @Before
    public void setup() {

    }

    @Test
    public void testCodec() {
        Huffman h = new Huffman();
        String input = "Oh I do like to be beside the seaside, I do like to be beside the sea";
        List<Boolean> code = h.encode(input);
        String decode = h.decode(code);
        assertEquals(input, decode);

        // Java chars take up two bytes each
        int inputSize = input.length()*2;
        int codeSize = code.size() / 8;
        float comp = 100 - (((float) codeSize / inputSize) * 100);
        System.out.printf("Compressed input from %d bytes to %d bytes, %4.2f%% compression\n",
                inputSize, codeSize, comp);

        String example = "Convert Java String";
        byte[] bytes = example.getBytes();
        System.out.println(bytes);

        String s = new String(bytes, StandardCharsets.US_ASCII);
        System.out.println(s);
    }

    @Test
    public void testCompressBook() {
        Huffman h = new Huffman();
        try {
            String input = Files.readString(Path.of("etc/pg1459.txt"), StandardCharsets.ISO_8859_1);
            List<Boolean> code = h.encode(input);
            String decode = h.decode(code);
            assertEquals(input, decode);

            // Java chars take up two bytes each
            int inputSize = input.length()*2;
            int codeSize = code.size() / 8;
            float comp = 100 - (((float) codeSize / inputSize) * 100);
            System.out.printf("Compressed input from %d bytes to %d bytes, %4.2f%% compression\n",
                    inputSize, codeSize, comp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
