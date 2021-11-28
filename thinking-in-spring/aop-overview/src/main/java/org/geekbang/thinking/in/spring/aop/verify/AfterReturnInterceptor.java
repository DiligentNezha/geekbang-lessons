package org.geekbang.thinking.in.spring.aop.verify;

import java.lang.reflect.Method;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/26
 */
public interface AfterReturnInterceptor {

    Object after(Object proxy, Method method, Object[] args, Object returnResult);
}
