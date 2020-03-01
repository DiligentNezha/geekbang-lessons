package org.geekbang.thinking.in.spring.verify.definition;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAliasDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/verify/bean-definitions-context.xml");
        User user = beanFactory.getBean("user", User.class);
        User xiaomageUser = beanFactory.getBean("xiaomage-user", User.class);
        System.out.println(user == xiaomageUser);

    }
}
