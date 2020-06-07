/*
 * Name: Linghang Kong
 * PID: A16127732
 */

import java.util.*;

/**
 * a  chained hash table that use linked list to handle duplicate
 *
 * @param <T> Generic type of value
 * @author Linghang Kong
 * @since June 5th 2020
 */

public class HashTable<T> {

    // constants
    public static final int RESIZE_FACTOR = 2; // resize factor
    public static final int MIN_CAPACITY = 10; // minimum initial capacity
    public static final double MAX_LOAD_FACTOR = (double) 2 / 3; // maximum load factor

    // instance variables
    private LinkedList<T>[] table; // data storage
    private int nElems; // number of elements stored

    /**
     * Constructor for hash table.
     *
     * @throws IllegalArgumentException if capacity is less than the minimum
     *                            threshold
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if(capacity < MIN_CAPACITY) {
            throw new IllegalArgumentException();
        }
        table = new LinkedList[capacity];
        for(int i=0;i<capacity;i++) {
            table[i] = new LinkedList();
        }
        nElems = 0;
    }

    /**
     * Insert the value into the hash table.
     *
     * @param value value to insert
     * @return true if the value was inserted, false if the value was already
     *         present
     * @throws NullPointerException if the value is null
     */
    public boolean insert(T value){

        if(value == null) {
            throw new NullPointerException();
        }
        if(lookup(value)) {
            return false;
        }
        //check the loading factor, rehash when conditions met
        if(nElems > table.length*MAX_LOAD_FACTOR) {
            this.rehash();
        }

        table[this.hashValue(value)].add(value);
        nElems++;
        return true;
    }

    /**
     * Delete the given value from the hash table.
     *
     * @param value value to delete
     * @return true if the value was deleted, false if the value was not found
     * @throws NullPointerException if the value is null
     */
    public boolean delete(T value) {
        if(value==null) {
            throw new NullPointerException();
        }
        if(!lookup(value)) {
            return false;
        }
        table[this.hashValue(value)].remove(value);
        nElems--;
        return true;
    }

    /**
     * Check if the given value is present in the hash table.
     *
     * @param value value to look up
     * @return true if the value was found, false if the value was not found
     * @throws NullPointerException if the value is null
     */
    public boolean lookup(T value) {
        if(value == null) {
            throw new NullPointerException();
        }
        return table[this.hashValue(value)].contains(value);
    }

    /**
     * Get the total number of elements stored in the hash table.
     *
     * @return total number of elements
     */
    public int size() {
        return nElems;
    }

    /**
     * Get the capacity of the hash table.
     *
     * @return capacity
     */
    public int capacity() {
       return table.length;
    }

    /**
     * Hash function calculated by the hash code of value.
     *
     * @param value input
     * @return hash value (index)
     */
    private int hashValue(T value) {
        return (value.hashCode()%table.length);
    }

    /**
     * Double the capacity of the array and rehash all values.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        List<T> temp = new ArrayList<>();
        for(int i=0; i<table.length; i++){
            for (T val: table[i]) {
                temp.add(val);
            }
        }

        //resize
        table = new LinkedList[capacity()*RESIZE_FACTOR];
        for(int i=0; i<table.length; i++) {
            table[i] = new LinkedList<T>();
        }

        //add every element
        nElems = 0;
        for(int i=0;i<temp.size();i++){
            this.insert(temp.get(i));
        }
    }
}
