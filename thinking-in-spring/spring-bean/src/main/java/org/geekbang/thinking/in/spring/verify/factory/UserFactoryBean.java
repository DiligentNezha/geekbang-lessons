package org.geekbang.thinking.in.spring.verify.factory;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;
import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
