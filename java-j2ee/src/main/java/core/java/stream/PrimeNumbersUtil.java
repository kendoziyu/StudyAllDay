package core.java.stream;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumbersUtil {

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(new PrimeNumbersCollector());
    }

    public static Boolean isPrime(Integer candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(p -> candidate % p == 0);
    }

    private static <A> List<A> takeWhile(List<A> list, Predicate<A> pred) {
        int i = 0;
        for (A item : list) {
            if (!pred.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(Collectors.partitioningBy(candidate -> isPrime(candidate)));
    }
}
