package org.geekbang.thinking.in.spring.aop.verify;

import java.lang.reflect.Method;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/26
 */
public interface FinallyInterceptor {

    Object finalize(Object proxy, Method method, Object[] args, Object returnResult);
}

class TimeFinallyInterceptor implements FinallyInterceptor {

    private final long start;

    private final long end;

    TimeFinallyInterceptor(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Object finalize(Object proxy, Method method, Object[] args, Object returnResult) {
        long cost = end - start;
        return cost;
    }
}