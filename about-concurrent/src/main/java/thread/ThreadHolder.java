package thread;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/5 0005 <br>
 */
public class ThreadHolder {

    Thread exclusiveOwnerThread;

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }
}
