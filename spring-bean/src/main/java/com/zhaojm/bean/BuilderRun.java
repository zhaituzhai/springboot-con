package com.zhaojm.bean;

import static com.zhaojm.bean.NyPizza.Size.SMALL;
import static com.zhaojm.bean.Pizza.Topping.*;

public class BuilderRun {
    public static void main(String[] args) {
        NyPizza pizza = new NyPizza.Builder(SMALL).addTopping(SAUSAGE).addTopping(ONION).build();
        Calzone calzone = new Calzone.Builder().addTopping(HAM).sauceInside().build();
        System.out.println(pizza.toppings);
        System.out.println(calzone.toppings);
    }
}
