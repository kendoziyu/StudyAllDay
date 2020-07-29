package core.java.stream;


import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class PrimeNumbersCollectorTest {

    @Test
    public void test1() {
        long fastest = Integer.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            PrimeNumbersUtil.partitionPrimesWithCustomCollector(1000000);
            long duration = (System.nanoTime() - start) / 1000000;
            if (duration < fastest) fastest = duration;
        }
        System.out.println(fastest);
    }

    @Test
    public void test2() {
        long fastest = Integer.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            PrimeNumbersUtil.partitionPrimes(1000000);
            long duration = (System.nanoTime() - start) / 1000000;
            if (duration < fastest) fastest = duration;
        }
        System.out.println(fastest);
    }
}
