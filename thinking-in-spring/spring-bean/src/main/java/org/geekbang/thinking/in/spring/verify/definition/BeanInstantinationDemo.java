package org.geekbang.thinking.in.spring.verify.definition;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInstantinationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/verify/bean-instantiation-context.xml");

        User user = beanFactory.getBean("user-by-static-method", User.class);
        User userByInstanceMethod = beanFactory.getBean("user-by-instance-method", User.class);
        User userByFactoryBean = beanFactory.getBean("user-by-factory-bean", User.class);

        System.out.println(user);
        System.out.println(userByInstanceMethod);
        System.out.println(userByFactoryBean);
    }
}
