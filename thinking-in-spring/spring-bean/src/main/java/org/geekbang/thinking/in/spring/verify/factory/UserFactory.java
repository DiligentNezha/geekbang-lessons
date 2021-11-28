package org.geekbang.thinking.in.spring.verify.factory;

import org.geekbang.thinking.in.spring.ioc.verify.domain.User;

public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }
}
