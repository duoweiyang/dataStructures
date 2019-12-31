/**
 * Your implementation of a non-circular doubly linked list with a tail pointer.
 *
 * @author Duo-Wei Yang
 * @userid dyang305 (e.g. gburdell3)
 * @GTID 903213022
 * @version 1.0
 */
public class DoublyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        LinkedListNode<T> current = head;
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index entered "
                    + "is less than zero or greater than the number"
                    + "of elements in the list.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data entered "
                    + "is null, which can't be added.");
        } else if (size == 0) {
            head = newNode;
            head.setPrevious(null);
            head.setNext(null);
            tail = head;
            size += 1;
        } else if (index == 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            newNode.setPrevious(null);
            head = newNode;
            size += 1;
        } else if (index == size) {
            if (size == 1) {
                tail = newNode;
                tail.setPrevious(head);
                head.setNext(tail);
                tail.setNext(null);
                size += 1;
            } else {
                tail.setNext(newNode);
                newNode.setPrevious(tail);
                newNode.setNext(null);
                tail = newNode;
                size += 1;
            }
        } else if (index < (size - index)) {
            int i = 0;
            while (current != null) {
                if (i == index) {
                    newNode.setPrevious(current.getPrevious());
                    current.getPrevious().setNext(newNode);
                    current.setPrevious(newNode);
                    newNode.setNext(current);
                    size += 1;
                }
                current = current.getNext();
                i += 1;
            }
        } else if (index >= (size - index)) {
            int i = size - 1;
            current = tail;
            while (current != null) {
                if (i == index) {
                    newNode.setPrevious(current.getPrevious());
                    current.getPrevious().setNext(newNode);
                    current.setPrevious(newNode);
                    newNode.setNext(current);
                    size += 1;
                }
                current = current.getPrevious();
                i -= 1;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("The data entered "
                    + "is null, which can't be added.");
        } else if (size == 0) {
            head = newNode;
            head.setPrevious(null);
            head.setNext(null);
            tail = head;
            size += 1;
        } else if (size == 1) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            newNode.setPrevious(null);
            tail = head;
            head = newNode;
            size += 1;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            newNode.setPrevious(null);
            head = newNode;
            size += 1;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("The data entered "
                    + "is null, which can't be added.");
        } else if (size == 0) {
            head = newNode;
            head.setPrevious(null);
            head.setNext(null);
            tail = head;
            size += 1;
        } else if (size == 1) {
            tail = newNode;
            tail.setPrevious(head);
            head.setNext(tail);
            tail.setNext(null);
            size += 1;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            newNode.setNext(null);
            tail = newNode;
            size += 1;
        }
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 and {@code size - 1} should be O(1), all other
     * cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        LinkedListNode<T> current = head;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index entered "
                    + "is less than zero or greater or equal to the number"
                    + "of elements in the list.");
        } else if (index == 0) {
            T result = head.getData();
            current.getNext().setPrevious(null);
            size -= 1;
            return result;
        } else if (index == (size - 1)) {
            T result = tail.getData();
            tail.getPrevious().setNext(null);
            size -= 1;
            return result;
        } else if (index < (size - index)) {
            int i = 0;
            while (current != null) {
                if (i == index) {
                    T result = current.getData();
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size -= 1;
                    return result;
                }
                current = current.getNext();
                i += 1;
            }
        } else if (index >= (size - index)) {
            int i = size;
            current = tail;
            while (current != null) {
                if (i == index) {
                    T result = current.getData();
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size -= 1;
                    return result;
                }
                current = current.getPrevious();
                i -= 1;
            }
        }
        return null;
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T result = head.getData();
            head = head.getPrevious();
            size -= 1;
            return result;
        } else {
            T result = head.getData();
            head.getNext().setPrevious(null);
            head = head.getNext();
            size -= 1;
            return result;
        }
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T result = tail.getData();
            tail = tail.getNext();
            size -= 1;
            return result;
        } else {
            T result = tail.getData();
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size -= 1;
            return result;
        }
    }

    /**
     * Returns the index of the last occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     *
     * If data is in the tail, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @throws java.lang.IllegalArgumentException if data is null
     * @return the index of the last occurrence or -1 if not in the list
     */
    public int lastOccurrence(T data) {
        LinkedListNode<T> current = tail;
        if (data == null) {
            throw new IllegalArgumentException("The data entered "
                    + "is null, which is not a valid search option.");
        } else if (tail.getData().equals(data)) {
            return (size - 1);
        } else {
            int index = size - 1;
            while (current != null) {
                if (current.getData().equals(data)) {
                    return index;
                }
                current = current.getPrevious();
                index -= 1;
            }
        }
        return -1;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        LinkedListNode<T> current = head;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is less "
                    + "than zero or bigger or equal to size, which"
                    + "are all invalid.");
        } else if (index == 0) {
            return head.getData();
        } else if (index == (size - 1)) {
            return tail.getData();
        } else if (index < (size - index)) {
            int i = 0;
            while (current != null) {
                if (i == index) {
                    return current.getData();
                }
                current = current.getNext();
                i += 1;
            }
        } else if (index >= (size - index)) {
            int i = size - 1;
            current = tail;
            while (current != null) {
                if (i == index) {
                    return current.getData();
                }
                current = current.getPrevious();
                i -= 1;
            }
        }
        return current.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order from head to tail
     */
    public Object[] toArray() {
        LinkedListNode<T> current = head;
        Object[] result = new Object[size];
        int i = 0;
        while (current != null) {
            result[i] = current.getData();
            current = current.getNext();
            i += 1;
        }
        return result;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
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
     * Clears the list of all data and resets the size.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     * 
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}