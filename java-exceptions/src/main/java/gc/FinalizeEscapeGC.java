package gc;

/**
 * 描述:  在finalize()方法中重新与引用链上的任何一个对象建立关联<br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/24 0024 <br>
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed !");
        SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        /*
         * 对象的第一次成功拯救自己
         */
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法的优先级很低，所以过0.5秒可以等待它完成
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }

        /*
         * 这段代码与上面完全相同，但是这次自救却失败了
         */
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法的优先级很低，所以过0.5秒可以等待它完成
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }
    }
}
