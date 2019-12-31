import java.util.NoSuchElementException;

/**
 * Your implementation of a linked deque.
 *
 * @author Duo-Wei Yang
 * @userid dyang305 (e.g. gburdell3)
 * @GTID 903213022
 * @version 1.0
 */
public class LinkedDeque<T> {
    // Do not add new instance variables and do not add a new constructor.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        LinkedNode<T> newNode = new LinkedNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("Data is null, which can't"
                    + "be added to the deque.");
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
     * Adds the data to the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        LinkedNode<T> newNode = new LinkedNode<T>(data);
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
     * Removes the data at the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty"
                    + "so nothing can be removed from the front.");
        } else if (size == 1) {
            T result = head.getData();
            head = head.getPrevious();
            tail = head;
            size -= 1;
            return result;
        } else {
            T result = head.getData();
            head.getNext().setPrevious(null);
            head = head.getNext();
            size -= 1;
            if (size == 1) {
                tail = head;
            }
            return result;
        }
    }

    /**
     * Removes the data at the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty"
                    + "so nothing can be removed from the back.");
        } else if (size == 1) {
            T result = tail.getData();
            tail = tail.getNext();
            head = tail;
            size -= 1;
            return result;
        } else {
            T result = tail.getData();
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size -= 1;
            if (size == 1) {
                tail = head;
            }
            return result;
        }
    }

    /**
     * Returns the number of elements in the deque.
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
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}