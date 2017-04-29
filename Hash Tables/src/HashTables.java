/* IMPORTS */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 * Hashcode class finds how many times all words are used in a document
 *
 * @author Cormac Dacker ✌ cxd289
 */
public class HashTables {

    // FIELDS
    private Entry[] table = new Entry[64];
    private int nextEmptySpace = 0;
    private int tableSize = table.length;
    private boolean wordAdded;

    // CONSTRUCTOR
    /**
     * Creates a HashCode instance
     */
    public HashTables(String file) {
        wordCount(file);
    }

    // GETTERS/SETTERS
    public Entry[] getTable() {
        return table;
    }

    public int getNextEmptySpace() {
        return nextEmptySpace;
    }

    // WORKING METHODS ☠
    /**
     * Runs the class with an input
     *
     * @param args meaningless input
     */
    public static void main(String[] args) {
        new HashTables("toy.txt");
    }

    /**
     * Creates the table and sends it to a file writer
     *
     * @param inputFileName the name of the file. IE: "fileName.txt"
     */
    public void wordCount(String inputFileName) {
        addFile(inputFileName);
        writeFile();
    }

    /**
     * Ceates the table from the input file
     *
     * @param inputFileName the name of the file being read from
     */
    public void addFile(String inputFileName) {
        try {
            String inputContents = new String(Files.readAllBytes(Paths.get(inputFileName)));
            StringTokenizer tokenizer = new StringTokenizer(inputContents,
                    " \\\'`~!@#$%^&*()_-+={}[]:;?/>.<,\\\"“‘’\"\n");
            /* the goal of the loop is to read each word and adding it or counting it to the table */
            while (tokenizer.hasMoreTokens()) {
                reHash();
                this.wordAdded = false;
                String word = tokenizer.nextToken().toLowerCase();
                /* the goal of the loop is to check each location for an prexhisting entry*/
                for (int i = 0; i < tableSize - 1 && !wordAdded; i++) {
                 /* if the word and key matches at the i; increment the count of the word */
                    if (getTable()[i] != null && getTable()[i].getKey() == makeKey(word) &&
                            getTable()[i].getWord().equals(word)) {
                        getTable()[i].increment();
                        this.wordAdded = true;
                    }
                    /* if the keys are equal but the words are not */
                    else if (getTable()[i] != null &&
                            getTable()[i].getKey() == makeKey(word) &&
                            getTable()[i].getWord().equals(word)) {
                        Entry node = getTable()[i];
                        /* the goal of the loop is to iterate down until we find the sub
                         * node that is ours */
                        while (node.getNext() != null) {
                            if (node.getWord().equals(word)) {
                                node.increment();
                                this.wordAdded = true;
                            }
                            node = node.getNext();
                        }
                        /* if the words are equal */
                        if (node.getWord().equals(word)) {
                            node.increment();
                            this.wordAdded = true;
                        } else {
                            insert(new Entry(word), i, true);
                            this.wordAdded = true;
                        }
                    }
                }
                /* if it's a new word */
                if (!wordAdded) {
                    insert(new Entry(word), getNextEmptySpace(), false);
                    this.wordAdded = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes the key of the word
     *
     * @param word word we want the key of
     * @return the key of the word
     */
    public int makeKey(String word) {
        return word.hashCode() % 32;
    }

    /**
     * Inserts a word at a location in the table
     *
     * @param word    the word we with to enter
     * @param i       the location in the table to enter it
     * @param subNode whether it is being added as a subNode
     */
    public void insert(Entry word, int i, boolean subNode) {
        /* if there is no sublevel needed just add the node */
        if (!subNode) {
            table[i] = word;
        } else {
            Entry node = table[i];
           /* the goal of the loop is to add the entry to the end of the LinkedList */
            while (node.getNext() != null) {
                node = node.getNext();
            }
            node.setNext(word);
        }
        nextEmptySpace++;
    }

    /**
     * The goal of the loop is to expand the table when it is almost full
     */
    public void reHash() {
       /* if there are less than 16 of the table is open, expand the table */
        if (tableSize - getNextEmptySpace() < 8) {
            int oldSize = tableSize;
            Entry[] oldTable = table;
            tableSize = 2 * oldSize;
            table = new Entry[tableSize];
            for (int index = 0; index < oldSize; index++) {
                table[index] = oldTable[index];
            }
        }
    }

    /**
     * Maybe one day
     *
     * @param numOfAchievements it doesn't matter
     * @return big surprise
     */
    public boolean areParentsProud(int numOfAchievements) {
        return false;
    }

    /**
     * Writes the table into a file
     */
    public void writeFile() {
        BufferedWriter writer = null;
        try {
           /* create a temporary file */
            File logFile = new File("READ_ME.txt");
           /* this will output the full path where the file will be written to */
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

    /**
     * The contents of the file being written
     *
     * @return contents of the table
     */
    public String fileContents() {
        String out = "";
        for (int i = 0; i < tableSize; i++) {
            if (getTable()[i] != null) {
                out += getTable()[i].toString();
            }
        }
        return out;
    }

    // INNER CLASS
    /**
     * Inner class that represents an entry to the HashTable
     */
    public class Entry {

        // FIELDS
        private int key = 0;
        private int count = 1;
        private String word = "";
        private Entry next = null;

        // CONSTRUCTOR
        public Entry(String word) {
            setWord(word);
            setKey(word.hashCode() % 32);
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

        /**
         * Creates a string to represent the Entry
         *
         * @return
         */
        @Override
        public String toString() {
            /* if there is a word existing */
            if (!this.getWord().equals("")) {
                String out = "(" + getWord() + " " + getCount() + ")";
                /* if there are sub nodes */
                if (getNext() != null &&
                        this.getNext().getWord().equals(getNext().getWord())) {
                    out = out + getNext().toString();
                }
                System.out.println(out + "\n");
                return out + "\n";
            }
            return "";
        }
    }
}

