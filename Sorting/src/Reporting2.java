import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 * Tests the sorting class algorithms and print the results into a file
 * @author Cormac
 *
 */
public class Reporting2 {

    /**
     * a method to print the sorted array value into a new file
     * @param outputFileName the name of the file being created
     * @param array the array of sorted integers to be added to the new file
     * @throws IOException if an input output error occurs
     */
    public static void printSortedArray(String outputFileName, int[] array) throws IOException {
        FileWriter writer = new FileWriter(outputFileName);
        /* a loop to add integers from the array into the output file */
        for (int index = 0; index < array.length; index++) {
            writer.write(array[index]);
          //      writer.write(System.getProperty( "line.separator" ));
        }
        writer.close();
    }

    public static int[] copyArray(int[] arr) {
        int[] copyArr = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            copyArr[i] = arr[i];
        }
        return(copyArr);
    }

    public static void printArr(int[] arr){
        for(int i:arr){
            System.out.print(String.valueOf(i)+", ");
        }
        System.out.println("");
    }

    /**
     * A method to test each sorting algorithm
     *
     * @param arr the name of the input arr that will be tested
     * @throws IOException if an input output error occurs
     */
    public static void test (int[] arr) throws IOException {
        int[] copy = copyArray(arr);
        /* a random array that will be heap sorted */
        int[] heapArray;
        /* a random array that will be quick sorted */
        int[] quickArray;
        /* a random array that will be merge sorted */
        int[] mergeArray;
        try {
            heapArray = copy;
            quickArray = copy;
            mergeArray = copy;
            /* stores the run time for heap sort */
            long heapTime = Reporting1.testHeapSort(heapArray);
            System.out.println("HScxd289 = " + heapTime);
            printArr(heapArray);
            /* prints the heap sorted array into an output file */
            printSortedArray("cxd289HS.txt", heapArray); //will these be the sorted arrays??
            /* stores the run time for quick sort */
            long quickTime = Reporting1.testQuickSort(quickArray);
            System.out.println("QScxd289= " + quickTime);
            printArr(quickArray);
            /* prints the quick sorted array into an output file */
            printSortedArray("cxd289QS.txt", quickArray);
            /* stores the run time for merge sort */
            long mergeTime = Reporting1.testMergeSort(mergeArray);
            System.out.println("MScxd289= " + mergeTime);
            printArr(mergeArray);
            /* prints the merge sorted array into an output file */
            printSortedArray("cxd289MS.txt", mergeArray);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * the main method used to run the test reporting2 program
     * @param args the input file name
     * @throws IOException if an input output error occurs
    public static void main (String[] args) throws IOException {
        if (args.length == 1) {
            try {
                test(args[0]);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }*/
    public static void main (String[] args){
        String fileName = "";
        /* convert name to one String */
        for (int index = 0; index < args.length - 1; index++){
            if (fileName.charAt(index) != '<' && fileName.charAt(index) != '>') {
                fileName = fileName + args[index];
            }
        }
        /* find file */
        try {
            /* retrieve the contents of the file */
            String contents = new String(Files.readAllBytes(Paths.get(fileName)));
            StringTokenizer tokenizer = new StringTokenizer(contents,
                    " \\\'`~!@#$%^&*()_-+={}[]:;?/>.<\\\"“‘’\"\n");
            int[] arr = new int[contents.length()];
            int data = 0;

            /* the goal of the loop is to create an array of int that is the file */
            for(int index = 0; tokenizer.hasMoreTokens();) {
                /* if the curren token is not a comma */
                if(!tokenizer.toString().equals(",")){
                    /* if there is already some data recorded */
                    if (data == 0){
                        data = Integer.parseInt(tokenizer.toString());
                        arr[index] = data;
                        tokenizer.nextToken();
                    } else {
                        data = (data * 10) + Integer.parseInt(tokenizer.toString());
                        arr[index] = data;
                        tokenizer.nextToken();
                    }
                } else {
                    index++;
                    data = 0;
                    tokenizer.nextToken();
                }
        }
        test(arr);
        } catch (AccessDeniedException r){
            r.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

