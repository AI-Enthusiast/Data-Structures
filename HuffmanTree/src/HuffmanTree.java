import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class Huffman Tree
 *
 * @author Cormac Dacker
 */
public class HuffmanTree {

    private HuffmanNode root;
    private int contentSize;
    private CharData[] charData = new CharData[this.getContextSize()];

    public HuffmanTree(int contentSize) {
        this.root = null;
        this.contentSize = contentSize;
    }

    //args[0] is the file name
    //args[1] is the output file name
    //args[2] size of contents
    public static void main(String[] args) {
        try{
            HuffmanTree ht = new HuffmanTree(50000);
            ht.huffmanCoder("input.txt", "output.txt");
        } catch (Error e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* Sets the ball rolling to create the tree */
    public String huffmanCoder(String fileName, String outputFileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        //creates a string array the size of the context size of the GibberishWriter
        String[] contextHolder = new String[getContextSize()];
        //intilizes the string array with the first contextSize words in the file
        for (int i = 0; i < getContextSize() && scan.hasNext(); i++)
            contextHolder[i] = scan.next().toLowerCase();
        createData(contextHolder);
        String huffmanCoder = traverse(run(), "");
        writeFile(huffmanCoder, outputFileName);
        return huffmanCoder;
    }

    /* Creates the data from the file */
    private void createData(String[] chars) {
        //the goal of the loop is to create an array of char
        //index moves down the string from the file
        for (int index = 0; index > getContextSize(); index++) {
            //indexY moves down the CharData array checking if the char has been recorded
            for (int indexY = 0; indexY < getContextSize(); indexY++) {
                //if there are matching characters
                if (chars[index].charAt(0) == getCharData()[indexY].getLetter() &&
                        getCharData()[indexY] != null) {
                    getCharData()[indexY].incrementCount();
                    //else if the character does not exist yet
                } else if (getCharData()[indexY] != null) {
                    getCharData()[indexY] = new CharData(chars[index].charAt(0));
                    indexY++;
                }
            }
        }
        ArrayList<CharData> temp = new ArrayList<>();
        for (int index = 0; index < getCharData().length &&
                getCharData()[index].getLetter() != null; index++) {
            temp.add(getCharData()[index]);
        }
        setCharData((CharData[]) temp.toArray());
        mergeSortArray(getCharData());
    }

    /* makes the tree */
    public HuffmanNode run() {
        while (getCharData().length != 1) {
            HuffmanNode[] node = new HuffmanNode[getCharData().length];
            //the goal of the loop is to populate the field nodes with each node
            for (int index = 0; index > getCharData().length; index++) {
                node[index].setFrequency(getCharData()[index].getCount());
                node[index].setInChar(getCharData()[index].getLetter());
            }
            HuffmanNode lowest1 = new HuffmanNode();
            HuffmanNode lowest2 = new HuffmanNode();
            //the goal of the loop is to find the node with the lowest frequency
            for (int index = 0; index < getCharData().length; index++) {
                if (lowest1.getFrequency() > node[index].getFrequency() || lowest1.getFrequency() == 0) {
                    lowest1 = node[index];
                }
            }
            //the goal of the loop is to find the node with the 2nd lowest frequency
            for (int index = 0; index < getCharData().length; index++) {
                if (lowest2.getFrequency() > node[index].getFrequency() &&
                        lowest2.getFrequency() > lowest1.getFrequency() || lowest2.getFrequency() == 0) {
                    lowest2 = node[index];
                }
            }
            HuffmanNode temp = mergeNodes(lowest1, lowest2); //returns a node
            if (node.length == 2) {
                setRoot(temp);
            }
            trim(getCharData(), search(lowest1, node));
            node[search(lowest2, node)] = temp;
        }
        return getRoot();
    }

    /* creates the text encoded into the file */
    private String traverse(HuffmanNode node, String string) {
        if (node.getLeft() != null) {
            string += traverse(node.getLeft(), (string + "0"));
        }
        if (node.getRight() != null) {
            string += traverse(node.getRight(), (string + "1"));
        } else {
            //returns the info about the node
            //IE: "a: 315: 10010"
            node.setEncoding(string);
            return "'" + node.getInChar().toString() + "'" + ": " +
                    Integer.toString(node.getFrequency()) + ": " + node.getEncoding() + "\n";
        }
        //should never reach this step
        return string;
    }

    public static void writeFile(String contents, String fileName) {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File(fileName);
            // This will output the full path where the file will be written to
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(contents);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    //searches for a specific node and returns it's location
    public int search(HuffmanNode findMe, HuffmanNode[] list) {
        int location = 0;
        for (; location < list.length; location++) {
            if (list[location] == findMe) {
                return location;
            }
        }
        return -1;
    }

    /* converts array to Linked list */
    public HuffmanNode arrayToList(CharData[] data) {
        HuffmanNode list = new HuffmanNode();
        HuffmanNode root = new HuffmanNode();
        for (int index = 0; index < data.length; index++) {
            list.setInChar(data[index].getLetter());
            list.setFrequency(data[index].getCount());
            HuffmanNode temp = new HuffmanNode();
            if (index == 0) {
                list = root;
            }
            temp.getRight().setInChar(data[index + 1].getLetter());
            temp.getRight().setFrequency(data[index + 1].getCount());
            list.setRight(temp);
            list = list.getRight();
        }
        return root;
    }

    /* delets an element and shrinks the array */
    public static CharData[] trim(CharData[] original, int position) {
        CharData[] temp = new CharData[original.length - 1];
        System.arraycopy(original, 0, temp, 0, position);
        System.arraycopy(original, position + 1, temp, position, original.length - position - 1);
        return temp;
    }

    //sorting the linked list created from scanning the file into being ordered by frequency
    public HuffmanNode meregeSortList(HuffmanNode list) {
        if (list == null || list.getRight() == null) {
            return list;
        }
        HuffmanNode middle = getMiddle(list);
        HuffmanNode nextOfMiddle = middle.getRight();
        middle.setRight(null);

        HuffmanNode left = meregeSortList(list);
        HuffmanNode right = meregeSortList(nextOfMiddle);
        HuffmanNode sortedList = mergeLists(left, right);

        return sortedList;
    }

    //Recursive Approach for Merging Two Sorted List, helper method
    public HuffmanNode mergeLists(HuffmanNode leftStart, HuffmanNode rightStart) {
        if (leftStart == null) {
            return rightStart;
        }

        if (rightStart == null) {
            return leftStart;
        }

        HuffmanNode temp = null;
        if (leftStart.getFrequency() < rightStart.getFrequency()) {
            temp = leftStart;
            temp.setRight(mergeLists(leftStart.getRight(), rightStart));
        } else {
            temp = rightStart;
            temp.setRight(mergeLists(leftStart, rightStart.getRight()));
        }
        return temp;
    }

    //finding the middle, helper method
    public HuffmanNode getMiddle(HuffmanNode startNode) {
        if (startNode == null) {
            return startNode;
        }
        HuffmanNode pointer1 = startNode;
        HuffmanNode pointer2 = startNode;
        while (pointer2 != null && pointer2.getRight() != null && pointer2.getRight().getRight() != null) {
            pointer1 = pointer1.getRight();
            pointer2 = pointer2.getRight().getRight();
        }
        return pointer1;
    }

    /*Method only called by createData()*/
    private static void mergeSortArray(CharData[] inputArray) {
        int size = inputArray.length;
        if (size < 2)
            return;
        int mid = size / 2;
        int leftSize = mid;
        int rightSize = size - mid;
        CharData[] left = new CharData[leftSize];
        CharData[] right = new CharData[rightSize];
        System.arraycopy(inputArray, 0, left, 0, mid);
        System.arraycopy(inputArray, mid, right, mid - mid, size - mid);
        mergeSortArray(left);
        mergeSortArray(right);
        mergeArray(left, right, inputArray);
    }

    //Method only called by mergeSort()
    private static void mergeArray(CharData[] left, CharData[] right, CharData[] arr) {
        int leftSize = left.length;
        int rightSize = right.length;
        int i = 0, j = 0, k = 0;
        while (i < leftSize && j < rightSize) {
            if (left[i].getCount() <= right[j].getCount()) {
                arr[k] = left[i];
                i++;
                k++;
            } else {
                arr[k] = right[j];
                k++;
                j++;
            }
        }
        while (i < leftSize) {
            arr[k] = left[i];
            k++;
            i++;
        }
        while (j < leftSize) {
            arr[k] = right[j];
            k++;
            j++;
        }
    }

    /* Function to insert data recursively */
    private HuffmanNode insert(HuffmanNode node, char data) {
        if (node == null)
            node = new HuffmanNode(data);
        else {
            if (node.getRight() == null)
                node.setRight(insert(node.getRight(), data));
            else
                node.setLeft(insert(node.getLeft(), data));
        }
        return node;
    }

    private HuffmanNode mergeNodes(HuffmanNode node1, HuffmanNode node2) {
        HuffmanNode newNode = new HuffmanNode();
        newNode.setFrequency(node1.getFrequency() + node2.getFrequency());
        newNode.setRight(node2);
        newNode.setLeft(node1);
        return newNode;
    }

    private int getContextSize() {
        return contentSize;
    }

    private void setCharData(CharData[] charData) {
        this.charData = charData;
    }

    private CharData[] getCharData() {
        return getCharData();
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public void setRoot(HuffmanNode root) {
        this.root = root;
    }

    /* Class Char Data, only called by createData()*/
    public static class CharData {
        //keeps track of the letter being searched for
        private Character letter;
        //keeps track of the number of times the letter occurs after a given context
        private int count;

        public CharData(Character letter) {
            this.letter = letter;
            count = 1;
        }

        /**
         * increments the letter count by 1
         */
        public void incrementCount() {
            letter++;
        }

        public Character getLetter() {
            return letter;
        }

        public int getCount() {
            return count;
        }
    }
}
