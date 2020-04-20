package helloworld;

import java.util.concurrent.locks.LockSupport;

/**
 * 描述: LockSupport 是对 UnSafe 的封装，我们使用它来实现 park <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/16 0016 <br>
 */
public class ParkTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                testPark();
            }
        }, "t1");

        t1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------ main wakeup after sleep ------");
        LockSupport.unpark(t1);
        System.out.println("------ main end ------");

    }

    public static void testPark() {
        System.out.println(Thread.currentThread().getName() + " is going to park.");
        LockSupport.park();
        System.out.println(Thread.currentThread().getName() + " end!");
    }
}
