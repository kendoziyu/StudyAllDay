package org.coderead.netty.nio.http;

import java.util.Map;

public class HttpRequest {
    String method; // 请求方法
    String url;    // 请求地址
    String version; // http版本
    Map<String, String> headers; // 请求头
    String body; // 请求主体
}
