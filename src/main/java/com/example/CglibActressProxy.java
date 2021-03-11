package com.example;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibActressProxy implements MethodInterceptor {
    /**
     * proxy：代理对象，CGLib动态生成的代理类实例
     * method：目标对象的方法，上文中实体类所调用的被代理的方法引用
     * args：目标对象方法的参数列表，参数值列表
     * methodProxy：代理对象的方法，生成的代理类对方法的代理引用
     */
    @Override
    public String intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("进入代理逻辑方法");
//        System.out.println(method.getName());
//        method.invoke(proxy,args);
        String result=(String)methodProxy.invokeSuper(proxy,args);
        return result;
    }


}
