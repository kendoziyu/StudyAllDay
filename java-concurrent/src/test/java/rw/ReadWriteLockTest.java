package rw;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述:  读写锁测试 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/5 0005 <br>
 */
public class ReadWriteLockTest {

    /**
     * 1.同一个ReentrantReadWriteLock对象，读锁和写锁在同一个队列中排队
     * 2.与ReentrantLock不同，头结点中的thread不为null
     */
    @Test
    public void writeLockAndReadLockQueuedTogether() {
        final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        Runnable writeTask = new Runnable() {
            public void run() {
                ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + " write");
                try {
                    Thread.sleep(200000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " write ok");
                writeLock.unlock();
            }
        };
        for (int i = 1; i <= 2; i++) {
            Thread thread = new Thread(writeTask, "w" + i);
            threads.add(thread);
        }

        Runnable readTask = new Runnable() {
            public void run() {
                ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + " read");
                System.out.println(Thread.currentThread().getName() + " read ok");
                readLock.unlock();
            }
        };
        for (int i = 1; i <= 3; i++) {
            Thread readThread = new Thread(readTask, "r" + i);
            threads.add(readThread);
        }

        for (Thread t : threads) {
            t.start();
        }
    }
}
