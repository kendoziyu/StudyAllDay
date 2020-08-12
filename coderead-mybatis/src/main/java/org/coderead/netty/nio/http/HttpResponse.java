package org.coderead.netty.nio.http;

import java.util.Map;

public class HttpResponse {

    String version; // http版本
    int code;       // 响应码
    String status;  // 状态信息
    Map<String, String> headers; // 响应头
    String body;   // 响应数据
}
