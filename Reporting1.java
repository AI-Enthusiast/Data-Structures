import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Reporting 1 testing class
 *
 * @author Cormac
 */
public class Reporting1 {

    // WORKING METHODS

    /**
     * Generates an array of a specified length and type (default type is random sorted array)
     *
     * @param sorted  create a sorted array
     * @param reverse create a reverse sorted array
     * @param size    the size of the array being created
     * @return the array once it is created
     */
    public static int[] generateArray(boolean sorted, boolean reverse, int size) {
        /* if it's sorted or reversed*/
        int[] arr = new int[size];
        if (sorted || reverse) {
            /* if it's reveresed */
            if (reverse) {
                for (int index = size - 1; index < 0; index--) {
                    arr[index] = index + 1;
                }
                /* else it's sorted (increasing order) */
            } else {
                for (int index = 0; index < size - 1; index++) {
                    arr[index] = index + 1;
                }
            }
            /* if it's a random array */
        } else {
            for (int index = 0; index < size - 1; index++) {
                int n = (int) (Math.random() * 99 + 1);
                arr[index] = n;
            }
        }
        return arr;
    }

    /**
     * Shuffels an existing an array so that it is sorted randomly
     *
     * @param arr the array that is being scrambled
     */
    private static void shuffle(int[] arr) {
        Random rnd = new Random();
        for (int index = arr.length - 1; index > 0; index--) {
            int swap = rnd.nextInt(index + 1);
            // Simple swap
            int a = arr[swap];
            arr[swap] = arr[index];
            arr[index] = a;
        }
    }


    /**
     * Reverses an existing array
     *
     * @param array the array being reversed
     */
    public static void reverse(int[] array) {
        for (int index = 0; index < (array.length / 2); index = index + 1) {
            int save = array[index];
            array[index] = array[array.length - 1 - index];
            array[array.length - 1 - index] = save;
        }
    }

    /**
     * Checks to make sure an array is in proper order
     *
     * @param arr the array being checked
     * @return boolean flag on the algorithms desition
     */
    public static String checker(int[] arr) {
        for (int index = 0; index < arr.length - 1; index++) {
            try {
                if (arr[index] > arr[index + 1]) {
                    return "FAIL";
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return "PASS";
            }
            if (index < 25) {
                System.out.print(arr[index] + ", ");
            }
            if (index == 25) {
                System.out.print("\n");
            }
        }
        return "PASS";
    }

    /**
     * Writes the table into a file
     *
     * @param contents
     */
    public static void writeFile(String contents) {
        BufferedWriter writer = null;
        try {
           /* create a temporary file */
            File logFile = new File("READ_ME.txt");
           /* this will output the full path where the file will be written to */
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(contents);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
               /* close the writer regardless of what happens */
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException r){
                r.printStackTrace();
            }
        }
    }

