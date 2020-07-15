package core.java.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    private static final List<Apple> appleStore = Arrays.asList(
            new Apple(1, "red", 500, "湖南"),
            new Apple(2, "red", 100, "天津"),
            new Apple(3, "green", 300, "湖南"),
            new Apple(4, "green", 200, "天津"),
            new Apple(5, "green", 100, "湖南")
    );
    public static void main(String[] args) {
        appleStore.stream().filter(apple -> apple.getWeight() > 100)
                .peek(apple -> System.out.println("通过第1层筛选 " + apple))
                .filter(apple -> "green".equals(apple.getColor()))
                .peek(apple -> System.out.println("通过第2层筛选 " + apple))
                .filter(apple -> "湖南".equals(apple.getBirthplace()))
                .peek(apple -> System.out.println("通过第3层筛选 " + apple))
                .collect(Collectors.toList());
    }
}
