import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class HashMapTest {

    private HashMap<Integer, Integer> map;
    public static final int TIMEOUT = 1000;//this can be changed; it is simply to prevent infinite loops
    public static final String PATH = "src/words.txt";
    public static final int FUZZ_LENGTH = 100000;

    @Before
    public void setUp() throws Exception {
        map = new HashMap<>(5);
    }

    @Test(timeout = TIMEOUT)
    public void defaultConstructor(){
        HashMap map = new HashMap();
        assertEquals("Your map, when initialized using the default"
                + " constructor, did not have the same length as the initial"
                + " capacity constant", HashMap.INITIAL_CAPACITY, map.getTable().length);
        assertEquals("A new instance of your map had a nonzero size.", map.size(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void customConstructor(){
        assertEquals("Your map, when initialized using the"
                + " constructor that accepts an integer, did not have the"
                + " correct length.", HashMap.INITIAL_CAPACITY, new HashMap<Integer, Integer>().getTable().length);
        assertEquals("A new instance of your map had a nonzero size.", map.size(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void put(){
        //Preliminary exception testing
        try{
            map.put(null,0);
            fail("Your map did not throw an exception when adding with a null key.");
        } catch (IllegalArgumentException e){}
        try{
            map.put(0,null);
            fail("Your map did not throw an exception when adding with a null value.");
        } catch (IllegalArgumentException e){}
        try{
            map.put(null,null);
            fail("Your map did not throw an exception when adding with a null key and value.");
        } catch (IllegalArgumentException e){}

        tM(0,null,null,null,null,null);
        //Round 1: basic adding.
        Integer out = map.put(0,0);
        assertNull("The put function did not return null when adding a new key to the list.", out);
        tM(1,e(0,0),null,null,null,null);
        map.put(2,4);
        tM(2,e(0,0),null,e(2,4),null,null);
        map.put(3,9);
        tM(3,e(0,0),null,e(2,4),e(3,9),null);
        //Round 2: a resize occurs, but no rehashing is required.
        map.put(5,25);
        tM(4,e(0,0),null,e(2,4),e(3,9),null,e(5,25),null,null,null,null,null);
        //Round 3: a negative hashcode is provided. According to the homework docs, it should be modded by table length, then taken to the absolute value.
        map.put(-6, 36);
        tM(5,e(0,0),null,e(2,4),e(3,9),null,e(5,25),e(-6,36),null,null,null,null);
        //Round 4: wrapping test. This will just make sure your compression function handles large hashcodes.
        map.put(12, 144);
        tM(6,e(0,0),e(12,144),e(2,4),e(3,9),null,e(5,25),e(-6,36),null,null,null,null);
        map.put(40, 1600);
        tM(7,e(0,0),e(12,144),e(2,4),e(3,9),null,e(5,25),e(-6,36),e(40,1600),null,null,null);
        //Round 5: a resize that requires rehashing will occur.
        map.put(100, 10000);
        tM(8,e(0,0),null,e(2,4),e(3,9),null,e(5,25),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),null,null,null,null,e(40,1600),null,null,
                null,null,null);
        //Round 6: some probing
        map.put(24, 576);
        tM(9,e(0,0),e(24,576),e(2,4),e(3,9),null,e(5,25),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),null,null,null,null,e(40,1600),null,null,
                null,null,null);
        map.put(35, 1225);
        tM(10,e(0,0),e(24,576),e(2,4),e(3,9),null,e(5,25),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),e(35,1225),null,null,null,e(40,1600),null,null,
                null,null,null);
        map.put(58, 3364);
        tM(11,e(0,0),e(24,576),e(2,4),e(3,9),null,e(5,25),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),e(35,1225),e(58,3364),null,null,e(40,1600),null,null,
                null,null,null);
        map.put(37, 1369);
        tM(12,e(0,0),e(24,576),e(2,4),e(3,9),null,e(5,25),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),e(35,1225),e(58,3364),e(37,1369),null,e(40,1600),null,null,
                null,null,null);
        //Round 7: replacing duplicates
        out = map.put(37, 1370);
        assertEquals("The value returned from the get function did not match the expected value.", new Integer(1369),out);
        tM(12,e(0,0),e(24,576),e(2,4),e(3,9),null,e(5,25),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),e(35,1225),e(58,3364),e(37,1370),null,e(40,1600),null,null,
                null,null,null);
        map.put(0, 1);
        tM(12,e(0,1),e(24,576),e(2,4),e(3,9),null,e(5,25),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),e(35,1225),e(58,3364),e(37,1370),null,e(40,1600),null,null,
                null,null,null);
        map.put(5, 4);
        tM(12,e(0,1),e(24,576),e(2,4),e(3,9),null,e(5,4),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),e(35,1225),e(58,3364),e(37,1370),null,e(40,1600),null,null,
                null,null,null);
    }

    @Test(timeout = TIMEOUT)
    public void getAndRemoveAndContains(){
        //Preliminary exception testing
        try {
            map.remove(null);
            fail("Your map did not throw an exception when attempting to remove null data.");
        } catch (IllegalArgumentException e){}
        try {
            map.get(null);
            fail("Your map did not throw an exception when searching for null data.");
        } catch (IllegalArgumentException e){}
        try {
            map.get(0);
            fail("Your map did not throw an exception when searching for a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.remove(0);
            fail("Your map did not throw an exception when removing a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.containsKey(null);
            fail("Your map did not throw an exception when searching for a nonexistent element.");
        } catch (IllegalArgumentException e){}
        //Round 1: removing from an unprobed map.
        map.put(0,0);map.put(1,1);map.put(2,4);
        tM(3,e(0,0),e(1,1),e(2,4),null,null);
        assertEquals("Your map returned the wrong element.",new Integer(0),map.get(0));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(0));
        assertEquals("Your map removed the wrong element.",new Integer(0),map.remove(0));
        tM(2,d(0,0),e(1,1),e(2,4),null,null);
        assertEquals("Your map returned the wrong element.",new Integer(1),map.get(1));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(1));
        assertEquals("Your map removed the wrong element.",new Integer(1),map.remove(1));
        tM(1,d(0,0),d(1,1),e(2,4),null,null);
        assertEquals("Your map returned the wrong element.",new Integer(4),map.get(2));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(2));
        assertEquals("Your map removed the wrong element.",new Integer(4),map.remove(2));
        tM(0,d(0,0),d(1,1),d(2,4),null,null);
        //Some extra tests to make sure you are properly deleting elements
        try {
            map.get(0);
            fail("Your map did not throw an exception when searching for a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.remove(0);
            fail("Your map did not throw an exception when removing a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.get(1);
            fail("Your map did not throw an exception when searching for a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.remove(1);
            fail("Your map did not throw an exception when removing a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.get(2);
            fail("Your map did not throw an exception when searching for a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.remove(2);
            fail("Your map did not throw an exception when removing a nonexistent element.");
        } catch (NoSuchElementException e){}
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(0));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(1));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(2));
        //Round 2: removing from a probed map.
        map.put(0,0);map.put(5,25);map.put(10,100);
        tM(3,e(0,0),e(5,25),e(10,100),null,null);
        assertEquals("Your map returned the wrong element.",new Integer(0),map.get(0));
        assertEquals("Your map returned the wrong element.",new Integer(100),map.get(10));
        assertEquals("Your map returned the wrong element.",new Integer(25),map.get(5));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(0));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(10));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(5));
        assertEquals("Your map removed the wrong element.",new Integer(0),map.remove(0));
        tM(2,d(0,0),e(5,25),e(10,100),null,null);
        assertEquals("Your map returned the wrong element.",new Integer(100),map.get(10));
        assertEquals("Your map returned the wrong element.",new Integer(25),map.get(5));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(10));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(5));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(0));
        assertEquals("Your map removed the wrong element.",new Integer(100),map.remove(10));
        tM(1,d(0,0),e(5,25),d(10,100),null,null);
        assertEquals("Your map returned the wrong element.",new Integer(25),map.get(5));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(5));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(0));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(10));
        assertEquals("Your map removed the wrong element.",new Integer(25),map.remove(5));
        tM(0,d(0,0),d(5,25),d(10,100),null,null);
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(0));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(5));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(10));
        //Round 3: working with a map full of deleted items.
        map.put(3,9);map.put(8,64);
        tM(2,d(0,0),d(5,25),d(10,100),e(3,9),e(8,64));
        assertEquals("Your map returned the wrong element.",new Integer(9),map.get(3));
        assertEquals("Your map returned the wrong element.",new Integer(64),map.get(8));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(3));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(8));
        assertEquals("Your map removed the wrong element.",new Integer(9),map.remove(3));
        tM(1,d(0,0),d(5,25),d(10,100),d(3,9),e(8,64));
        assertEquals("Your map returned the wrong element.",new Integer(64),map.get(8));
        assertTrue("Your map said it did not contain an element that it should have.",map.containsKey(8));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(3));
        assertEquals("Your map removed the wrong element.",new Integer(64),map.remove(8));
        tM(0,d(0,0),d(5,25),d(10,100),d(3,9),d(8,64));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(3));
        assertFalse("Your map said it contained an element that it should not have.",map.containsKey(8));
        //Some more checks for exceptions. If an infinite loop occurs here, your search is probably uncapped by the map's length.
        try {
            map.get(3);
            fail("Your map did not throw an exception when searching for a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.remove(3);
            fail("Your map did not throw an exception when removing a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.get(8);
            fail("Your map did not throw an exception when searching for a nonexistent element.");
        } catch (NoSuchElementException e){}
        try {
            map.remove(8);
            fail("Your map did not throw an exception when removing a nonexistent element.");
        } catch (NoSuchElementException e){}
    }

    @Test(timeout = TIMEOUT)
    public void delTests(){
        map = new HashMap<>(9);//can hold 6 elements before resizing
        //The map will contain 5 elements with several empty slots; it must place elements in the correct position.
        map.put(0,0);map.put(9,9);map.put(18,18);map.put(27,27);map.put(36,36);map.put(45,45);
        map.remove(9);map.remove(27);map.put(6,6);map.put(15,15);
        map.remove(45);map.put(24,24);map.remove(15);
        tM(5,e(0,0),d(9,9),e(18,18),d(27,27),e(36,36),d(45,45),e(6,6),d(15,15),e(24,24));
        map.put(90,90);
        tM(6,e(0,0),e(90,90),e(18,18),d(27,27),e(36,36),d(45,45),e(6,6),d(15,15),e(24,24));
        map.remove(0);
        tM(5,d(0,0),e(90,90),e(18,18),d(27,27),e(36,36),d(45,45),e(6,6),d(15,15),e(24,24));
        map.put(900,900);
        tM(6,e(900,900),e(90,90),e(18,18),d(27,27),e(36,36),d(45,45),e(6,6),d(15,15),e(24,24));
        map.remove(18);
        tM(5,e(900,900),e(90,90),d(18,18),d(27,27),e(36,36),d(45,45),e(6,6),d(15,15),e(24,24));
        map.remove(90);
        tM(4,e(900,900),d(90,90),d(18,18),d(27,27),e(36,36),d(45,45),e(6,6),d(15,15),e(24,24));
        map.put(33,33);
        tM(5,e(900,900),d(90,90),d(18,18),d(27,27),e(36,36),d(45,45),e(6,6),e(33,33),e(24,24));
        map.put(42,42);
        tM(6,e(900,900),e(42,42),d(18,18),d(27,27),e(36,36),d(45,45),e(6,6),e(33,33),e(24,24));
        //Note: this will trigger a resize even though the element is a duplicate. This is according to the javadocs.
        map.put(42,43);
        tM(6, null,null,null,null,e(42,43),
                e(24,24),e(6,6),e(900,900),null,null,
                null,null,null,null,e(33,33),
                null,null,e(36,36),null);
    }

    @Test(timeout = TIMEOUT)
    public void resizingAndClear(){
        //Note: this test assumes that your map is adding elements to the newly resized array in the order they appeared in the old array.
        map.put(101,101);
        map.put(102,102);
        map.put(103,103);
        tM(3,null,e(101,101),e(102,102),e(103,103),null);
        map.resizeBackingTable(6);
        tM(3,e(102,102),e(103,103),null,null,null,e(101,101));
        map.resizeBackingTable(7);
        tM(3,null,null,null,e(101,101),e(102,102),e(103,103),null);
        map.resizeBackingTable(3);
        tM(3,e(102,102),e(103,103),e(101,101));
        try {
            map.resizeBackingTable(2);
            fail("Your map attempted to resize the backing array to a size smaller than the number of elements in the map.");
        }catch (IllegalArgumentException e){}
        map.clear();
        tM(0,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }

    @Test(timeout = TIMEOUT)
    public void sets(){
        try{put();}catch(Exception e){System.out.println("Please test the put() test first.");throw e;}
        Set<Integer> keys = map.keySet();
        /*tM(12,e(0,1),e(24,576),e(2,4),e(3,9),null,e(5,4),e(-6,36),null,e(100,10000),null,
                null,null,e(12,144),e(35,1225),e(58,3364),e(37,1370),null,e(40,1600),null,null,
                null,null,null);*/
        for(int i:new int[]{0,24,2,3,5,-6,100,12,35,58,37,40})
            assertTrue("Your map's key set did not contain a key it was supposed to have.",keys.contains(i));
        assertFalse("Your map's key set contains a null value.",keys.contains(null));
        try{
            map.remove(100);
        }catch(Exception e){System.out.println("Please test the remove() method first.");throw e;}
        keys = map.keySet();
        assertFalse("Your map's key set contains a value that was originally in the map, but was removed.",keys.contains(100));

        List<Integer> values = map.values();
        for(int i:new int[]{1,576,4,9,4,36,144,1225,3364,1370,1600})
            assertTrue("Your map's value list did not contain a value it was supposed to have.",values.contains(i));
        assertFalse("Your map's value list has a null value.", values.contains(null));
        assertFalse("Your map's value list contains a value that was originally in the map, but was removed.",values.contains(10000));
        assertNotEquals("Your map's value list does not contain multiple elements of a value that appears multiple times in the map.",values.indexOf(4),values.lastIndexOf(4));
    }

    @Test
    public void fuzz(){
        Random rand = new Random(10101);
        java.util.HashMap<Integer, Integer> oracle = new java.util.HashMap<>();
        map = new HashMap<>();
        for(int i=0;i<FUZZ_LENGTH;i++){
            int k = rand.nextInt(1000);
            int v = rand.nextInt(10000);
            if(rand.nextBoolean()){
                oracle.put(k,v);
                map.put(k,v);
            }else{
                assertEquals("Your map does not contain a key that it should.",oracle.containsKey(k), map.containsKey(k));
                if(oracle.containsKey(k)){
                    assertEquals("Your map contains the wrong value for a key.", oracle.get(k), map.get(k));
                    assertEquals("Your map removed the wrong value for a key.", oracle.remove(k), map.remove(k));
                }
            }
            assertEquals("Your map's size does not match the expected value.", oracle.size(), map.size());
            if(i%(FUZZ_LENGTH/10)==FUZZ_LENGTH/10-1){
                assertEquals("Your map's key set does not match the expected value.",oracle.keySet(),map.keySet());
                ArrayList<Integer> oracleValues=new ArrayList<>(oracle.values());
                oracleValues.sort(Integer::compareTo);
                ArrayList<Integer> actualValues=new ArrayList<>(map.values());
                actualValues.sort(Integer::compareTo);
                assertEquals("Your map's value list does not match the expected value.",oracleValues, actualValues);
            }
        }
    }
    @Test
    public void wordCounter(){
        HashMap<String,Integer> map = new HashMap<>();
        String[] words = readWords();
        for(String word:words){
            word = word.toLowerCase();
            if(map.containsKey(word)){
                map.put(word,map.get(word)+1);
            }else{
                map.put(word,1);
            }
        }
        PriorityQueue<MapEntry<String,Integer>> heap = new PriorityQueue<>(Comparator.comparing(MapEntry<String, Integer>::getValue).reversed());
        for(String key:map.keySet())heap.add(new MapEntry<>(key,map.get(key)));
        while(!heap.isEmpty()){
            MapEntry<String, Integer> ent = heap.poll();
            System.out.println(ent.getKey()+": "+ent.getValue());
        }
    }

    private void tM(int size, MapEntry<Integer, Integer>... ents){
        try {
            assertEquals("Your map's size variable and the expected size are not the same.", size, map.size());
            assertEquals("Your map's backing array and the expected"
                    + " backing array have different lengths. Make sure you are"
                    + " resizing only when the addition of an element is about to"
                    + " exceed the load factor.", ents.length, map.getTable().length);
            for (int i = 0; i < ents.length; i++) {
                assertEquals("An element in the expected backing array was different from an element in your array.", ents[i], map.getTable()[i]);
            }
        } catch (AssertionError e){
            System.out.println("Expected Backing Array: (size variable = "+size+")");
            for(int i=0;i<ents.length;i++){
                String s = ents[i]==null?"null":"<"+ents[i].getKey().intValue()+", "+ents[i].getValue().intValue()+">";
                if(ents[i]!=null && ents[i].isRemoved())s+=" DEL";
                System.out.println("["+i+"] = "+s);
            }
            System.out.println();
            System.out.println("Actual Backing Array: (size variable = "+map.size()+")");
            for(int i=0;i<map.getTable().length;i++){
                String s = map.getTable()[i]==null?"null":"<"+map.getTable()[i].getKey().intValue()+", "+map.getTable()[i].getValue().intValue()+">";
                if(map.getTable()[i]!=null && map.getTable()[i].isRemoved())s+=" DEL";
                System.out.println("["+i+"] = "+s);
            }
            System.out.println();
            throw e;
        }
    }

    private String[] readWords(){
        Pattern p = Pattern.compile("(?<=\\s|^|\\b)(?:[-'.%$#&/]\\b|\\b[-'.%$#&/]|[A-Za-z0-9]|\\([A-Za-z0-9]+\\))+(?=\\s|$|\\b)");
        LinkedList<String> list = new LinkedList<>();
        try (BufferedReader r = Files.newBufferedReader(Paths.get(PATH),Charset.defaultCharset())) {
            r.lines().forEach(s -> {
                Matcher m = p.matcher(s);while(m.find())list.addLast(m.group().replaceAll("[\\(\\)\\.]",""));
            });
            return list.toArray(new String[list.size()]);
        } catch (Exception e){
            return new String[]{"An","error","occurred"};
        }
    }

    private MapEntry<Integer, Integer> e(int a, int b){return new MapEntry<>(a,b);}
    private MapEntry<Integer, Integer> d(int a, int b){MapEntry<Integer,Integer> m = e(a,b);m.setRemoved(true);return m;}
}