    /**
     * The main method here does all the heavy lifting
     * here we test the various sorting methods and generate
     * the file "READ_ME.txt" with the results
     *
     * @param args meaningless input
     */
    public static void main(String[] args) {
        String contents = "";
        /* start with all sorted arrays */
        int[] arr1 = generateArray(true, false, 1000);
        int[] arr10 = generateArray(true, false, 10000);
        int[] arr100 = generateArray(true, false, 100000);
        int[] arr1000 = generateArray(true, false, 1000000);

        /* first round of double checking  an already sorted array */
        long sortHS1 = 0;
        long sortHS10 = 0;
        long sortHS100 = 0;
        long sortHS1000 = 0;
        long sortQS1 = 0;
        long sortQS10 = 0;
        long sortQS100 = 0;
        long sortQS1000 = 0;
        long sortMS1 = 0;
        long sortMS10 = 0;
        long sortMS100 = 0;
        long sortMS1000 = 0;

        /* test are organized as such : */
        /* --- Sorted ---
         *      heapsort
         *          1000
         *          10000
         *          100000
         *          1000000
         *      quicksort
         *      mergesort
         * --- Reversed ---
         * --- Random ---
         */

        contents = contents + "------ SORTED ------";
        /* three rounds of checking an already sorted array */
        for (int count = 1; count < 4; count++) {
            contents = contents + "\n\n   --- Round" + count + " Sorted ---";
            /* heap sort */
            Sorting.heapSort(arr1);
            sortHS1 = sortHS1 + Sorting.getTime();
            contents = contents + "\n      HeapSort" + count + ":" + checker(arr1);
            Sorting.heapSort(arr10);
            sortHS10 = sortHS10 + Sorting.getTime();
            Sorting.heapSort(arr100);
            sortHS100 = sortHS100 + Sorting.getTime();
            Sorting.heapSort(arr1000);
            sortHS1000 = sortHS1000 + Sorting.getTime();
            /* quick sort */
            Sorting.quickSort(arr1);
            sortQS1 = sortQS1 + Sorting.getTime();
            contents = contents + "\n      QuickSort" + count + ":" + checker(arr1);
            Sorting.quickSort(arr10);
            sortQS10 = sortQS10 + Sorting.getTime();
            Sorting.quickSort(arr100);
            sortQS100 = sortQS100 + Sorting.getTime();
            Sorting.quickSort(arr1000);
            sortQS1000 = sortQS1000 + Sorting.getTime();
            Sorting.mergeSort(arr1);
            /* merge sort */
            sortMS1 = sortMS1 + Sorting.getTime();
            contents = contents + "\n      MergeSort" + count + ":" + checker(arr1);
            Sorting.mergeSort(arr10);
            sortMS10 = sortMS10 + Sorting.getTime();
            Sorting.mergeSort(arr100);
            sortMS100 = sortMS100 + Sorting.getTime();
            Sorting.mergeSort(arr1000);
            sortMS1000 = sortMS1000 + Sorting.getTime();
        }
        contents = contents + "\n\n   --- Averages Sorted ---";
        contents = contents + "\n      HeapSort1 average time(ms):" + sortHS1 / 3;
        contents = contents + "\n      HeapSort10 average time(ms):" + sortHS10 / 3;
        contents = contents + "\n      HeapSort100 average time(ms):" + sortHS100 / 3;
        contents = contents + "\n      HeapSort1000 average time(ms):" + sortHS1000 / 3;
        contents = contents + "\n      QuickSort1 average time(ms):" + sortQS1 / 3;
        contents = contents + "\n      QuickSort10 average time(ms):" + sortQS10 / 3;
        contents = contents + "\n      QuickSort100 average time(ms):" + sortQS100 / 3;
        contents = contents + "\n      QuickSort1000 average time(ms):" + sortQS1000 / 3;
        contents = contents + "\n      MergeSort1 average time(ms):" + sortMS1 / 3;
        contents = contents + "\n      MergeSort10 average time(ms):" + sortMS10 / 3;
        contents = contents + "\n      MergeSort100 average time(ms):" + sortMS100 / 3;
        contents = contents + "\n      MergeSort1000 average time(ms):" + sortMS1000 / 3;

        contents = contents + "\n\n\n\n------ REVERSED ------";
        /* three rounds of checking a reversed sorted array */
        for (int count = 1; count < 4; count++) {
            contents = contents + "\n\n   --- Round" + count + " Reversed ---";
            /* heap sort */
            reverse(arr1);
            Sorting.heapSort(arr1);
            sortHS1 = sortHS1 + Sorting.getTime();
            contents = contents + "\n      HeapSort" + count + ":" + checker(arr1);
            reverse(arr10);
            Sorting.heapSort(arr10);
            sortHS10 = sortHS10 + Sorting.getTime();
            reverse(arr100);
            Sorting.heapSort(arr100);
            sortHS100 = sortHS100 + Sorting.getTime();
            reverse(arr1000);
            Sorting.heapSort(arr1000);
            sortHS1000 = sortHS1000 + Sorting.getTime();
            /* quick sort */
            reverse(arr1);
            Sorting.quickSort(arr1);
            sortQS1 = sortQS1 + Sorting.getTime();
            contents = contents + "\n      QuickSort" + count + ":" + checker(arr1);
            reverse(arr10);
            Sorting.quickSort(arr10);
            sortQS10 = sortQS10 + Sorting.getTime();
            reverse(arr100);
            Sorting.quickSort(arr100);
            sortQS100 = sortQS100 + Sorting.getTime();
            reverse(arr1000);
            Sorting.quickSort(arr1000);
            sortQS1000 = sortQS1000 + Sorting.getTime();
            /* merge sort */
            reverse(arr1);
            Sorting.mergeSort(arr1);
            sortMS1 = sortMS1 + Sorting.getTime();
            contents = contents + "\n      MergeSort" + count + ":" + checker(arr1);
            reverse(arr10);
            Sorting.mergeSort(arr10);
            sortMS10 = sortMS10 + Sorting.getTime();
            reverse(arr100);
            Sorting.mergeSort(arr100);
            sortMS100 = sortMS100 + Sorting.getTime();
            reverse(arr1000);
            Sorting.mergeSort(arr1000);
            sortMS1000 = sortMS1000 + Sorting.getTime();
        }
        contents = contents + "\n\n   --- Averages Reversed ---";
        contents = contents + "\n      HeapSort1 average time(ms):" + sortHS1 / 3;
        contents = contents + "\n      HeapSort10 average time(ms):" + sortHS10 / 3;
        contents = contents + "\n      HeapSort100 average time(ms):" + sortHS100 / 3;
        contents = contents + "\n      HeapSort1000 average time(ms):" + sortHS1000 / 3;
        contents = contents + "\n      QuickSort1 average time(ms):" + sortQS1 / 3;
        contents = contents + "\n      QuickSort10 average time(ms):" + sortQS10 / 3;
        contents = contents + "\n      QuickSort100 average time(ms):" + sortQS100 / 3;
        contents = contents + "\n      QuickSort1000 average time(ms):" + sortQS1000 / 3;
        contents = contents + "\n      MergeSort1 average time(ms):" + sortMS1 / 3;
        contents = contents + "\n      MergeSort10 average time(ms):" + sortMS10 / 3;
        contents = contents + "\n      MergeSort100 average time(ms):" + sortMS100 / 3;
        contents = contents + "\n      MergeSort1000 average time(ms):" + sortMS1000 / 3;

        contents = contents + "\n\n\n\n------ RANDOM ------";
        /* three rounds of checking a random array */
        for (int count = 0; count < 4; count++) {
            contents = contents + "\n\n   --- Round" + count + " Random ---";
            /* heap sort */
            shuffle(arr1);
            Sorting.heapSort(arr1);
            sortHS1 = sortHS1 + Sorting.getTime();
            contents = contents + "\n      HeapSort" + count + ":" + checker(arr1);
            shuffle(arr10);
            Sorting.heapSort(arr10);
            sortHS10 = sortHS10 + Sorting.getTime();
            shuffle(arr100);
            Sorting.heapSort(arr100);
            sortHS100 = sortHS100 + Sorting.getTime();
            shuffle(arr1000);
            Sorting.heapSort(arr1000);
            sortHS1000 = sortHS1000 + Sorting.getTime();
            /* quick sort */
            shuffle(arr1);
            Sorting.quickSort(arr1);
            sortQS1 = sortQS1 + Sorting.getTime();
            contents = contents + "\n      QuickSort" + count + ":" + checker(arr1);
            shuffle(arr10);
            Sorting.quickSort(arr10);
            sortQS10 = sortQS10 + Sorting.getTime();
            shuffle(arr100);
            Sorting.quickSort(arr100);
            sortQS100 = sortQS100 + Sorting.getTime();
            shuffle(arr1000);
            Sorting.quickSort(arr1000);
            sortQS1000 = sortQS1000 + Sorting.getTime();
            /* merge sort */
            shuffle(arr1);
            Sorting.mergeSort(arr1);
            sortMS1 = sortMS1 + Sorting.getTime();
            contents = contents + "\n      MergeSort" + count + ":" + checker(arr1);
            shuffle(arr10);
            Sorting.mergeSort(arr10);
            sortMS10 = sortMS10 + Sorting.getTime();
            shuffle(arr100);
            Sorting.mergeSort(arr100);
            sortMS100 = sortMS100 + Sorting.getTime();
            shuffle(arr1000);
            Sorting.mergeSort(arr1000);
            sortMS1000 = sortMS1000 + Sorting.getTime();
        }
        contents = contents + "\n\n   --- Averages Random ---";
        contents = contents + "\n      HeapSort1 average time(ms):" + sortHS1 / 3;
        contents = contents + "\n      HeapSort10 average time(ms):" + sortHS10 / 3;
        contents = contents + "\n      HeapSort100 average time(ms):" + sortHS100 / 3;
        contents = contents + "\n      HeapSort1000 average time(ms):" + sortHS1000 / 3;
        contents = contents + "\n      QuickSort1 average time(ms):" + sortQS1 / 3;
        contents = contents + "\n      QuickSort10 average time(ms):" + sortQS10 / 3;
        contents = contents + "\n      QuickSort100 average time(ms):" + sortQS100 / 3;
        contents = contents + "\n      QuickSort1000 average time(ms):" + sortQS1000 / 3;
        contents = contents + "\n      MergeSort1 average time(ms):" + sortMS1 / 3;
        contents = contents + "\n      MergeSort10 average time(ms):" + sortMS10 / 3;
        contents = contents + "\n      MergeSort100 average time(ms):" + sortMS100 / 3;
        contents = contents + "\n      MergeSort1000 average time(ms):" + sortMS1000 / 3;

        /* write dat file */
        writeFile(contents);
    }
}
