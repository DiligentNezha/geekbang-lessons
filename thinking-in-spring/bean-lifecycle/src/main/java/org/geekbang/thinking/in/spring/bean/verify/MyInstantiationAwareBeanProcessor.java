package org.geekbang.thinking.in.spring.bean.verify;

import org.geekbang.thinking.in.spring.ioc.verify.domain.SuperUser;
import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/14
 */
public class MyInstantiationAwareBeanProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)) {
            return new SuperUser();
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
            // user 对象不允许赋值
            User user = (User) bean;
            user.setId(4L);
            user.setName("不偷懒的小哪吒");
            return false;
        }
        return true;
    }

    /**
     * user 跳过属性赋值
     * superUser 完全跳过 bean 实例化
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            MutablePropertyValues propertyValues;
            if (pvs instanceof MutablePropertyValues) {
                propertyValues = ((MutablePropertyValues) pvs);
            } else {
                propertyValues = new MutablePropertyValues();
            }
            propertyValues.add("number", 1);
            // 原始属性 <property name="description" value="The user holder"/>

            if (propertyValues.contains("description")) {
                propertyValues.removePropertyValue("description");
                propertyValues.addPropertyValue("description", "the user holder v2");
            }
            return propertyValues;
        }
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("the user holder v3");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("the user holder v7");
            System.out.println("postProcessAfterInitialization() : " + userHolder.getDescription());
        }
        return bean;
    }
}
