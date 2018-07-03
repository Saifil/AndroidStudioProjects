package com.example.saifil.myarrayadopter;

public class Drinks {

    private String name;
    private String desc;

    public Drinks(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static final Drinks[] drinks = {
            new Drinks("Latte","See you Latte!"),
            new Drinks("Cappuccino","Go Cappuccino yourself."),
            new Drinks("Filter Coffee","To bean or not to bean.")
    };

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }


    public String toString() {
        return this.name;
    }

}
