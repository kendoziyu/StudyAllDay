package helloworld;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 描述: 写一个死循环，然后让线程去执行这个死循环，然后通过 jstack 命令观察线程调用栈 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/18 0018 <br>
 */
public class JStackTest {

    public static Executor executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        Task task1 = new Task();
        Task task2 = new Task();
        executor.execute(task1);
        executor.execute(task2);
    }

    static class Task implements Runnable {

        public void calculate() {
            int i = 0;
            while (true) {
                i++;
            }
        }

        public void run() {
            calculate();
        }
    }
}
