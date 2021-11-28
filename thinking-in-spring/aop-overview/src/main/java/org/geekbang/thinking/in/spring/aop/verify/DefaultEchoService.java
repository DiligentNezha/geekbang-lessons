package org.geekbang.thinking.in.spring.aop.verify;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/20
 */
public class DefaultEchoService implements EchoService{


    @Override
    public String echo(String msg) {
        System.out.println("DefaultEchoService#echo 方法执行");
        return "[ECHO] " + msg;
    }
}
