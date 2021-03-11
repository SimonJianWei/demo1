package com.example.service;

/***
 *
 */
public interface Star {
    public  abstract String  sing(String name,String nationality);
    public  abstract String dance(String name, String alias,String hobby);
    static  void   init(){
        System.out.println("测试接口静态方法使用");
        System.out.println("调用实现方法");
    }
}
