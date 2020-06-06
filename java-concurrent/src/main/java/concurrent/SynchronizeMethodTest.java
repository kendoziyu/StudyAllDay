package concurrent;

public class SynchronizeMethodTest {

    private static int threadId = 0;

    private static boolean consumed = false;

    public void syncMethod() {
        if (consumed) {
            return;
        }
        synchronized (SynchronizeMethodTest.class) {
            System.out.println(Thread.currentThread() + " enter critical region");
            if (consumed) {
                return;
            }
            System.out.println(Thread.currentThread() + " get the job");
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread() + " is working");
                }
            }, "worker" + (++threadId)).start();

            consumed = true;
        }
    }


    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    SynchronizeMethodTest test = new SynchronizeMethodTest();
                    test.syncMethod();
                }
            }, "test-" + i).start();
        }

    }
}
