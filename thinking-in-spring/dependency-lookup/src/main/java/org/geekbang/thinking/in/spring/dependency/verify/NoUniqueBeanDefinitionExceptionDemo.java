package org.geekbang.thinking.in.spring.dependency.verify;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/2
 */
public class NoUniqueBeanDefinitionExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);

        applicationContext.refresh();

        try {
            // 由于存在两个 String 类型的 bean，通过单一类型查找会抛出异常
            String bean = applicationContext.getBean(String.class);
        } catch (NoUniqueBeanDefinitionException e) {
            System.err.printf("Spring 上下文存在%d个%s类型的bean，具体原因：%s\n", e.getNumberOfBeansFound(), String.class.getName(), e.getMessage());
        }

        applicationContext.close();
    }

    @Bean
    public String bean1() {
        return "bean1";
    }

    @Bean
    public String bean2() {
        return "bean2";
    }

    @Bean
    public String bean3() {
        return "bean3";
    }
}
