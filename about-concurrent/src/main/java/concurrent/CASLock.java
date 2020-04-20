package concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 描述: 自定义锁 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/16 0016 <br>
 */
public class CASLock {

    volatile int status = 0;

    public void lock() {
        while(!compareAndSet(0, 1)) {
             Thread.yield();
            // Thread.sleep(1000);
            // LockSupport.park()
        }
    }

    public void unlock() {
        status = 0;
    }

    private boolean compareAndSet(int except, int update) {
        // cas 操作，修改 status 成功返回 true，否则返回 false
        if (except == status) {
            status = update;
            return true;
        }
        return false;
    }
}
