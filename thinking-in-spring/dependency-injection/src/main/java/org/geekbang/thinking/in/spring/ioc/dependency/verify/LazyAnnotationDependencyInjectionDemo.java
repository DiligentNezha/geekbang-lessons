package org.geekbang.thinking.in.spring.ioc.dependency.verify;

import org.geekbang.thinking.in.spring.ioc.dependency.verify.annotation.UserGroup;
import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/3
 */
public class LazyAnnotationDependencyInjectionDemo {

    @Autowired
    private User user; // 实时注入

    @Autowired
    private ObjectProvider<User> userObjectProvider; // 延迟注入

    @Autowired
    private ObjectProvider<Collection<User>> userCollectionObjectProvider;

    @Autowired
    private ObjectFactory<Collection<User>> userObjectFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/verify/dependency-lookup-context.xml");

        applicationContext.refresh();

        LazyAnnotationDependencyInjectionDemo bean = applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);

        System.out.println("demo.user:" + bean.user);
        System.out.println("demo.userObjectProvider:" + bean.userObjectProvider.getObject());
        System.out.println("demo.userCollectionObjectProvider:" + bean.userCollectionObjectProvider.getObject());
        System.out.println("demo.userObjectFactory:" + bean.userObjectFactory.getObject());

        bean.userObjectProvider.forEach(System.out::println);

        applicationContext.close();
    }

}
