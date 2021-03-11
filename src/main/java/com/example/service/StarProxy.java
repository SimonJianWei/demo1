package com.example.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class StarProxy implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     *
     * @param proxy 代理类对象
     * @param method 被代理类方法对象
     * @param args 被代理类方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public String  invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入代理逻辑方法");
        System.out.println("在调度真实对象之前的服务");
        System.out.println(method.getName());
        String   result=method.invoke(target,args)+"";
        System.out.println("在调度真实对象之后的服务");
        return result;
    }

    public Object creatProxyObj(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
}
