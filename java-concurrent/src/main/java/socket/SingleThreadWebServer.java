package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述: 单线程网络服务 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/6 0006 <br>
 */
public class SingleThreadWebServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(8098);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) throws InterruptedException {
        System.out.println("yes commander");
        Thread.sleep(15000);
    }
}
