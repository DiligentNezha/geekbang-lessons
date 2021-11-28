package org.geekbang.thinking.in.spring.aop.verify;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/26
 */
public class TargetFilterDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        String targetClassName = "org.geekbang.thinking.in.spring.aop.verify.EchoService";
        // 获取当前线程 ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 获取目标类
        Class<?> targetClass = classLoader.loadClass(targetClassName);
        //获取目标方法
        Method targetMethod = ReflectionUtils.findMethod(targetClass, "echo", String.class);
        System.out.println(targetMethod);

        // 查找方法 throws 类型为 NullPointerException

        ReflectionUtils.doWithMethods(targetClass, method -> {
            System.out.println("仅抛出 NullPointerException 方法为:" + method);
        }, method -> {
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            return exceptionTypes.length == 1 && NullPointerException.class.equals(exceptionTypes[0]);
        });

    }
}
