package org.geekbang.thinking.in.spring.dependency.verify;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 将当前类 ObjectProviderDemo 作为配置类进行注册
        applicationContext.register(ObjectProviderDemo.class);

        applicationContext.refresh();

        // 获取 HierarchicalBeanFactory
        ConfigurableListableBeanFactory configurableListableBeanFactory = applicationContext.getBeanFactory();

        System.out.println("当前 beanFactory 的 parent beanFactory:" + configurableListableBeanFactory.getParentBeanFactory());

        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        configurableListableBeanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前 beanFactory 的 parent beanFactory:" + configurableListableBeanFactory.getParentBeanFactory());

        displayContainsBean(configurableListableBeanFactory, "user");
        displayContainsBean(parentBeanFactory, "user");

        applicationContext.close();
    }


    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if (containsBean(parentHierarchicalBeanFactory, beanName)) {
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayContainsLocalBean(HierarchicalBeanFactory hierarchicalBeanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含localBean[name : %s], %s\n", hierarchicalBeanFactory, beanName, hierarchicalBeanFactory.containsLocalBean(beanName));
    }

    private static void displayContainsBean(HierarchicalBeanFactory hierarchicalBeanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含Bean[name : %s], %s\n", hierarchicalBeanFactory, beanName, containsBean(hierarchicalBeanFactory, beanName));
    }

    private static HierarchicalBeanFactory createParentBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/verify/dependency-lookup-context.xml");
        return defaultListableBeanFactory;
    }
}
