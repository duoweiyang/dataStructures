import org.junit.Test;
import org.junit.Before;

import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * This is a basic set of unit tests for DoublyLinkedList.
 * 
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author The 1332 TAs
 * @version 1.0
 */
public class HW02Tests {
    private DoublyLinkedList<String> list;

    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<String>();
    }
    
    @Test(timeout = TIMEOUT)
    public void testToArray2() {
        String[] expectedItems = new String[0];
        Object[] array = list.toArray();
        assertArrayEquals(expectedItems, array);
    }

    @Test(timeout = TIMEOUT)
    public void testClearIsEmpty() {
        list.addAtIndex(0, "0a"); // 0a
        list.addAtIndex(1, "1a"); // 0a 1a
        list.addAtIndex(2, "2a"); // 0a 1a 2a
        list.addAtIndex(3, "3a"); // 0a 1a 2a 3a
        list.addAtIndex(4, "4a"); // 0a 1a 2a 3a 4a

        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertTrue(list.isEmpty());
    }

    @Test
    public void fuzz() {
        LinkedList<String> oracle = new LinkedList<>();
        Random rng = new Random();
        for (int i = 0; i < 100000; i++) {
            int idx;
            int val = rng.nextInt(i+1);
            switch (rng.nextInt(9)) {
            case 0:
                idx = rng.nextInt(list.size()+1);
                list.addAtIndex(idx, ""+val);
                oracle.add(idx, ""+val);
                break;
            case 1:
                if (!list.isEmpty()) {
                    idx = rng.nextInt(list.size());
                    list.removeAtIndex(idx);
                    oracle.remove(idx);
                }
                break;
            case 2:
                list.addToFront(""+val);
                oracle.addFirst(""+val);
                break;
            case 3:
                list.removeFromFront();
                if (!oracle.isEmpty())
                    oracle.removeFirst();
                break;
            case 4:
                list.addToBack(""+val);
                oracle.add(""+val);
                break;
            case 5:
                list.removeFromBack();
                if (!oracle.isEmpty())
                    oracle.removeLast();
                break;
            case 6:
                if (!list.isEmpty()) {
                    idx = rng.nextInt(list.size());
                    assertEquals(list.get(idx), oracle.get(idx));
                }
                break;
            case 7:
                String value = ""+rng.nextInt(i+1);
                assertEquals(list.lastOccurrence(value),
                        oracle.lastIndexOf(value));
                break;
            case 8:
                list.clear();
                oracle.clear();
            }
            assertEquals(list.size(), oracle.size());
            assertArrayEquals(list.toArray(), oracle.toArray());
        }
    }

    @Test
    public void bad() {
        list.addToFront("1");
        assertEquals(list.lastOccurrence("1"), 0);
        list.addToFront("1");
        assertEquals(list.lastOccurrence("1"), 1);
        list.addToFront("1");
        assertEquals(list.lastOccurrence("1"), 2);
    }
}
