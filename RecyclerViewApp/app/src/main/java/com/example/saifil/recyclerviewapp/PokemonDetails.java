package com.example.saifil.recyclerviewapp;

//this class handles the pokemon card details

public class PokemonDetails {

    private int id, imgID;
    private String name;

    public PokemonDetails(int id, int imgID, String name) {
        this.id = id;
        this.imgID = imgID;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getImgID() {
        return imgID;
    }

    public String getName() {
        return name;
    }
}
