package org.geekbang.thinking.in.spring.ioc.dependency.verify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/4
 */
@Configuration
@PropertySource(value = "/META-INF/default.properties", encoding = "UTF-8")
public class ExternalConfigurationDependencySourceDemo {

    @Value("${user.id}")
    private Long id;

    @Value("${user.name}")
    private String name;

    @Value("${user.resource}")
    private Resource resource;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);

        applicationContext.refresh();

        ExternalConfigurationDependencySourceDemo bean = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);

        System.out.println(bean.id);
        System.out.println(bean.name);
        System.out.println(bean.resource);

        applicationContext.close();
    }
}
