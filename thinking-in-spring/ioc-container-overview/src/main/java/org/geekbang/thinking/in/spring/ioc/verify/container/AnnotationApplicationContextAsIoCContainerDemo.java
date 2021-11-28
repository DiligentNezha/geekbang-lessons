package org.geekbang.thinking.in.spring.ioc.verify.container;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.*;
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

        applicationContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
                    @Override
                    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                        return null;
                    }

                    @Override
                    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                        return true;
                    }

                    @Override
                    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
                        return null;
                    }
                });
            }
        });

        applicationContext.getDefaultListableBeanFactory().addBeanPostProcessor(new DestructionAwareBeanPostProcessor() {
            @Override
            public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {

            }

            @Override
            public boolean requiresDestruction(Object bean) {
                return true;
            }
        });
        applicationContext.getDefaultListableBeanFactory().addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("用户对象[" + beanName + "]通过BeanPostProcessor#postProcessBeforeInitialization 自定义 bean");
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("用户对象[" + beanName + "]通过BeanPostProcessor#postProcessAfterInitialization 自定义 bean");
                return bean;
            }
        });
        // 启动应用上下文
        applicationContext.refresh();

        lookupCollectionType(applicationContext);

        // 关闭应用上下文
        applicationContext.close();
    }

    private static void lookupCollectionType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
//            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
//            System.out.println("查找到的所有的 User 集合对象:" + users);
        }
    }

    @Bean(name = "user", initMethod = "initMethod", destroyMethod = "destroyMethod")
    public User user() {
        User user = new User();
        user.setId(8L);
        user.setName("不偷懒的小哪吒");
        return user;
    }

}
