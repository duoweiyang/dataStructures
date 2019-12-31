/**
 * Your implementation of an ArrayList.
 *
 * @author Duo-Wei Yang
 * @userid dyang305
 * @GTID 903213022
 * @version 1.0
 */
public class ArrayList<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of the array list.
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the element to the index specified.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Adding to index {@code size} should be amortized O(1),
     * all other adds are O(n).
     *
     * @param index The index where you want the new element.
     * @param data The data to add to the list.
     * @throws java.lang.IndexOutOfBoundsException if index is negative
     * or index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index entered "
                    + "is less than zero or greater than the number"
                    + "of elements in the array.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null, "
                    + "which is invalid.");
        }
        if (size == backingArray.length) {
            T[] tempArray = (T[]) new Object[backingArray.length * 2];

            for (int i = 0; i < index; i++) {
                tempArray[i] = backingArray[i];
            }

            for (int i = index + 1; i < size + 1; i++) {
                tempArray[i + 1] = backingArray[i];
            }
            tempArray[index] = data;
            backingArray = tempArray;
            size += 1;
        } else if (size < backingArray.length) {
            if (index == size) {
                backingArray[index] = data;
                size += 1;
            } else {
                for (int i = size; i >= index; i--) {
                    backingArray[i + 1] = backingArray[i];
                }
                backingArray[index] = data;
                size += 1;
            }
        }
    }

    /**
     * Add the given data to the front of your array list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null, "
                    + "which is invalid.");
        }
        if (size == backingArray.length) {
            T[] tempArray = (T[]) new Object[backingArray.length * 2];

            for (int i = 1; i <= backingArray.length; i++) {
                tempArray[i] = backingArray[i - 1];
            }
            tempArray[0] = data;
            backingArray = tempArray;
            size += 1;
        } else if (size < backingArray.length) {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[0] = data;
            size += 1;
            return;
        }
    }

    /**
     * Add the given data to the back of your array list.
     *
     * Must be amortized O(1).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null, "
                    + "which is invalid.");
        }
        if (size == backingArray.length) {
            T[] tempArray = (T[]) new Object[backingArray.length * 2];

            for (int i = 0; i < size; i++) {
                tempArray[i] = backingArray[i];
            }
            tempArray[size] = data;
            backingArray = tempArray;
            size += 1;
        } else if (size < backingArray.length) {
            backingArray[size] = data;
            size += 1;
        }
    }

    /**
     * Removes and returns the element at index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * This method should be O(1) for index {@code size - 1} and O(n) in
     * all other cases.
     *
     * @param index The index of the element
     * @return The object that was formerly at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index entered is "
                    + "less than zero or greater or equal to the "
                    + "number of elements in the array.");
        }
        if (index == size) {
            T result = backingArray[index];
            backingArray[size] = null;
            size -= 1;
            return result;
        }
        if (size < backingArray.length) {
            T result = backingArray[index];
            for (int i = index; i <= size; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            size -= 1;
            return result;
        }
        return null;
    }

    /**
     * Remove the first element in the list and return it.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return The data from the front of the list or null if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            return null;
        } else {
            T result = backingArray[0];
            for (int i = 1; i <= size; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size] = null;
            size -= 1;
            return result;
        }
    }

    /**
     * Remove the last element in the list and return it.
     *
     * Must be O(1).
     *
     * @return The data from the back of the list or null if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            return null;
        } else {
            T result = backingArray[size - 1];
            backingArray[size - 1] = null;
            size -= 1;
            return result;
        }
    }

    /**
     * Returns the element at the given index.
     *
     * Must be O(1).
     *
     * @param index The index of the element
     * @return The data stored at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index entered is "
                    + "less than zero or equal or greater than size.");
        } else {
            T result = backingArray[index];
            return result;
        }
    }

    /**
     * Return a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clear the list. Reset the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    public void clear() {
        T[] tempArray = (T[]) new Object[INITIAL_CAPACITY];
        backingArray = tempArray;
        size = 0;
    }

    /**
     * Return the size of the list as an integer.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for this list.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for this list
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}
