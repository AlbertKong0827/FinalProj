/*
 * Name: Linghang Kong
 * PID: A16127732
 */

import java.util.*;

/**
 * FADAF class with high performance
 *
 * @param <K> Generic type of key
 * @param <D> Generic type of data
 * @author Linghang Kong
 * @since June 5th 2020
 */

public class FADAF<K extends Comparable<? super K>, D> {

    /*
     * instance variables
     */
    HashTable<K> hashTable;
    DAFTree<K,D> DAFTree;

    /**
     * Constructor for FADAF.
     *
     * @param capacity initial capacity
     * @throws IllegalArgumentException if capacity is less than the minimum
     *                                  threshold
     */
    public FADAF(int capacity) {
        //constraints check
        if(capacity < HashTable.MIN_CAPACITY) {
            throw new IllegalArgumentException();
        }
        hashTable = new HashTable<>(capacity);
        DAFTree = new DAFTree<>();
    }

    /**
     * Returns the total number of key-data pairs stored.
     *
     * @return count of key-data pairs
     */
    public int size() {
        return DAFTree.size();
    }

    /**
     * Returns the total number of unique keys stored.
     *
     * @return count of unique keys
     */
    public int nUniqueKeys() {
        return DAFTree.nUniqueKeys();
    }

    /**
     * Insert the given key-data pair.
     *
     * @param key  key to insert
     * @param data data to insert
     * @return true if the pair is inserted, false if the pair was already present
     * @throws NullPointerException if key or data is null
     */
    public boolean insert(K key, D data) {
        if(key == null || data == null) {
            throw new NullPointerException();
        }
        //if exist return false
        if(DAFTree.lookup(key,data))
            return false;
        hashTable.insert(key);
        DAFTree.insert(key, data);
        return true;
    }

    /**
     * Remove all key-data pairs that share the given key from the FADAF.
     *
     * @param key key to remove
     * @return true if at least 1 pair is removed, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean removeAll(K key) {
        if(key == null) {
            throw new NullPointerException();
        }
        if(!DAFTree.lookupAny(key)) {
            return false;
        }
        DAFTree.removeAll(key);
        hashTable.delete(key);
        return true;
    }

    /**
     * Remove the specified pair from the FADAF.
     *
     * @param key  key of the pair to remove
     * @param data data of the pair to remove
     * @return true if this pair is removed, false if this pair is not present
     * @throws NullPointerException if key or data is null
     */
    public boolean remove(K key, D data) {
        if(key == null || data == null) {
            throw new NullPointerException();
        }
        if(!DAFTree.lookup(key, data)) {
            return false;
        }
        hashTable.delete(key);
        DAFTree.remove(key, data);
        return true;
    }

    /**
     * Check if any pair with the given key is stored.
     *
     * @param key key to lookup
     * @return true if any pair is found, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean lookupAny(K key) {
        if(key == null) {
            throw new NullPointerException();
        }
        return hashTable.lookup(key);
    }

    /**
     * Check if a pair with the given key and data is stored.
     *
     * @param key  key of the pair to lookup
     * @param data data of the pair to lookup
     * @return true if the pair is found, false otherwise
     * @throws NullPointerException if key or data is null
     */
    public boolean lookup(K key, D data) {
        if(key == null || data == null) {
            throw new NullPointerException();
        }
        return DAFTree.lookup(key , data);
    }

    /**
     * Return a LinkedList of all keys (including duplicates) in ascending order.
     *
     * @return a list of all keys, empty list if no keys stored
     */
    public LinkedList<K> getAllKeys() {
        LinkedList<K> result = new LinkedList<K>();
        DAFTree.DAFTreeIterator iterator = (DAFTree.DAFTreeIterator) DAFTree.iterator();
        while(iterator.hasNext()){
            DAFTree.DAFNode nextNode = iterator.next();
            result.add((K) nextNode.key);
        }
        return result;
    }

    /**
     * Return a LinkedList of data paired with the given key.
     *
     * @param key target key
     * @return a list of data
     * @throws NullPointerException if the key is null
     */
    public LinkedList<D> getAllData(K key) {
        if(key == null ) {
            throw new NullPointerException();
        }
        return DAFTree.getAllData(key);
    }

    /**
     * Return the minimum key stored.
     *
     * @return minimum key, or null if no keys stored
     */
    public K getMinKey() {
        DAFTree.DAFTreeIterator iterator = (DAFTree.DAFTreeIterator) DAFTree.iterator();
        K nextKey = null;
        if(iterator.hasNext())
            nextKey = (K) iterator.next().key;
        return nextKey;
    }

    /**
     * Return the maximum key stored.
     *
     * @return maximum key, or null if no keys stored
     */
    public K getMaxKey() {
        return getAllKeys().getLast();
    }


    public static  void  main(String args[]){
        FADAF<Integer , Integer> fadaf = new FADAF(12);
        fadaf.insert(3 , 1);
        fadaf.insert(4 , 1);

        fadaf.insert(1 , 1);
        fadaf.insert(1 , 2);
        fadaf.insert(1 , 3);
        fadaf.insert(2 , 1);
        fadaf.insert(2 , 1);

        fadaf.insert(5 , 1);
        fadaf.insert(5 , 2);
        fadaf.insert(7 , 1);
        fadaf.insert(7 , 2);
        fadaf.insert(9 , 1);
        fadaf.insert(9 , 2);
        fadaf.insert(11 , 1);

        int m = fadaf.size();
        int n = fadaf.nUniqueKeys();

        boolean b = fadaf.remove(11,2);
        boolean b1 = fadaf.removeAll(9);
        b = fadaf.lookupAny(7);
        b1 = fadaf.lookup(7,1);
        b1 = fadaf.lookup(7,3);

        LinkedList<Integer> linkedList = fadaf.getAllData(7);
        LinkedList<Integer> linkedList1 = fadaf.getAllKeys();
        m = fadaf.getMinKey();
        n = fadaf.getMaxKey();
    }


}