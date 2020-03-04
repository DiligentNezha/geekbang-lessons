package org.geekbang.thinking.in.spring.ioc.dependency.verify;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/2
 */
public class AutowiringConstructorDependencySetterInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String location = "classpath:/verify/autowiring-dependency-constructor-injection.xml";
        xmlBeanDefinitionReader.loadBeanDefinitions(location);

        System.out.println(beanFactory.getBean(UserHolder.class));
    }
}
