import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest {

    @Test
    public void insert() {
        HashTable test1 = new HashTable(10);
        test1.insert(1);
        test1.insert(2);
        test1.insert(3);
        test1.insert(4);
        test1.insert(5);
        test1.insert(6);
        test1.insert(7);
        assertEquals(20,test1.capacity());
    }

    @Test
    public void delete() {
    }

    @Test
    public void lookup() {
    }

    @Test
    public void size() {
    }

    @Test
    public void capacity() {
    }
}