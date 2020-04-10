package collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapTest {


    @Test
    public void insertion() {
        TreeMap<Integer, Void> test = new TreeMap<>();
        test.put(1, null);
        test.put(2, null);
        test.put(3, null);
        test.put(4, null);
        test.put(5, null);
        test.put(6, null);
        test.put(7, null);
    }

    @Test
    public void nullable() {
        TreeMap<Integer, String> test2 = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == null) {
                    return o2 == null ? 0 : -o2;
                } else if (o2 == null) {
                    return o1;
                }
                return o1 - o2;
            }
        });

        test2.put(null, "null");
        test2.put(0, "0");

        Assert.assertEquals(test2.get(null), "null");
        Assert.assertEquals(test2.get(null), "0");
    }
}
