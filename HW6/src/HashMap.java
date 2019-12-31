import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 *
 * @author Duo-Wei Yang
 * @userid dyang305
 * @GTID 903213022
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        this.table = new MapEntry[initialCapacity];
        this.size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The key or value was null.");
        }
        MapEntry<K, V> entry = new MapEntry<>(key, value);
        double loadFactor = (double) (size + 1) / (double) table.length;
        if (loadFactor > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }

        // Hashcode generator
        int hashIndex = Math.abs(entry.getKey().hashCode() % table.length);

        if (table[hashIndex] == null) {
            table[hashIndex] = entry;
            size++;
            return null;
        } else {
            MapEntry<K, V> current = table[hashIndex];
            boolean removeFlag = false;
            int removeIndex = 0;
            int i = 0;
            hashIndex = Math.abs(entry.getKey().hashCode()) % table.length;
            while (current != null && i < table.length) {
                if (!removeFlag) {
                    if (current.isRemoved()) {
                        removeIndex = hashIndex;
                        removeFlag = true;
                    }
                }
                if (current.getKey().equals(key)) {
                    if (!current.isRemoved()) {
                        V oldValue = current.getValue();
                        current.setValue(value);
                        return oldValue;
                    }
                }
                i++;
                hashIndex = (Math.abs(key.hashCode()) + i) % table.length;
                current = table[hashIndex];
            }
            if (removeFlag) {
                table[removeIndex] = entry;
                size++;
                return null;
            } else if (table[hashIndex] == null) {
                table[hashIndex] = entry;
                size++;
                return null;
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        // Hashcode generator
        int hashIndex = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> current = table[hashIndex];
        // Index starts at position of current entry
        int index = 0;

        if (current == null) {
            throw new NoSuchElementException("Key couldn't be "
                    + "found in hash map.");
        }

        while ((index < table.length) && (current != null)) {
            if (current.getKey().equals(key)) {
                if (!current.isRemoved()) {
                    V result = current.getValue();
                    current.setRemoved(true);
                    size--;
                    return result;
                } else if (current.isRemoved()) {
                    throw new NoSuchElementException("Element was "
                            + "already removed.");
                }
            }
            index++;
            hashIndex = (hashIndex + index) % table.length;
            current = table[hashIndex];
        }
        if (current == null) {
            throw new NoSuchElementException("Key couldn't be found.");
        }
        if (index == table.length) {
            throw new NoSuchElementException("Key couldn't be found.");
        }
        return null;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null, so getting "
                    + "value is impossible.");
        }
        // Hashcode generator
        int hashIndex = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> current = table[hashIndex];

        // Since key doesn't exist, no point in continuing
        if (current == null) {
            throw new NoSuchElementException("Key can't be null.");
        }

        int index = 0;
        while ((index < table.length) && (current != null)) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            index++;
            hashIndex = Math.abs(key.hashCode() + index) % table.length;
            current = table[hashIndex];
        }

        // We've reached the end and no key has been found
        throw new NoSuchElementException("Key couldn't be found in hash map.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null which is invalid.");
        }
        // If attempt to get key fails, catch exception and return false.
        int hashIndex = Math.abs(key.hashCode() % table.length);
        if (table[hashIndex] == null) {
            return false;
        } else {
            MapEntry<K, V> current = table[hashIndex];
            int index = 0;
            while ((index < table.length) && (current != null)) {
                if (current.getKey().equals(key) && !current.isRemoved()) {
                    return true;
                }
                index++;
                hashIndex = Math.abs(key.hashCode() + index) % table.length;
                current = table[hashIndex];
            }
            return false;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        // So long as entry nor key is null, add key to Set
        Set<K> keys = new HashSet<K>();

        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                if (entry.getKey() != null && !entry.isRemoved()) {
                    keys.add(entry.getKey());
                }
            }
        }
        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> values = new ArrayList<V>();

        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                values.add(entry.getValue());
            }
        }
        return values;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the number of
     * items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size || length <= 0) {
            throw new IllegalArgumentException("Length is less than the "
                    + "number of items in the hash map.");
        }
        MapEntry<K, V>[] temp = new MapEntry[length];
        int index = 0;

        while (index < table.length) {
            // Current key-value pair in the hash map
            MapEntry<K, V> current = table[index];
            if (current == null) {
                index++;
            } else if (current != null) {
                if (!current.isRemoved()) {
                    // Remember: you need to get hashcode for current element
                    int hashIndex = Math.abs(current.getKey().hashCode())
                            % length;
                    if (temp[hashIndex] == null) {
                        temp[hashIndex] = current;
                    } else {
                        int i = 0;
                        int hashTemp = Math.abs(current.
                                getKey().hashCode() + i) % length;
                        MapEntry<K, V> newEntry = temp[hashTemp];
                        while (newEntry != null) {
                            i++;
                            hashTemp = (Math.abs(current.getKey().hashCode())
                                    + i) % length;
                            newEntry = temp[hashTemp];
                        }
                        if (temp[hashTemp] == null) {
                            temp[hashTemp] = current;
                        }
                    }
                }
                index++;
            }
        }
        table = temp;
    }

    /**
     * Clears the table and resets it to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}
