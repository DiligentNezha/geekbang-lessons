package org.geekbang.thinking.in.spring.verify.definition;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.geekbang.thinking.in.spring.verify.factory.DefaultUserFactory;
import org.geekbang.thinking.in.spring.verify.factory.UserFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类作为配置类
        applicationContext.register(BeanInitializationDemo.class);

        // 启动应用上下文
        applicationContext.refresh();
        // 非延迟初始化在 Spring 应用上下文启动完成后，被初始化
        System.out.println("Spring 应用上下文已启动....");
        System.out.println(applicationContext.getBean(UserFactory.class));

        // 关闭应用上下文
        System.out.println("Spring 应用上下文准备关闭");
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
//    @Lazy
    public DefaultUserFactory userFactory() {
        return new DefaultUserFactory();
    }

}
