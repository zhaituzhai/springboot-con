package com.zhaojm.bean;

/**
 * builder 方式
 */
public class Student {
    private final int no;
    private final String name;
    private final int age;

    public static class Builder{
        private final int no;
        private String name = "";
        private int age = 0;

        public Builder(int no){
            this.no = no;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Student build(){
            return new Student(this);
        }

    }

    private Student(Builder builder){
        no = builder.no;
        name = builder.name;
        age = builder.age;
    }

    public static void main(String[] args) {
        Student stu = new Builder(1).name("matte").age(18).build();
        System.out.println(stu.name);
    }

}
