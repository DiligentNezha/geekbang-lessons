package org.geekbang.thinking.in.spring.ioc.bean.verify;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/5
 */
public class BeanScopeDemo implements DisposableBean {

    @Bean
    public static User singletonUser() {
        return createUser();
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static User prototypeUser() {
        return createUser();
    }

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    private Map<String, User> users;

    /**
     * ResolvableDependency
     */
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(BeanScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
                @Override
                public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                    System.out.printf("%s Bean 名称:%s 在初始化后回调....\n", bean.getClass().getName(), beanName);
                    return bean;
                }

                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    System.out.printf("%s Bean 名称:%s 在销毁后回调....\n", bean.getClass().getName(), beanName);
                    return bean;
                }
            });
        });

        applicationContext.refresh();

//        scopedBeansByLookup(applicationContext);

//        scopedBeansByInjection(applicationContext);

        applicationContext.close();
    }

    private static void scopedBeansByInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo bean = applicationContext.getBean(BeanScopeDemo.class);
        System.out.println("beanScopeDemo.singletonUser:" + bean.singletonUser);
        System.out.println("beanScopeDemo.singletonUser1:" + bean.singletonUser1);

        System.out.println("beanScopeDemo.prototypeUser:" + bean.prototypeUser);
        System.out.println("beanScopeDemo.prototypeUser1:" + bean.prototypeUser1);
        System.out.println("beanScopeDemo.prototypeUser2:" + bean.prototypeUser2);

        System.out.println("beanScopeDemo.users:" + bean.users);
    }

    private static void scopedBeansByLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println("singletonUser:" + singletonUser);

            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            System.out.println("prototypeUser:" + prototypeUser);
        }
    }


    @Override
    public void destroy() throws Exception {
//        this.singletonUser.destroy();
//        this.singletonUser1.destroy();

        System.out.println("当前 BeanScopeDemo Bean 正在销毁中....");
        this.prototypeUser.destroy();
        this.prototypeUser1.destroy();
        this.prototypeUser2.destroy();

        for (Map.Entry<String, User> entry : users.entrySet()) {
            String beanName = entry.getKey();

            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            if (beanDefinition.isPrototype()) {
                User user = entry.getValue();
                user.destroy();
            }

        }
        System.out.println("当前 BeanScopeDemo Bean 正在销毁完成....");
    }
}
