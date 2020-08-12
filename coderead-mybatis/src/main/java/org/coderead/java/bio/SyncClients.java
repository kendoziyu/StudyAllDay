package org.coderead.java.bio;

import java.net.Socket;

/**
 * 描述:  <br>
 *
 * @author: kendo ziyu <br>
 * @date: 2020/8/4 0004 <br>
 */
public class SyncClients {

    private static Socket[] clients = new Socket[100];
    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= clients.length; i++) {
            clients[i-1] = new Socket("127.0.0.1", 8080);
            System.out.println("client connection:" + i);
        }
        System.in.read();
    }
}
