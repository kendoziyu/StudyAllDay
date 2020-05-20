package rw;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述: 写锁测试 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/5 0005 <br>
 */
public class WriteLockTest {

    @Test
    public void fairLockCompete() {
        final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        Runnable task = new Runnable() {
            public void run() {
                ReentrantReadWriteLock.WriteLock writeLock;
                writeLock = readWriteLock.writeLock();
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + " write");
                try {
                    Thread.sleep(2000000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " write ok");
                writeLock.unlock();
            }
        };
        // 写锁排队
        for (int i = 0; i < 3; i++) {
            new Thread(task, String.valueOf(i)).start();
        }
    }

    @Test
    public void nonFairLockCompete() {
        final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
        Runnable task = new Runnable() {
            public void run() {
                ReentrantReadWriteLock.WriteLock writeLock;
                writeLock = readWriteLock.writeLock();
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + " write");
                try {
                    Thread.sleep(2000000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " write ok");
                writeLock.unlock();
            }
        };
        // 写锁排队
        for (int i = 0; i < 3; i++) {
            new Thread(task, String.valueOf(i)).start();
        }
    }
}
