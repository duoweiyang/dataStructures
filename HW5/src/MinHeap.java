import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a min heap.
 *
 * @author Duo-Wei Yang
 * @userid dyang305
 * @GTID 903213022
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MinHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the ArrayList before you start the Build Heap Algorithm.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("ArrayList passed in is null.");
        }
        this.backingArray = (T[]) new Comparable[(2 * data.size()) + 1];
        this.size = data.size();
        // Copies data from ArrayList over to backingArray
        for (int i = data.size() - 1; i >= 0; i--) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Element in "
                        + "arrayList is null.");
            }
            backingArray[i + 1] = data.get(i);
        }

        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null and "
                    + "can't be added.");
        }
        if ((backingArray.length - 1) == size) {
            T[] tempArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                tempArray[i] = backingArray[i];
            }
            backingArray = tempArray;
        }
        backingArray[size + 1] = item;
        size++;
        upHeap(size);
    }

    /**
     * Removes and returns the min item of the heap. Null out all elements not
     * existing in the heap after this operation. Do not decrease the capacity
     * of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T result = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        // Remember: the smallest number of a minheap is at the top
        downHeap(1);
        return result;
    }

    /**
     * Compares a node's value with its parent. If its value is less
     * than its parent's, you keep switching the values. Used for add method.
     *
     * @param index the index of the element you want to upheap
     */
    private void upHeap(int index) {
        if (index > 1) {
            T parent = backingArray[parent(index)];
            if (backingArray[index].compareTo(parent) < 0) {
                swap(backingArray, index, parent(index));
                upHeap(parent(index));
            }
        }
    }

    /**
     * Compares a node's value with its two children. If the node is
     * greater than one or both of its children, switch it with the child
     * of lowest value. If the node is less than both the children, it
     * stays in place. Used for removing a node and buildHeap.
     *
     * @param index The index of the element you want to downheap.
     */
    private void downHeap(int index) {
        int min = index;
        int leftChild = left(index);
        int rightChild = right(index);

        if ((leftChild <= size)
                && (backingArray[leftChild].compareTo(backingArray[min]) < 0)) {
            min = leftChild;
        }
        if ((rightChild <= size)
                && (backingArray[rightChild].
                compareTo(backingArray[min]) < 0)) {
            min = rightChild;
        }
        if (min != index) {
            swap(backingArray, min, index);
            downHeap(min);
        }
    }

    /**
     * Get the current node's parent.
     *
     * @param index current node's index
     * @return parent's index
     */
    private int parent(int index) {
        return index / 2;
    }

    /**
     * Get the current node's left child.
     *
     * @param index current node's index
     * @return left child's index
     */
    private int left(int index) {
        return 2 * index;
    }

    /**
     * Get the current node's right child.
     *
     * @param index current node's index
     * @return right child's index
     */
    private int right(int index) {
        return ((2 * index) + 1);
    }

    /**
     * A helper method that swaps the values of index1 and index2.
     *
     * @param arr the array you're accessing
     * @param index1 the first index
     * @param index2 the second index
     */
    private void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element, null if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            return null;
        }
        T result = backingArray[1];
        return result;
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the heap and returns array to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for the heap.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}
