package gc;

/**
 * 描述: 年轻代回收 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/24 0024 <br>
 */
public class YoungGenGCTest {

    /**
     * -Xms20m -Xmx20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails
     * @param args
     */
    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 10; i++) {
            b = new byte[1 * 1024 * 1024];
        }
    }
}
