package cn.skilled.peon.agent;

import java.lang.instrument.Instrumentation;

/**
 * 描述:  监控代理<br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/2 0002 <br>
 */
public class MonitorAgent {

    public static void premain(String arg, Instrumentation instrumentation) {
        System.out.println("javaagent");
    }
}
