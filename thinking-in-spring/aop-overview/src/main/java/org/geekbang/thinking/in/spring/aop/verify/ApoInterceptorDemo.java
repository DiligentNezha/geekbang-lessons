package org.geekbang.thinking.in.spring.aop.verify;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/26
 */
public class ApoInterceptorDemo {

    public static void main(String[] args) {
        // 前置模式
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{EchoService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (EchoService.class.isAssignableFrom(method.getDeclaringClass())) {
                    BeforeInterceptor beforeInterceptor = new BeforeInterceptor() {
                        @Override
                        public Object before(Object proxy, Method method, Object[] args) {
                            return System.currentTimeMillis();
                        }
                    };
                    long start = 0L;
                    long end = 0L;
                    String result = null;
                    try {
                        start = (long) beforeInterceptor.before(proxy, method, args);
                        DefaultEchoService echoService = new DefaultEchoService();

                        result = echoService.echo((String) args[0]);
                        AfterReturnInterceptor afterReturnInterceptor = new AfterReturnInterceptor() {
                            @Override
                            public Object after(Object proxy, Method method, Object[] args, Object returnResult) {
                                return System.currentTimeMillis();
                            }
                        };
                        end = (long) afterReturnInterceptor.after(proxy, method, args, result);
                    } catch (Throwable throwable) {
                        ExceptionInterceptor exceptionInterceptor = new ExceptionInterceptor() {
                            @Override
                            public void intercept(Object proxy, Method method, Object[] args, Throwable throwable) {
                                throwable.printStackTrace();
                                try {
                                    throw throwable;
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        exceptionInterceptor.intercept(proxy, method, args, throwable);
                    } finally {
                        FinallyInterceptor finallyInterceptor = new TimeFinallyInterceptor(start, end);
                        long cost = (long) finallyInterceptor.finalize(proxy, method, args, result);
                        System.out.println("方法执行的实现:" + cost + ".ms");
                    }
                    return result;
                }
                return null;
            }
        });

        ((EchoService) proxy).echo("hello");
    }
}
