package helloworld;

import concurrent.CASLock;
import concurrent.ParkLock;

/**
 * 描述:  自制自旋锁测试 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/19 0019 <br>
 */
public class CustomCASTest {

//    static CASLock lock = new CASLock();
    static ParkLock lock = new ParkLock();

    public static void main(String[] args) {
        Runnable task = new Runnable() {
            public void run() {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " -> begin");
                long sum = 0;
                for (int x = 0; x < 100000; x++) {
                    for (int y = 0; y < 100000; y++) {
                            sum += x + y;
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -> end " + sum);
                lock.unlock();
            }
        };
        Thread t1 = new Thread(task, "t1");
        Thread t2 = new Thread(task, "t2");
        t1.start();
        t2.start();
    }
}
