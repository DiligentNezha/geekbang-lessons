package org.geekbang.thinking.in.spring.bean.verify;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/13
 */
public class UserHolder implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, EnvironmentAware, InitializingBean, SmartInitializingSingleton, DisposableBean {

    private final User user;

    private Integer number;

    private String description;

    public UserHolder(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    private String beanName;

    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        // initPostConstruct v4 -> afterPropertiesSet v5
        this.description = "the user holder v5";
        System.out.println("afterPropertiesSet() = " + description);
    }

    @PostConstruct
    public void initPostConstruct() {
        // postProcessBeforeInitialization v3 -> initPostConstruct v4
        this.description = "the user holder v4";
        System.out.println("initPostConstruct() = " + description);
    }

    @PreDestroy
    public void preDestroy() {
        // postProcessBeforeDestruction : the user holder v9
        this.description = "the user holder v10";
        System.out.println("preDestroy() = " + description);
    }

    public void init() {
        // afterPropertiesSet v5 -> init v6
        this.description = "the user holder v6";
        System.out.println("init() = " + description);
    }

    public void doDestroy() {
        // destroy: v11
        this.description = "the user holder v12";
        System.out.println("doDestroy() = " + description);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
    // 属于 ApplicationContext

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterSingletonsInstantiated() {
        // postProcessAfterInitialization v7 -> init v8
        this.description = "the user holder v8";
        System.out.println("afterSingletonsInstantiated() = " + description);
    }

    @Override
    public void destroy() throws Exception {
        // preDestroy : the user holder v10
        this.description = "the user holder v11";
        System.out.println("destroy() = " + description);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("UserHolder is finalized.....");
    }
}
