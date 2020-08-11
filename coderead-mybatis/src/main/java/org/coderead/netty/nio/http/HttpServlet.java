package org.coderead.netty.nio.http;

public class HttpServlet {

    public void doGet(HttpRequest request, HttpResponse response) {
        System.out.println(request.url);
        response.body = "<html><h1>Hello World!</h1></html>";
    }

    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
