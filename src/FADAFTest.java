import java.util.LinkedList;

import static org.junit.Assert.*;

public class FADAFTest {

    @org.junit.Test
    public void size() {

        FADAF<Integer, Integer> fadaf = new FADAF(12);
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
        assertEquals(13,m);
        int n = fadaf.nUniqueKeys();
        assertEquals(8,n);

        boolean b = fadaf.remove(11,2);
        assertEquals(false,b);
        boolean b1 = fadaf.removeAll(9);
        assertEquals(true,b1);
        b = fadaf.lookupAny(7);
        assertEquals(true,b);
        b1 = fadaf.lookup(7,1);
        assertEquals(true,b1);
        b1 = fadaf.lookup(7,3);
        assertEquals(false,b1);

        LinkedList<Integer> linkedList = fadaf.getAllData(7);
        LinkedList<Integer> linkedList1 = fadaf.getAllKeys();
        m = fadaf.getMinKey();
        n = fadaf.getMaxKey();

        FADAF<String,Integer> fadaf2 = new FADAF<>(10);
        fadaf2.insert("a",1);
        fadaf2.insert("b",2);
        fadaf2.insert("c",3);
        assertEquals(3,fadaf2.size());

    }


    @org.junit.Test
    public void nUniqueKeys() {
    }

    @org.junit.Test
    public void insert() {
    }

    @org.junit.Test
    public void removeAll() {
    }

    @org.junit.Test
    public void remove() {
    }

    @org.junit.Test
    public void lookupAny() {
    }

    @org.junit.Test
    public void lookup() {
    }

    @org.junit.Test
    public void getAllKeys() {
    }

    @org.junit.Test
    public void getAllData() {
    }

    @org.junit.Test
    public void getMinKey() {
    }

    @org.junit.Test
    public void getMaxKey() {
    }

    @org.junit.Test
    public void main() {
    }
}