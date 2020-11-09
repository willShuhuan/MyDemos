package com.dsh.txlessons.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author dongshuhuan
 * date 2020/10/30
 * version
 */
public class ProxyDemo {
    public static void main(String[] args) {
        //通过动态代理获取 ApiService 的对象
        GitHubService apiService = (GitHubService) Proxy.newProxyInstance(
                GitHubService.class.getClassLoader(),
                new Class[]{GitHubService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("method = " + method.getName() + "   args = " + Arrays.toString(args));
                        System.out.println("annotations = " + method.getAnnotations()[0]);

                        return null;
                    }
                });
        apiService.httpRepos("dsh");
    }
}
