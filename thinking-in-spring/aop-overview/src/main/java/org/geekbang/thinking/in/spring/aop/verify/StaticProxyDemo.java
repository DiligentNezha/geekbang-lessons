package org.geekbang.thinking.in.spring.aop.verify;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/20
 */
public class StaticProxyDemo {
    public static void main(String[] args) {
        new ProxyEchoService(new DefaultEchoService()).echo("good");
    }
}
