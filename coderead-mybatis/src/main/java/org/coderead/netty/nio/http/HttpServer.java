package org.coderead.netty.nio.http;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HttpServer {

    final int port;
    private final Selector selector;
    private final HttpServlet servlet;
    ExecutorService service;

    /**
     * 初始化
     * @param port
     * @param servlet
     * @throws IOException
     */
    public HttpServer(int port, HttpServlet servlet) throws IOException {
        this.port = port;
        this.servlet = servlet;
        this.service = Executors.newFixedThreadPool(5);
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(80));
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 启动
     */
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    poll(selector);
                } catch (IOException e) {
                    System.out.println("服务器异常退出...");
                    e.printStackTrace();
                }
            }
        }, "Selector-IO").start();
    }

    public static void main(String[] args) throws IOException {
        try {
            HttpServer server = new HttpServer(80, new HttpServlet());
            server.start();
            System.out.println("服务器启动成功, 您现在可以访问 http://localhost:" + server.port);
        } catch (IOException e) {
            System.out.println("服务器启动失败...");
            e.printStackTrace();
        }
        System.in.read();
    }

    /**
     * 轮询键集
     * @param selector
     * @throws IOException
     */
    private void poll(Selector selector) throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept(key);
                } else if (key.isReadable()) {
                    handleRead(key);
                } else if (key.isWritable()) {
                    handleWrite(key);
                }
                iterator.remove();
            }
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 1. 读取数据
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        read(socketChannel, out);
        // 坑：浏览器空数据
        if (out.size() == 0) {
            System.out.println("关闭连接："+ socketChannel.getRemoteAddress());
            socketChannel.close();
            return;
        }
        // 2. 解码
        final HttpRequest request = decode(out.toByteArray());
        // 3. 业务处理
        service.submit(new Runnable() {
            @Override
            public void run() {
                HttpResponse response = new HttpResponse();
                if ("get".equalsIgnoreCase(request.method)) {
                    servlet.doGet(request, response);
                } else if ("post".equalsIgnoreCase(request.method)) {
                    servlet.doPost(request, response);
                }
                // 获得响应
                key.interestOps(SelectionKey.OP_WRITE);
                key.attach(response);
                // 坑：异步唤醒
                key.selector().wakeup();
//                socketChannel.register(key.selector(), SelectionKey.OP_WRITE, response);
            }
        });

    }

    /**
     * 从缓冲区读取数据并写入 {@link ByteArrayOutputStream}
     * @param socketChannel
     * @param out
     * @throws IOException
     */
    private void read(SocketChannel socketChannel, ByteArrayOutputStream out) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(buffer) > 0) {
            buffer.flip(); // 切换到读模式
            out.write(buffer.array());
            buffer.clear(); // 清理缓冲区
        }
    }

    /**
     * 解码 Http 请求报文
     * @param array
     * @return
     */
    private HttpRequest decode(byte[] array) {
        try {
            HttpRequest request = new HttpRequest();
            ByteArrayInputStream inStream = new ByteArrayInputStream(array);
            InputStreamReader reader = new InputStreamReader(inStream);
            BufferedReader in = new BufferedReader(reader);

            // 解析起始行
            String firstLine = in.readLine();
            System.out.println(firstLine);
            String[] split = firstLine.split(" ");
            request.method = split[0];
            request.url = split[1];
            request.version = split[2];

            // 解析首部
            Map<String, String> headers = new HashMap<>();
            while (true) {
                String line = in.readLine();
                // 首部以一个空行结束
                if ("".equals(line.trim())) {
                    break;
                }
                String[] keyValue = line.split(":");
                headers.put(keyValue[0], keyValue[1]);
            }
            request.headers = headers;

            // 解析请求主体
            CharBuffer buffer = CharBuffer.allocate(1024);
            CharArrayWriter out = new CharArrayWriter();
            while (in.read(buffer) > 0) {
                buffer.flip();
                out.write(buffer.array());
                buffer.clear();
            }
            request.body = out.toString();
            return request;
        } catch (Exception e) {
            System.out.println("解码 Http 失败");
            e.printStackTrace();
        }
        return null;
    }

    private void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        HttpResponse response = (HttpResponse) key.attachment();

        // 编码
        byte[] bytes = encode(response);
        channel.write(ByteBuffer.wrap(bytes));

        key.interestOps(SelectionKey.OP_READ);
        key.attach(null);
    }

    /**
     * http 响应报文编码
     * @param response
     * @return
     */
    private byte[] encode(HttpResponse response) {
        StringBuilder builder = new StringBuilder();
        if (response.code == 0) {
            response.code = 200; // 默认成功
        }
        // 响应起始行
        builder.append("HTTP/1.1 ").append(response.code).append(" ").append(Code.msg(response.code)).append("\r\n");
        // 响应头
        if (response.body != null && response.body.length() > 0) {
            builder.append("Content-Length:").append(response.body.length()).append("\r\n");
            builder.append("Content-Type:text/html\r\n");
        }
        if (response.headers != null) {
            String headStr = response.headers.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
                    .collect(Collectors.joining("\r\n"));
            if (!headStr.isEmpty()) {
                builder.append(headStr).append("\r\n");
            }
        }
        // 首部以一个空行结束
        builder.append("\r\n");
        if (response.body != null) {
            builder.append(response.body);
        }
        return builder.toString().getBytes();
    }

    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        Selector selector = key.selector();

        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
}
