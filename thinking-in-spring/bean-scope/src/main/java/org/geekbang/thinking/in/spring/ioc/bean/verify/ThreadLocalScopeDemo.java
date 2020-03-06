package org.geekbang.thinking.in.spring.ioc.bean.verify;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/6
 */
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public User user() {
        return createUser();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(ThreadLocalScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope());
        });

        applicationContext.refresh();

        scopedBeansByLookup(applicationContext);

        applicationContext.close();
    }

    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    private static void scopedBeansByInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo bean = applicationContext.getBean(BeanScopeDemo.class);
//        System.out.println("beanScopeDemo.singletonUser:" + bean.singletonUser);
//        System.out.println("beanScopeDemo.singletonUser1:" + bean.singletonUser1);
//
//        System.out.println("beanScopeDemo.prototypeUser:" + bean.prototypeUser);
//        System.out.println("beanScopeDemo.prototypeUser1:" + bean.prototypeUser1);
//        System.out.println("beanScopeDemo.prototypeUser2:" + bean.prototypeUser2);
//
//        System.out.println("beanScopeDemo.users:" + bean.users);
    }

    private static void scopedBeansByLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> {
                User user = applicationContext.getBean("user", User.class);
                System.out.printf("Thread Id:%d, user:%s \n", Thread.currentThread().getId(), user);
            });
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
