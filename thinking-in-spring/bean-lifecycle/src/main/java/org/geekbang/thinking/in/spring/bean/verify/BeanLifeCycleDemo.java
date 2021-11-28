package org.geekbang.thinking.in.spring.bean.verify;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/19
 */
public class BeanLifeCycleDemo {

    public static void main(String[] args) throws InterruptedException {
        executeBeanFactory();
    }

    private static void executeBeanFactory() throws InterruptedException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanProcessor());
        beanFactory.addBeanPostProcessor(new MyDestructionAwareBeanPostProcessor());
        // 执行@PostConstruct
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String[] locations = {"verify/dependency-lookup-context.xml", "verify/bean-constructor-dependency-injection.xml"};
        int beanNums = beanDefinitionReader.loadBeanDefinitions(locations);

        System.out.println(beanNums);

        // SmartInitializingSingleton 通常在 SpringApplicationContext 中使用
        beanFactory.preInstantiateSingletons();

        System.out.println(beanFactory.getBean("user", User.class));
        System.out.println(beanFactory.getBean("superUser", User.class));
        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);

        // 执行 Bean 销毁（容器内）
        beanFactory.destroyBean("userHolder", userHolder);

        // Bean 销毁并不意味着 Bean 垃圾回收了
        System.out.println(userHolder);

        // 销毁 BeanFactory 中的单例
        System.gc();

        Thread.sleep(3000);
    }
}
