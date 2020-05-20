package condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述: 利用Condition实现多线程顺序执行 A->B->C <br>
 *
 * @author skilled-peon <br>
 * @since 2020/5/6 0006 <br>
 */
public class ConditionTest {

    private ReentrantLock lock = new ReentrantLock(true);
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int number = 1;

    private void printA() {
        lock.lock();
        try {
            // 判断 => 执行 => 通知
            while (number != 1)
                condition1.await();

            System.out.println(Thread.currentThread().getName() + "=> AAAAA");
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    private void printB() {
        lock.lock();
        try {
            // 判断 => 执行 => 通知
            while (number != 2)
                condition2.await();

            System.out.println(Thread.currentThread().getName() + "=> BB");
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    private void printC() {
        lock.lock();
        try {
            // 判断 => 执行 => 通知
            while (number != 3)
                condition3.await();

            System.out.println(Thread.currentThread().getName() + "=> CCC");
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    /**
     * 多线程顺序执行 A->B->C
     * @param args 参数
     */
    public static void main(String[] args) {
        final ConditionTest condition = new ConditionTest();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    condition.printA();
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    condition.printB();
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    condition.printC();
                }
            }
        }, "C").start();
    }
}
