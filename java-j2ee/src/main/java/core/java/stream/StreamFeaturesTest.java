package core.java.stream;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFeaturesTest {

    /**
     * 流的简单例子
     */
    @Test
    public void test1() {
        List<Integer> list =  Stream.of(1, 2, 3).filter(val-> val> 2).collect(Collectors.toList());
        for (Integer item : list) {
            System.out.println(item);
        }
    }
    /**
     * 流不会改变数据源
     */
    @Test
    public void test2() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        Assert.assertEquals(3, list.stream().distinct().count());
        Assert.assertEquals(4, list.size());
    }

    /**
     * 流不可以重复使用
     */
    @Test(expected = IllegalStateException.class)
    public void test3() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        Stream<Integer> newStream = integerStream.filter(val -> val > 2);
        integerStream.skip(1);
    }
}
