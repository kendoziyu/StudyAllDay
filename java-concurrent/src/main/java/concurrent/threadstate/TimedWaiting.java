package concurrent.threadstate;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * 描述: 测试线程进入 TIME_WAITING 状态 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/19 0019 <br>
 */
public class TimedWaiting {
    static Executor executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        SleepTask t1 = new SleepTask();
        WaitTask t2 = new WaitTask();
        JoinTask t3 = new JoinTask();
        ParkTask t4 = new ParkTask();
        executor.execute(t1);
        executor.execute(t2);
        executor.execute(t3);
        executor.execute(t4);
    }

    /**
     * 执行随眠的线程
     */
    public static class SleepTask implements Runnable {

        public void run() {
            try {
                Thread.sleep(1000 * 60 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行 wait 的线程
     */
    public static class WaitTask implements Runnable {

        public void run() {
            try {
                wait(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行 join 的线程
     */
    public static class JoinTask implements Runnable {

        public void run() {
            try {
                Thread.currentThread().join(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ParkTask implements Runnable {

        public void run() {
            LockSupport.parkNanos(1000 * 1000 * 60);
        }
    }

}
