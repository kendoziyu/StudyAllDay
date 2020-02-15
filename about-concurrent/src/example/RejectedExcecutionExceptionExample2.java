package example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class RejectedExcecutionExceptionExample2 {

    private static ScheduledExecutorService threads = Executors.newScheduledThreadPool(1);

    private static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);

    private static final ExecutorService pool3 = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        Worker tasks[] = new Worker[6];
        for (int i = 0; i < 6; i++) {
            tasks[i] = new Worker(i);
            System.out.println("提交任务: " + tasks[i] + ", " + i);
            threads.execute(tasks[i]);
            threadPool.execute(tasks[i]);
            pool3.execute(tasks[i]);
        }
        System.out.println("主线程结束");
    }
}
