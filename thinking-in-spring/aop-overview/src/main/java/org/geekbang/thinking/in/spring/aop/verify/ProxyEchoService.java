package org.geekbang.thinking.in.spring.aop.verify;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/12/20
 */
public class ProxyEchoService implements EchoService{

    private EchoService echoService;

    public ProxyEchoService(EchoService echoService) {
        this.echoService = echoService;
    }

    @Override
    public String echo(String msg) {
        long start = System.currentTimeMillis();
        String result = echoService.echo(msg);
        long costTime = System.currentTimeMillis() - start;
        System.out.println("echo 方法执行时间: " + costTime + " ms.");
        return result;
    }
}
