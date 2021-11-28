package org.geekbang.thinking.in.spring.ioc.dependency.verify;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/4
 */
public class DependencySourceDemo {

    // 注入在 postProcessProperties 方法执行，早于 setter 注入，也早于 @PostConstruct

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void initByLookup() {
        getBean(BeanFactory.class);
        getBean(ApplicationContext.class);
        getBean(ResourceLoader.class);
        getBean(ApplicationEventPublisher.class);
    }

    @PostConstruct
    public void init() {
        System.out.println("beanFactory == applicationContext:" + (beanFactory == applicationContext));
        System.out.println("beanFactory == applicationContext.getBeanFactory:" + (beanFactory == applicationContext.getAutowireCapableBeanFactory()));
        System.out.println("resourceLoader == applicationContext:" + (resourceLoader == applicationContext));
        System.out.println("applicationEventPublisher == applicationContext:" + (applicationEventPublisher == applicationContext));
    }

    private <T> T getBean(Class<T> beanType) {
        try {
            return beanFactory.getBean(beanType);
        } catch (NoSuchBeanDefinitionException e) {
            System.err.println("当前类型:" + beanType.getName() + "无法在 BeanFactory 中查找");
        }
        return null;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(DependencySourceDemo.class);

        applicationContext.refresh();

        DependencySourceDemo bean = applicationContext.getBean(DependencySourceDemo.class);


        applicationContext.close();
    }
}
