package org.geekbang.thinking.in.spring.aop.verify;

import org.geekbang.thinking.in.spring.aop.overview.ProxyEchoService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/20
 */
public class JdkDynamicProxyDemo {

    public static void main(String[] args) {
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{EchoService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (EchoService.class.isAssignableFrom(method.getDeclaringClass())) {
                    long start = System.currentTimeMillis();
//                    String result = echoService.echo((String) args[0]);
//                    String result = "proxy: " + args[0];
                    String result = (String) method.invoke(new DefaultEchoService(), args[0]);
                    long costTime = System.currentTimeMillis() - start;
                    System.out.println("echo 方法执行时间: " + costTime + " ms.");
                    return result;
                }
                return null;
            }
        });
        EchoService echoService = (EchoService) proxy;
        System.out.println(echoService.echo("hello world"));
    }
}
