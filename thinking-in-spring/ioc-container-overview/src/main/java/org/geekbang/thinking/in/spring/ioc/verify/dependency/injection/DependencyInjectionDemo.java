package org.geekbang.thinking.in.spring.ioc.verify.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.geekbang.thinking.in.spring.ioc.verify.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

public class DependencyInjectionDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/verify/dependency-injection-context.xml");
        // 依赖来源 1：自定义 bean
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);

        // 依赖来源 2：依赖注入（内建依赖）
        System.out.println(userRepository.getBeanFactory());

        ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();

        System.out.println(objectFactory.getObject() == beanFactory);

        // 依赖来源 3：容器内建 bean
        Environment environment = beanFactory.getBean(Environment.class);

        System.out.println(environment);
        // 依赖查找（报错）
        System.out.println(beanFactory.getBean(BeanFactory.class));

    }

    private static void whoIsIocContainer(UserRepository userRepository, BeanFactory beanFactory) {
        // 这个表达式为什么不成立？
        System.out.println(userRepository.getBeanFactory() == beanFactory);

    }
}
