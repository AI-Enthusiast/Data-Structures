/* IMPORTS */

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Hashcode class finds how many times all words are used in a document
 *
 * @author Cormac Dacker ? cxd289
 */
public class HashCode {

    // FIELDS
    private Entry[] table = new Entry[32];
    private int nextEmptySpace;
    private int tableSize = table.length;
    // CONSTRUCTOR

    /**
     * Creates a HashCode instance
     */
   public HashCode(String input, String output) {
        try {
            addFile(input, output);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    // GETTERS/SETTERS
    public Entry[] getTable() {
        return table;
    }

    public int getNextEmptySpace() {
        return nextEmptySpace;
    }

    // WORKING METHODS ?
    public String wordCount(String inputFileName, String outputFileName) {
  //      makeTable(inputFileName);
        File outputFile = new File(outputFileName);
        PrintWriter writer;
        String finalString = "";
        try {
            writer = new PrintWriter(outputFile);
            /* appending this full string of entries into an output file */
            writer.print(fileContents());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return finalString;
    }

    public void addFile(String inputFile, String outputFile) {
        
      Scanner scan = new Scanner(new File(inputFile));
        String inputContents = "";
        while (scan.hasNext()){
            inputContents = inputContents + scan.next();
        }
        StringTokenizer tokenizer = new StringTokenizer(inputContents, " \\\'`~!@#$%^&*()_-+={}[]:;?/>.<,\\\"���\"\n");
        while (tokenizer.hasMoreTokens()) {


            String word = tokenizer.nextToken().toLowerCase();


           /* the goal of the loop is to check each location for an exhisting entry*/
            for (int index = 0; index < getTable().length; index++) {
               /* if the word and key matches at the index; increment the count of the word */
                if (getTable()[index].getKey() == makeKey(word) && getTable()[index].getWord().equals(word)) {
                    getTable()[index].increment();
                }
               /* if the keys are equal but the words are not */
                else if (getTable()[index].getKey() == makeKey(word) && getTable()[index].getWord().equals(word)) {
                    Entry node = getTable()[index];
                    while (node.getNext() != null && node.getWord().equals(word)) {
                        node = node.getNext();
                    }
                    if (node.getWord().equals(word)) {
                        node.increment();
                    } else {
                        insert(new Entry(word), index, true);
                    }
                }
            }
            insert(new Entry(word), getNextEmptySpace(), false);
        }
    }

    public int makeKey(String word) {
        return word.hashCode() % tableSize;
    }

    public void insert(Entry word, int location, boolean subNodeLevel) {
       /* if there is no sublevel needed just add the node */
        if (!subNodeLevel) {
            table[location] = word;
        } else {
           /* the goal of the loop is to add the entry to the end of the LinkedList */
            while (subNodeLevel) {
                Entry node = table[location];
                if (node.getNext() != null) {
                    node = node.getNext();
                } else {
                    node.setNext(word);
                }
            }
        }
        nextEmptySpace++;
    }

    public void reHash() {
        int empty = 0;
        for (Entry aTable : table) {
            if (aTable == null) {
                empty++;
            }
        }
       /* if there are less than 10% of the table is open, expand the table */
        if (table.length / .25 < empty) {
            int oldSize = table.length;
            Entry[] oldTable = table;
            int size = 3 / 2 * oldSize;
            table = new Entry[size];
            for (int index = 0; index < oldSize; index++) {
                table[index] = oldTable[index];
            }
        }
    }

    public boolean areParentsProud(int numOfAchivements) {
        return false;
    }

    public void writeFile(String fileName) {
        BufferedWriter writer = null;
        try {
           /* create a temporary file */
            File logFile = new File(fileName);
           /* this will output the full path where the file will be written to */
            System.out.println(logFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(fileContents());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
               /* close the writer regardless of what happens */
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    public String fileContents() {
        String out = "";
        for (Entry aTable : getTable()) {
            out = out + aTable.toString();
        }
        return out;
    }

    // INNER CLASS

    /**
     * Inner class that represents an entry to the HashTable
     */
    public class Entry {

        // FIELDS
        private int key;
        private int count = 1;
        private String word;
        private Entry next;

        // CONSTRUCTOR
        public Entry(String word) {
            this.word = word;
            this.key = word.hashCode();
        }

        // GETTERS/SETTERS
        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }

        // WORKING METHODS

        /**
         * Increments the count by one
         */
        public void increment() {
            this.count++;
        }

        @Override
        public String toString() {
            String out = "(" + getWord() + " " + getCount() + ")";
           /* if this index is double hashed*/
            if (this.getNext() != null) {
                out = out + getNext().toString();
            }
            return out;
        }
    }
}

