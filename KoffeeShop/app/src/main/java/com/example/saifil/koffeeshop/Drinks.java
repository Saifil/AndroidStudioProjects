package com.example.saifil.koffeeshop;

public class Drinks {

    private String name;
    private String desc;
    private int imgID;

    public Drinks(String name, String desc, int imgID) {
        this.name = name;
        this.desc = desc;
        this.imgID = imgID;
    }

    public Drinks() {
    }

    public static final Drinks[] drinks = {
            new Drinks("Latte","See you Latte!",R.drawable.latte),
            new Drinks("Cappuccino","Go Cappuccino yourself.",R.drawable.cap),
            new Drinks("Filter Coffee","To bean or not to bean.",R.drawable.beans)
    };

    public String getName(int value) {
        return drinks[value].name;
    }

    public String getDesc(int value) {
        return drinks[value].desc;
    }

    public int getImgID(int value) {
        return drinks[value].imgID;
    }

    public String toString() {
        return this.name;
    }

}
