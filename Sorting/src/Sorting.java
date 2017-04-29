/**
 * Sorting class to for HeapSort, QuickSort, and MergeSort
 *
 * @author Cormac Dacker cxd289
 */
class Sorting {

    //FIELDS
    private static long time = System.currentTimeMillis();

    //CONSTRUCTOR
    public Sorting() {
    }

    //WORKING METHODS

    /**
     * Sorts an array using heap sort
     *
     * @param arr the array being sorted
     */
    static void heapSort(int[] arr) {
        long zero = startTime();
        if (! inOrder(arr)) {
            for (int i = arr.length / 2 - 1; i >= 0; i--) {
                heapify(arr, arr.length, i);
            }
            for (int index = arr.length - 1; index >= 1; index--) {
                int temp = arr[0];
                arr[0] = arr[index];
                arr[index] = temp;
                heapify(arr, index, 0);
            }
            stopTime(zero);
        }
    }

    /**
     * Sorts an array using quick sort
     *
     * @param arr the array being sorted
     */
    static void quickSort(int[] arr) {
        long zero = startTime();
        if (! inOrder(arr)) {
            quickSort(arr, 0, arr.length);
        }
        stopTime(zero);
    }

    /**
     * Sorts an array using merge sort
     *
     * @param arr the array to be sorted
     */
    static void mergeSort(int[] arr) {
        long zero = startTime();
        if (! inOrder(arr)) {
            int[] fromHere = arr;
            int[] toGoTo = new int[arr.length];
            for (int stepSize = 1; stepSize < arr.length; stepSize *= 2) {
                for (int index = 0; index < arr.length; index += 2 * stepSize) {
                    merge(fromHere, toGoTo, index, index + stepSize, index + 2 * stepSize);
                }
                int[] temporary = fromHere;
                fromHere = toGoTo;
                toGoTo = temporary;
            }
            if (arr != fromHere) {
                System.arraycopy(fromHere, 0, arr, 0, arr.length);
            }
        }
        stopTime(zero);
    }

    /**
     * Checks to see if the array is already in order
     *
     * @param arr the array  in question
     * @return boolean on the algorithms decision
     */
    private static boolean inOrder(int[] arr) {
        /* if ordered form least to greatest */
        if (arr[0] < arr[1]) {
            for (int index = 0; index < arr.length - 1; index++) {
                if (arr[index] > arr[index + 1]) {
                    return false;
                }
            }
            return true;
        /* if ordered but in reverse */
        } else {
            for (int index = 0; index < arr.length - 1; index++) {
                if (arr[index] > arr[index + 1]) {
                    return false;
                }
            }
            reverse(arr);
            return true;
        }
    }

    /**
     * Reverses the inputted array
     *
     * @param arr the array in question
     */
    private static void reverse(int[] arr) {
        for (int index = 0; index < (arr.length / 2); index = index + 1) {
            int save = arr[index];
            arr[index] = arr[arr.length - 1 - index];
            arr[arr.length - 1 - index] = save;
        }
    }

    /**
     * Merges two arrays together
     *
     * @param arr1   array one to be merged with array two
     * @param arr2   array two to be merged with array one
     * @param low    the lowest value
     * @param middle the medium value
     * @param high   the highest value
     */
    private static void merge(int[] arr1, int[] arr2, int low, int middle, int high) {
        if (middle > arr1.length)
            middle = arr1.length;
        if (high > arr1.length)
            high = arr1.length;
        int i = low;
        int j = middle;
        for (int k = low; k < high; k++) {
            if (i == middle) {
                arr2[k] = arr1[j];
                j++;
            } else if (j == high) {
                arr2[k] = arr1[i];
                i++;
            } else if (arr1[j] < arr1[i]) {
                arr2[k] = arr1[j];
                j++;
            } else {
                arr2[k] = arr1[i];
                i++;
            }
        }
    }

    /**
     * Recursively sorts the array through multiple switches
     * A helper method to heapSort
     *
     * @param arr The input array being sorted
     * @param a   the left most location in t he array
     * @param b   the rightmost location in the array
     */
    private static void heapify(int arr[], int a, int b) {
        /* find largest among root, left child and right child */
        int largest = b;
        int l = 2 * b + 1;
        int r = 2 * b + 2;
        if (l < a && arr[l] > arr[largest])
            largest = l;
        if (r < a && arr[r] > arr[largest])
            largest = r;
        /* swap and continue heapifying if root is not largest */
        if (largest != b) {
            switcher(arr, largest, b);
            heapify(arr, a, largest);
        }
    }

    /**
     * Method switches two int at the locations given in the array
     * A helper method to heapSort
     *
     * @param arr the array the switch will preform in
     * @param a   being switched with b
     * @param b   being switched with a
     */
    private static void switcher(int[] arr, int a, int b) {
        int c = arr[a];
        arr[a] = arr[b];
        arr[b] = c;
    }

    /**
     * Sorts an array of integers using the quickSort algorithm
     *
     * @param arr   The full array to sort
     * @param start The starting index (inclusive)
     * @param end   The ending index (exclusive)
     */
    private static void quickSort(int[] arr, int start, int end) {
        int pivot = (start + end) / 2;
        /* temporary array containing the ordered sub-list */
        int[] sub = new int[end - start];
        int left = 0, right = sub.length;
        /* populate the sub-list */
        for (int index = start; index < end; index++) {
            if (index == pivot) continue;
            if (arr[index] < arr[pivot]) {
                sub[left++] = arr[index];
            } else {
                sub[-- right] = arr[index];
            }
        }
        /* add in the original pivot in its new position */
        sub[left] = arr[pivot];
        /* copy back into the original array */
        System.arraycopy(sub, 0, arr, start, end - start);
        /* potential for StackOverflow Error */
        /* translate new pivot position into full list index */
        left += start;
        if (left - start > 0) quickSort(arr, start, left);
        /* the start of the right branch should not include the pivot */
        left++;
        if (end - left > 0) quickSort(arr, left, end);
    }

    /**
     * Produces the start time in milliseconds
     *
     * @return time
     */
    private static long startTime() {
        return System.nanoTime() / 1000;
    }

    /**
     * Determines how much time has passed since startTime
     *
     * @param zero the start time (not actually 0)
     */
    private static void stopTime(long zero) {
        setTime((System.nanoTime()/1000) - zero);
    }

    //GETTER/SETTERS
    static long getTime() {
        return time;
    }

    private static void setTime(long time) {
        Sorting.time = time;
    }
}