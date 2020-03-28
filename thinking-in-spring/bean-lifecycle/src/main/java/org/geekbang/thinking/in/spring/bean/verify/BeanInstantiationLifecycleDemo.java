package org.geekbang.thinking.in.spring.bean.verify;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/13
 */
public class BeanInstantiationLifecycleDemo {

    public static void main(String[] args) {
//        executeApplicationContext();
        executeBeanFactory();
    }

    private static void executeBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanProcessor());
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String[] locations = {"verify/dependency-lookup-context.xml", "verify/bean-constructor-dependency-injection.xml"};
        int beanNums = beanDefinitionReader.loadBeanDefinitions(locations);

        System.out.println(beanNums);

        // SmartInitializingSingleton 通常在 SpringApplicationContext 中使用
        beanFactory.preInstantiateSingletons();

        System.out.println(beanFactory.getBean("user", User.class));
        System.out.println(beanFactory.getBean("superUser", User.class));
        System.out.println(beanFactory.getBean("userHolder", UserHolder.class));

    }

    public static void executeApplicationContext() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();

        String[] locations = {"verify/dependency-lookup-context.xml", "verify/bean-constructor-dependency-injection.xml"};

        applicationContext.setConfigLocations(locations);

        applicationContext.refresh();

        System.out.println(applicationContext.getBean("user", User.class));
        System.out.println(applicationContext.getBean("superUser", User.class));
        System.out.println(applicationContext.getBean("userHolder", UserHolder.class));
        applicationContext.close();
    }
}
