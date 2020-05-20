package thread;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/5 0005 <br>
 */
public class AOSTest {
    public static void main(String[] args) {
        final ThreadHolder holder = new ThreadHolder();
        Runnable task = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " begin");
                label:
                while (true) {
                    // 如果不加这个for循环,不能正常获取到被设置为null的exclusiveOwnerThread
                    // 除非设置 volatile 关键字
                    for (int i = 0; i < 2; i++) {
                        if (holder.getExclusiveOwnerThread() == null) {
                            holder.setExclusiveOwnerThread(Thread.currentThread());
                            System.out.println("set " + Thread.currentThread().getName() + " success");
                            break label;
                        }
                    }
                }
                System.out.println(Thread.currentThread().getName() + " sleep");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " awake");
                if (holder.getExclusiveOwnerThread() == Thread.currentThread()) {
                    holder.setExclusiveOwnerThread(null);
                    System.out.println("release " + Thread.currentThread().getName());
                }
                System.out.println(Thread.currentThread().getName() + " end");
            }
        };

        for (int i = 1; i <= 3; i++) {
            new Thread(task, "t" + i).start();
        }
    }
}
