package concurrent;

import java.util.Queue;

/**
 * 描述: 基于LockSupport.park() 实现的一把锁<br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/16 0016 <br>
 */
public class ParkLock {

    volatile int status = 0;
    Queue<Thread> waitQueue;

    public void lock() {
        while(!compareAndSet(0, 1)) {
            // yield
            // sleep
            park();
        }
    }

    public void unlock() {
        status = 0;
        lockNotify();
    }

    void park() {
        // 将当前线程放入等待队列
        waitQueue.add(Thread.currentThread());
        // 释放cpu
        releaseCpu();
    }

    private void releaseCpu() {

    }

    void lockNotify() {
        Thread header = waitQueue.poll();
        // 唤醒等待的线程
        unpark(header);
    }

    void unpark(Thread t) {

    }



    private boolean compareAndSet(int except, int update) {
        return false;
    }
}
