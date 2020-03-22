package collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/3/22 0022 <br>
 */
public class HashMapEntryTest {

    @Test
    public void testEquals() {
        HashMap7.Entry<String, String> e1 = new HashMap7.Entry<>();
        String oldValue = e1.setValue("hello");
        Assert.assertNull(oldValue);
        Assert.assertEquals("hello", e1.getValue());

        HashMap7.Entry<String, String> e2 = new HashMap7.Entry<>();
        e2.setValue("hello2");
        Assert.assertNotEquals(e2, e1);

        e2.setValue("hello");
        Assert.assertEquals(e1, e2);
    }
}
