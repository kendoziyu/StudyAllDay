package core.java.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Test
    public void sumGroupBy() {
        Map<String, Integer> collect = appleStore.stream().collect(groupingBy(Apple::getColor, summingInt(Apple::getWeight)));
        collect.forEach((color, weight) -> System.out.println(color + " apple weight: " + weight));
    }

    @Test
    public void flatMap() {
        Stream<int[]> stream = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );
        stream.limit(5).forEach(a -> System.out.println(a[0] + " " + a[1] + " " + a[2]));
    }

    @Test
    public void equalFlatMap() {
        List<int[]> resultList = new ArrayList<>();
        for (int a = 1; a <= 100; a++) {
            for (int b = a; b <= 100; b++) {
                double c = Math.sqrt(a * a + b * b);
                if (c % 1 == 0) {
                    resultList.add(new int[]{a, b, (int) c});
                }
            }
        }

        int size = resultList.size();
        for (int i = 0; i < size && i < 5; i++) {
            int[] a = resultList.get(i);
            System.out.println(a[0] + " " + a[1] + " " + a[2]);
        }
    }
}
