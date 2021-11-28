package org.geekbang.thinking.in.spring.verify.definition;

import org.geekbang.thinking.in.spring.verify.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.verify.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonBeanRegistrationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册外部单例对象

        UserFactory userFactory = new DefaultUserFactory();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

        //注册外部单例对象
        beanFactory.registerSingleton("userFactory", userFactory);

        // 启动应用上下文
        applicationContext.refresh();

        UserFactory userFactoryByLookup = applicationContext.getBean("userFactory", UserFactory.class);

        System.out.println(userFactory == userFactoryByLookup);

        // 非延迟初始化在 Spring 应用上下文启动完成后，被初始化
        applicationContext.close();

    }
}
