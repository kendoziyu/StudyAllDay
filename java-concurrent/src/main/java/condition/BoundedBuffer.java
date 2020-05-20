package condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (count == items.length) {
                notFull.await();
            }
            // 执行
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            // 通知
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (count == 0) {
                notEmpty.await();
            }
            // 执行
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            // 通知
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final BoundedBuffer buffer = new BoundedBuffer();
        Runnable consumer = new Runnable() {
            public void run() {
                try {
                    Object item = buffer.take();
                    System.out.println(Thread.currentThread().getName() + " consume one:" + item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable producer = new Runnable() {
            public void run() {
                try {
                    for (int i = 1;; i++) {
                        // 1分钟生产一个
                        Thread.sleep(60000);
                        buffer.put(i);
                        System.out.println(Thread.currentThread().getName() + " produce one:" + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(consumer, "consumer" + i).start();
        }

        new Thread(producer, "producer").start();

    }
}
