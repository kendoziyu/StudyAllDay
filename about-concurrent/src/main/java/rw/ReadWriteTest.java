package rw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/5 0005 <br>
 */
public class ReadWriteTest {

    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public static void main(String[] args) {
        final boolean testLock = true;
        final HashMap<Integer, String> map = new HashMap<Integer, String>();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        // 3 写
        for (int i = 0; i < 3; i++) {
            final int temp = i;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ReentrantReadWriteLock.WriteLock writeLock = null;
                    if (testLock) {
                        writeLock = readWriteLock.writeLock();
                        writeLock.lock();
                    }
                    System.out.println(Thread.currentThread().getName() + " write");
                    map.put(temp, String.valueOf(temp));
                    System.out.println(Thread.currentThread().getName() + " write ok");
                    if (testLock) {
                        writeLock.unlock();
                    }
                }
            }, String.valueOf(i));
            threads.add(thread);
        }

        for (int i = 0; i < 3; i++) {
            final int temp = i;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ReentrantReadWriteLock.ReadLock readLock = null;
                    if (testLock) {
                        readLock = readWriteLock.readLock();
                        readLock.lock();
                    }
                    System.out.println(Thread.currentThread().getName() + " read");
                    String value = map.get(temp);
                    System.out.println(Thread.currentThread().getName() + " read " + value);
                    if (testLock) {
                        readLock.unlock();
                    }
                }
            }, String.valueOf(i));
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
