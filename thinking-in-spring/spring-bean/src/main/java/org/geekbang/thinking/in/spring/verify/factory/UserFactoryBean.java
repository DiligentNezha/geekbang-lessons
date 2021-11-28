package org.geekbang.thinking.in.spring.verify.factory;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class UserFactoryBean implements FactoryBean, BeanNameAware {

    private String beanName;

    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @PostConstruct
    public void init() {
        System.out.println("用户对象[" + beanName+ "]通过@PostConstruct初始化.......");
    }

    public void initMethod() {
        System.out.println("用户对象[" + beanName+ "]执行 initMethod() 进行初始化.......");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("用户对象[" + beanName + "]通过@PreDestroy销毁.....");
    }

    public void destroyMethod() {
        System.out.println("用户对象[" + beanName + "]执行 destroyMethod() 进行销毁.....");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
