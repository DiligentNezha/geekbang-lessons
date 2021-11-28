package org.geekbang.thinking.in.spring.aop.verify;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/19
 */
public class ClassLoadingDemo {
    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

//        System.out.println(contextClassLoader);
        ClassLoader parentClassLoader = contextClassLoader;

        do {
            System.out.println(parentClassLoader);
            parentClassLoader = parentClassLoader.getParent();
        } while (parentClassLoader != null);

//        while (true) {
//            parentClassLoader = parentClassLoader.getParent();
//            if (parentClassLoader == null) {
//                break;
//            }
//            System.out.println(parentClassLoader);
//        }
    }
}
