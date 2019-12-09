package com.zhaojm.controller;

public class BounsTest {

    public static String bonusTime(final int salary, final boolean bonus) {
        return "\u00A3"+ salary +(bonus?"0":"");
    }

    public static void main(String[] args) {
        System.out.println(bonusTime(10000, true));
    }

}
