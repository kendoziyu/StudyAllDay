package collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:  测试 HashMap <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/3/22 <br>
 */
public class HashMap7Test {

    private String[] array = {"morning", "noon", "afternoon", "midnight", "good morning", "good afternoon", "at midnight"};

    @Test
    public void putValue() {
        HashMap<String, String> table = new HashMap<String, String>();

        for (int i = 0; i < array.length; i++) {
            table.put(array[i], String.valueOf(i));
        }

        for (Map.Entry<String, String> entry : table.entrySet()) {
            Assert.assertEquals(entry.getKey(), array[Integer.parseInt(entry.getValue())]);
        }

    }
}
