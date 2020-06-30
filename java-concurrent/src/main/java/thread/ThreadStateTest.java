package thread;

import org.junit.Assert;
import org.junit.Test;

/**
 * 描述: 线程状态测试 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/28 0028 <br>
 */
public class ThreadStateTest {

    /**
     * 线程被创建，然后因为 run 方法正常退出被终止
     * @throws InterruptedException
     */
    @Test
    public void newThenNormalTerminated() throws InterruptedException {
        Thread t1 = new Thread();
        Assert.assertEquals(Thread.State.NEW, t1.getState());
        t1.start();
        Assert.assertEquals(Thread.State.RUNNABLE, t1.getState());
        Thread.sleep(1);
        Assert.assertEquals(Thread.State.TERMINATED, t1.getState());
    }

    /**
     * 线程因为未被捕获的异常终止了 run 方法而意外死亡
     * @throws InterruptedException
     */
    @Test
    public void exceptionTerminated() throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                // 线程运行内容
                throw new RuntimeException("test exception");
            }
        };
        Thread t1 = new Thread(task);
        Assert.assertEquals(Thread.State.NEW, t1.getState());
        t1.start();
        Assert.assertEquals(Thread.State.RUNNABLE, t1.getState());
        Thread.sleep(5);
        Assert.assertEquals(Thread.State.TERMINATED, t1.getState());
    }

    /**
     * 线程因为未被捕获的异常终止了 run 方法而意外死亡
     * @throws InterruptedException
     */
    @Test
    public void synchronizeBlocked() throws InterruptedException {
        Runnable sync = new Runnable() {
            final Object object = new Object();
            @Override
            public void run() {
                synchronized (object) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t1 = new Thread(sync);
        Thread t2 = new Thread(sync);
        t1.start();
        t2.start();
        System.out.println(t1.getState());
        System.out.println(t2.getState());
        Thread.sleep(5);
        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }

    /**
     * 通过 {@link Object#wait()} 让线程变为 WAITING
     * @throws InterruptedException
     */
    @Test
    public void objectWait() throws InterruptedException {
        final Object object = new Object();
        Runnable waiter = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("wait signal");
                    synchronized (object) {
                        object.wait();
                    }
                    System.out.println("signal received");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable notify = new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    object.notify();
                }
                System.out.println("send signal");
            }
        };
        Thread t1 = new Thread(waiter);
        Thread t2 = new Thread(notify);
        t1.start();
        Thread.sleep(1);
        // 等待通知
        Assert.assertEquals(Thread.State.WAITING, t1.getState());
        Thread.sleep(10);
        System.out.println("==== 10 millis later ====");
        t2.start();
    }

}
