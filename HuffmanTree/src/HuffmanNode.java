/* Class HuffmanNode */
public class HuffmanNode {

    private Character inChar;
    private int frequency = 0;
    private HuffmanNode left, right;
    private String encoding;


    /* Creates a Huffman Node */
    public HuffmanNode() {
        this.left = null;
        this.right = null;
        this.inChar = ' ';
    }

    /* Creates a Huffman Node */
    public HuffmanNode(Character n) {
        this.left = null;
        this.right = null;
        this.inChar = n;
    }

    /* Function to count number of nodes recursively */
    private int countNodes(HuffmanNode node) {
        int count = 1;
        count += countNodes(node.getLeft());
        count += countNodes(node.getRight());
        return count;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public Character getInChar() {
        return inChar;
    }

    public void setInChar(Character inChar) {
        this.inChar = inChar;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}

