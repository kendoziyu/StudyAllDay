package core.java.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class StreamStatisticsTest {

    List<Apple> appleStore;
    @Before
    public void initData() {
        appleStore = Arrays.asList(
                new Apple(1, "red", 500, "湖南"),
                new Apple(2, "red", 100, "天津"),
                new Apple(3, "green", 300, "湖南"),
                new Apple(4, "green", 200, "天津"),
                new Apple(5, "green", 100, "湖南")
        );
    }

    @Test
    public void sum() {
        Integer sum = appleStore.stream().collect(summingInt(Apple::getWeight));
        System.out.println(sum);
    }

    @Test
    public void average() {
        Double average = appleStore.stream().collect(averagingInt(Apple::getWeight));
        System.out.println(average);
    }

    @Test
    public void reduce() {
        Integer sum = appleStore.stream().collect(reducing(0, Apple::getWeight, (a, b) -> a + b));
        System.out.println(sum);
    }
}
