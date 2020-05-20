import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    private ExecutorService executorService = new ThreadPoolExecutor(6 , 6, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private static int num = 0;

    private static ThreadLocal<Integer> threadLocalNum = new ThreadLocal<Integer>();

    @Test
    public void unsafe() {
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread() + ",start:" + num);
                    for (int j = 0; j < 2; j++) {
                        num++;
                    }
                    System.out.println(Thread.currentThread() + ",end:" + num);
                }
            });
        }
        System.out.println(Thread.currentThread() + ",all:" + num);
    }


    @Test
    public void safe() {
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    threadLocalNum.set(0);
                    System.out.println(Thread.currentThread() + ",start:" + threadLocalNum.get());
                    for (int j = 0; j < 2; j++) {
                        Integer temp = threadLocalNum.get();
                        threadLocalNum.set(++temp);
                    }
                    System.out.println(Thread.currentThread() + ",end:" + threadLocalNum.get());
                }
            });
        }
        System.out.println(Thread.currentThread() + ",all:" + threadLocalNum.get());
    }

    static class LocalVariable {
        private Long[] a = new Long[4 * 1024 * 1024];
    }

    private static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<LocalVariable>();

    /**
     * jvm参数 -Xms100m -Xmx100m
     */
    @Test
    public void outOfMemory() throws InterruptedException{

        for (int i = 0; i < 5000; ++i) {
            executorService.execute(new Runnable() {
                public void run() {
                    localVariable.set(new LocalVariable());
                    System.out.println("use local variable:" + localVariable.get());
//                    localVariable.remove();
                }
            });
            Thread.sleep(1000);
        }
        System.out.println("pool execute over");

    }
}
