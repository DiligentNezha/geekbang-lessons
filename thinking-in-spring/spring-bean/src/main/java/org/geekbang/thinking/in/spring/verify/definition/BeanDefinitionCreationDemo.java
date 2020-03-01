package org.geekbang.thinking.in.spring.verify.definition;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        // 1.通过 BeanDefinition 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);

        // 通过属性设置
        beanDefinitionBuilder.addPropertyValue("id", 15);

        // 获取 beanDefinition 实例
        beanDefinitionBuilder.addPropertyValue("name", "不偷懒的小哪吒");

        // BeanDefinition 并非 Bean 终态，可以自定义修改
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        // 2.通过 AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();

        // 设置 bean 类型
        genericBeanDefinition.setBeanClass(User.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id", 13);
//        propertyValues.addPropertyValue("name", "不偷懒的小哪吒");
        propertyValues.add("id", 13).add("name", "不偷懒的小哪吒");
        // 通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);
    }
}
