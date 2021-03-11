package com.example.service;

import java.util.Comparator;

/**
 * 实验建造者&&代理
 */
public class Edward implements Star ,Comparable<Edward>, Comparator {

    private int age;
    private String alias;
    private String hobby;
    private String name;
    private String job;
    private String nationality;

    @Override
    public String sing(String name, String nationality) {
        Star.init();
        System.out.println("This  is  an actor  who   is  come from " + nationality);
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

    public Edward(EdwardBuilder builder) {
        this.age = builder.age;
        this.alias = builder.alias;
        this.hobby = builder.hobby;
        this.name = builder.name;
        this.job = builder.job;
        this.nationality = builder.nationality;
    }
    public  Edward(){

    }

    public int getAge() {
        return age;
    }

    public String getAlias() {
        return alias;
    }

    public String getHobby() {
        return hobby;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public int compareTo(Edward o) {
        return 0;                //当compareTo方法返回0的时候集合中只有一个元素
//        return 1;                //当compareTo方法返回正数的时候集合会怎么存x就怎么取
//        return -1;                //当compareTo方法返回负数的时候集合会倒序存储
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    public static class EdwardBuilder {
        private int age;
        private String alias;
        private String hobby;
        private String name;
        private String job;
        private String nationality;

        public int getAge() {
            return age;
        }

        public String getAlias() {
            return alias;
        }

        public String getHobby() {
            return hobby;
        }

        public String getName() {
            return name;
        }

        public String getJob() {
            return job;
        }

        public String getNationality() {
            return nationality;
        }

        public EdwardBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public EdwardBuilder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public EdwardBuilder setHobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        public EdwardBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public EdwardBuilder setJob(String job) {
            this.job = job;
            return this;
        }

        public EdwardBuilder setNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }


        public EdwardBuilder(String name, String nationality) {
            this.name = name;
            this.nationality = nationality;
        }

        public Edward build() {
            return new Edward(this);
        }
    }

}
