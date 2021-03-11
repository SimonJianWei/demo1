package com.example.service;

import com.example.CglibActressProxy;
import com.google.common.collect.Interner;
import org.springframework.cglib.proxy.Enhancer;

public class Test {

    public static void main(String[] args) {
        Edward edward = new Edward.EdwardBuilder("Simon", "china").setAge(23).setJob("programmer").build();
        StarProxy starProxy = new StarProxy();
//        starProxy.setTarget( new Actress( ));
//        starProxy.setTarget(edward);
//        Star  star = (Star)starProxy.creatProxyObj();//代理对象
//        star.dance("yunmianhu","oldsecond","play a joke");
//        star.dance(edward.getName(),edward.getAlias(),edward.getHobby());
        /****** cglib代理**** */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Actress.class);
        enhancer.setCallback(new CglibActressProxy());//回调函数
        Actress my = (Actress) enhancer.create();
        my.dance("yunmianhu", "oldsecond", "play a joke");
    }

    /**
     * 测试锁住key
     *
     * @param key
     */
    public void test1(Interner pool, String key) {
        ;
//        synchronized (pool.intern(key)){
        synchronized (key.intern()) {
            String name = Thread.currentThread().getName();
            System.out.println(key);
            System.out.println(name + "占用了锁");
            while (true) {

            }
        }
    }




}
