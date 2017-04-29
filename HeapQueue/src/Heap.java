/**
 * Created by Cormac on 3/1/2017.
 */
public class Heap <T extends Comparable <T>>{
    private T[] items;
    private int maxItems;
    private int numItems = 0;

    public Heap(int maxSize){
        this.items = (T[])new Comparable[maxSize];
        this.maxItems = maxSize;
    }

    private void siftDown(int i) { // Input: the node to sift
        T toSift = items[i];
        int parent = i;
        int child = 2 * parent + 1; // Child to compare with; start with left child
        while (child < numItems) {
            // If the right child is bigger than the left one, use the right child instead.
            if (child +1 < numItems  && // if the right child exists
                    items[child].compareTo(items[child + 1]) < 0)  // … and is bigger than the left child
                child = child + 1; // take the right child
            if (toSift.compareTo(items[child]) >= 0)
                break; // we’re done
            // Sift down one level in the tree.
            items[parent] = items[child];
            items[child] = toSift;
            parent = child;
            child = 2 * parent + 1;
        }
        items[parent] = toSift;
    }

    public void buildHeap(T[] array, int size){
        Heap heap = new Heap(size);
        for(int i=0;i<size;i++)
            heap.insert(array[i]);
    }

    public void insert(T value){
        if (this.maxItems == items.legth){
            System.out.print("Heap's undelying storage is overflow");
        } else {
            this.numItems++;
            this.items[maxItems - 1] = value;
            siftUp(maxItems -1);
        }
    }

    public T removeMax() {
        T toRemove = items[0];
        items[0] = items[numItems-1];
        numItems--;
        siftDown(0);
        return toRemove;
    }
}
