package rw;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述: 读锁测试 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/5 0005 <br>
 */
public class ReadLockTest {

    /**
     * 公平锁竞争
     * 1. 如果线程交替执行，那么只用到firstReader和firstReaderHoldCount；
     * firstReader是上次将共享计数从0更改为1的唯一线程，并且此后未释放读取锁；
     */
    @Test
    public void fairLockCompete() {
        final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        Runnable task = new Runnable() {
            public void run() {
                ReentrantReadWriteLock.ReadLock readLock;
                readLock = readWriteLock.readLock();
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + " read");
                try {
                    Thread.sleep(2000000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " read ok");
                readLock.unlock();
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(task, String.valueOf(i)).start();
        }
    }

    /**
     * 非公平锁竞争
     */
    @Test
    public void nonFairLockCompete() {
        final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
        Runnable task = new Runnable() {
            public void run() {
                ReentrantReadWriteLock.ReadLock readLock;
                readLock = readWriteLock.readLock();
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + " read");
                try {
                    Thread.sleep(2000000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " read ok");
                readLock.unlock();
            }
        };
        // 写锁排队
        for (int i = 0; i < 3; i++) {
            new Thread(task, String.valueOf(i)).start();
        }
    }

}
