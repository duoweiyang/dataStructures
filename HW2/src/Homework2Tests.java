import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * This is a more thorough of unit tests for DoublyLinkedList. The tests
 * include exceptions and pointers for add and remove operations. Let me
 * know on piazza if there are any problems with these tests!
 * 
 * @author Justin Higgins
 * @version 1.0
 */

public class Homework2Tests {
    private DoublyLinkedList<String> list;
    @Before
    public void createList() {
        list = new DoublyLinkedList<>();
    }

    @Test
    public void testCreation() {
        assertEquals(list.size(), 0);
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testAddFromFront() {
        list.addToFront("e");
        assertEquals(list.size(), 1);
        LinkedListNode<String> head = list.getHead();
        assertNull(head.getPrevious());
        assertNull(head.getNext());
        assertEquals(head.getData(), "e");
        assertEquals(list.getTail().getData(), "e"); //test references
        list.addToFront("d");
        assertEquals(list.size(), 2);
        list.addToFront("c");
        list.addToFront("b");
        list.addToFront("a"); //more adds
        assertEquals(list.size(), 5);
        String[] arr = {"a", "b", "c", "d", "e"};
        assertArrayEquals(list.toArray(), arr);
        assertEquals(list.getHead().getData(), "a"); //head is correct
        assertEquals(list.getTail().getData(), "e"); //tail is correct
    }

    @Test
    public void testAddFromBack() {
        list.addToBack("a");
        assertEquals(list.size(), 1);
        LinkedListNode<String> head = list.getHead();
        assertNull(head.getPrevious());
        assertNull(head.getNext());
        assertEquals(head.getData(), "a");
        assertEquals(list.getTail().getData(), "a"); //test references
        list.addToBack("b");
        assertEquals(list.size(), 2);
        list.addToBack("c");
        list.addToBack("d");
        list.addToBack("e");
        assertEquals(list.size(), 5);
        String[] arr = {"a", "b", "c", "d", "e"};
        assertArrayEquals(list.toArray(), arr);
        assertEquals(list.getHead().getData(), "a"); //head is correct
        assertEquals(list.getTail().getData(), "e"); //tail is correct
    }

    @Test
    public void testAddAtIndex() {
        list.addAtIndex(0, "a");
        assertEquals(list.size(), 1);
        LinkedListNode<String> head = list.getHead();
        assertNull(head.getPrevious());
        assertNull(head.getNext());
        assertEquals(head.getData(), "a");
        assertEquals(list.getTail().getData(), "a"); //test references
        list.addAtIndex(1, "c");
        assertEquals(list.size(), 2);
        list.addAtIndex(1, "b");
        list.addAtIndex(3, "e");
        list.addAtIndex(3, "d");
        assertEquals(list.size(), 5);
        String[] arr = {"a", "b", "c", "d", "e"};
        assertArrayEquals(list.toArray(), arr);
        assertEquals(list.getHead().getData(), "a"); //head is correct
        assertEquals(list.getTail().getData(), "e"); //tail is correct
    }

    private void addSomeStuff() {
        list.addToBack("a");
        list.addToBack("b");
        list.addToBack("c");
        list.addToBack("d");
        list.addToBack("e");
    }

    @Test
    public void testRemoveFromFront() {
        addSomeStuff();
        assertEquals(list.removeFromFront(), "a");
        assertEquals(list.size(), 4);
        assertNull(list.getHead().getPrevious());
        assertEquals(list.getHead().getData(), list.toArray()[0]);
        assertEquals(list.getTail().getData(), list.toArray()[3]);
    }

    @Test
    public void testRemoveFromBack() {
        addSomeStuff();
        assertEquals(list.removeFromBack(), "e");
        assertEquals(list.size(), 4);
        assertNull(list.getTail().getNext());
        assertEquals(list.getHead().getData(), list.toArray()[0]);
        assertEquals(list.getTail().getData(), list.toArray()[3]);
    }

    @Test
    public void testRemoveAtIndex() {
        addSomeStuff();
        assertEquals(list.removeAtIndex(2), "c");
        String[] arr = {"a", "b", "d", "e"};
        assertArrayEquals(list.toArray(), arr);
        LinkedListNode<String> nodeOne = list.getHead().getNext();
        LinkedListNode<String> nodeTwo = list.getTail().getPrevious();
        assertEquals(nodeOne.getNext(), nodeTwo);
        assertEquals(nodeTwo.getPrevious(), nodeOne);
    }

    @Test
    public void testGet() {
        addSomeStuff();
        assertEquals(list.get(0), "a");
        assertEquals(list.get(1), "b");
    }

    @Test
    public void testClear() {
        addSomeStuff();
        list.clear();
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertEquals(0, list.size());
    }

    @Test
    public void testToArray() {
        addSomeStuff();
        String[] arr = {"a", "b", "c", "d", "e"};
        assertArrayEquals(arr, list.toArray());
    }

    @Test
    public void testLastOccurrence() {
        addSomeStuff();
        addSomeStuff();
        assertEquals(list.lastOccurrence("e"), 9);
        assertEquals(list.lastOccurrence("d"), 8);
        assertEquals(list.lastOccurrence("c"), 7);
        assertEquals(list.lastOccurrence("b"), 6);
        assertEquals(list.lastOccurrence("a"), 5);
    }

    /**
     * Exception testing starts here.
     */

    @Test (expected = IllegalArgumentException.class)
    public void testAddAtIndexNullData() {
        list.addAtIndex(0, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddToFrontNullData() {
        list.addToFront(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddToBackNullData() {
        list.addToBack(null);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testAddNegativeIndex() {
        list.addAtIndex(-1, "a");
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testAddIndexGreaterThanSize() {
        list.addAtIndex(2, "a");
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveNegativeIndex() {
        addSomeStuff();
        list.removeAtIndex(-1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexGreaterThanSize() {
        addSomeStuff();
        list.removeAtIndex(5);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {
        addSomeStuff();
        list.get(-1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetIndexGreaterThanSize() {
        addSomeStuff();
        list.get(5);
    }
}