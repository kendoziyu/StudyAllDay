package example;

/**
 * Created by on 2019/4/20.
 */
public class Worker implements Runnable {

    private int id;

    public Worker(int id) {
        this.id = id;
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 执行任务 " + id);
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " 完成任务 " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}