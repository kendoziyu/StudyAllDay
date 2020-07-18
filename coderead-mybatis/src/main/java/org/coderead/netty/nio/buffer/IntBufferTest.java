package org.coderead.netty.nio.buffer;

import org.junit.Assert;
import org.junit.Test;

import java.nio.IntBuffer;

/**
 * 描述:  <br>
 *
 * @author: kendo ziyu <br>
 * @date: 2020/7/11 0011 <br>
 */
public class IntBufferTest {

    @Test
    public void wrapTest1() {
        int[] array = new int[6];
        array[1] = 101;
        IntBuffer buffer = IntBuffer.wrap(array);
        Assert.assertEquals(101, buffer.get(1));
        // 修改数组中的值，等同于修改缓存区的值
        array[1] = 99;
        Assert.assertEquals(99, buffer.get(1));
    }

    @Test
    public void wrapTest2() {
        int[] array = new int[6];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        IntBuffer buffer = IntBuffer.wrap(array, 1, 3);
        Assert.assertEquals(101, buffer.get(1));
        // 修改数组中的值，等同于修改缓存区的值
        array[1] = 99;
        Assert.assertEquals(99, buffer.get(1));
    }

    @Test
    public void test3() {
        IntBuffer buffer = IntBuffer.allocate(6);
        buffer.put(1);
        buffer.flip();
        int result = buffer.get();
        Assert.assertEquals(1, result);
    }
}
