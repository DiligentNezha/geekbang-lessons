package org.geekbang.thinking.in.spring.ioc.dependency.verify;

import org.geekbang.thinking.in.spring.ioc.dependency.verify.annotation.InjectedUser;
import org.geekbang.thinking.in.spring.ioc.dependency.verify.annotation.MyAutowired;
import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/3
 */
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    private User lazyUser; // 延迟依赖查找

    @Autowired
    private User user; // DependencyDescriptor required=ture 实时注入 + 通过类型(User.class) 依赖查找 + 字段名称（“user”）

    @Autowired // 集合类型依赖注入
    private Map<String, User> userMap;

    @MyAutowired
    private Optional<User> userOptional; // superUser

    @InjectedUser
    private User myInjectedUser;

//    @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
////    @Bean(name = "injectedUserAnnotationBeanPostProcessor")
//    // static 方法可以提前触发 bean 的创建
//    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
//        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        // 替换原有注解处理，是用自定义注解 @InjectedUser
//        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(Arrays.asList(Autowired.class, Inject.class, InjectedUser.class));
//        autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
//
//        return autowiredAnnotationBeanPostProcessor;
//    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
        return beanPostProcessor;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/verify/dependency-lookup-context.xml");

        applicationContext.refresh();

        AnnotationDependencyInjectionResolutionDemo bean = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        System.out.println("demo.user:" + bean.user);

        System.out.println("demo.userMap:" + bean.userMap);

        System.out.println("demo.userOptional:" + bean.userOptional);

        System.out.println("demo.lazyUser:" + bean.lazyUser);

        System.out.println("demo.myInjectedUser:" + bean.myInjectedUser);

        applicationContext.close();
    }

}
