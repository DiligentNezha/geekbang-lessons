package org.geekbang.thinking.in.spring.configuration.verify;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ObjectUtils;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/28
 */
public class BeanConfigurationMetadataDemo {

    public static void main(String[] args) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name", "mercyblitz");

        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        //附加属性（不影响 bean populate， initialize）
        beanDefinition.setAttribute("name", "小马哥");

        //辅助作用，标识 bean 注册来源
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
                    BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
                    // 属性上下文
                    if (BeanConfigurationMetadataDemo.class.equals(bd.getSource())) {
                        String name = (String) bd.getAttribute("name");
                        ((User) bean).setName(name);
                    }
                }
                return bean;
            }
        });

        beanFactory.registerBeanDefinition("user", beanDefinition);

        System.out.println(beanFactory.getBean(User.class));

    }
}
