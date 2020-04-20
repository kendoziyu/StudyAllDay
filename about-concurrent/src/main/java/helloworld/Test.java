package helloworld;

import concurrent.CASLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述: 测试无锁情况下的同步 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/16 0016 <br>
 */
public class Test {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Runnable task = new Runnable() {
            public void run() {
                noLockTest();
                testSync();
            }
        };
        Thread t1 = new Thread(task, "t1");
        Thread t2 = new Thread(task, "t2");

        t1.start();
        t2.start();
    }

    public static void noLockTest() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testSync() {
        lock.lock();
        System.out.println("sync:" + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
