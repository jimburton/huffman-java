package huffman;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * A class to contain the details of a Huffman coding, i.e. the mapping from
 * characters to binary sequences and the binary data itself. This class is
 * Serializable so objects can be written to file and read back in using the
 * `save' and `read' methods.
 */
public class HuffmanCoding implements Serializable {

    private final Map<Character, List<Boolean>> code; // The code.
    private final List<Boolean> data;                 // The data.

    public HuffmanCoding(Map<Character, List<Boolean>> code, List<Boolean> data) {
        this.code = code;
        this.data = data;
    }

    /**
     * Getter for the code.
     * @return  The code.
     */
    public Map<Character, List<Boolean>> getCode() {
        return code;
    }

    /**
     * Getter for the data.
     * @return  The data.
     */
    public List<Boolean> getData() {
        return data;
    }

    /**
     * Write this object ot the filesystem as a serialized Java object.
     * @param path  The path to write the object to.
     */
    public void save(String path) {
        try {
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read in a serialized object containing a Huffman coding.
     * @param path  The path to the file containing the serialized object.
     * @return      The deserialized object.
     */
    public static Optional<HuffmanCoding> read(String path) {
        try {
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fin);
            return Optional.of((HuffmanCoding) ois.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
