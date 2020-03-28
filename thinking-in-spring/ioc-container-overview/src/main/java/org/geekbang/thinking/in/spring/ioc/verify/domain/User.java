package org.geekbang.thinking.in.spring.ioc.verify.domain;

import org.geekbang.thinking.in.spring.ioc.verify.enums.City;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class User implements BeanNameAware, InitializingBean, DisposableBean {

    private Long id;

    private String name;

    private City city;

    private City[] workCities;

    private List<City> lifeCities;

    private Map<String, Object> map;

    private Resource configFileLocation;

    private String beanName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Resource getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(Resource configFileLocation) {
        this.configFileLocation = configFileLocation;
    }

    public City[] getWorkCities() {
        return workCities;
    }

    public void setWorkCities(City[] workCities) {
        this.workCities = workCities;
    }

    public List<City> getLifeCities() {
        return lifeCities;
    }

    public void setLifeCities(List<City> lifeCities) {
        this.lifeCities = lifeCities;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("小马哥");
        return user;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("用户对象[" + beanName+ "]通过@PostConstruct初始化.......");
    }

    public void initMethod() {
        System.out.println("用户对象[" + beanName+ "]执行 initMethod() 进行初始化.......");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("用户对象[" + beanName + "]通过@PreDestroy销毁.....");
    }

    public void destroyMethod() {
        System.out.println("用户对象[" + beanName + "]执行 destroyMethod() 进行销毁.....");
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", workCities=" + Arrays.toString(workCities) +
                ", lifeCities=" + lifeCities +
                ", map=" + map +
                ", configFileLocation=" + configFileLocation +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("用户对象[" + beanName + "]执行 afterPropertiesSet() 进行属性设置.....");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("用户对象[" + beanName + "]执行 destroy() 进行销毁.....");
    }
}
