package com.example.service;

public class Actress implements  Star {

    @Override
    public String sing(String name, String nationality) {
        Star.init();
        System.out.println("This  is  a n actor  who   is  come from " + nationality);
        System.out.println("he is " + name);
        return "end of singing";
    }

    @Override
    public String dance(String name, String alias, String hobby) {
        Star.init();
        System.out.println(" he is " + alias + " who  name  is " + name);
        System.out.println(" just last dancing ");
        return "end of  dancing ";
    }


}
