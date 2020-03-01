package org.geekbang.thinking.in.spring.ioc.verify.container;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
@Configuration
public class AnnotationApplicationContextAsIoCContainerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类作为配置类
        applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);

        // 启动应用上下文
        applicationContext.refresh();

        lookupCollectionType(applicationContext);

        // 关闭应用上下文
        applicationContext.close();
    }

    private static void lookupCollectionType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有的 User 集合对象:" + users);
        }
    }

    @Bean
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("不偷懒的小哪吒");
        return user;
    }

}
