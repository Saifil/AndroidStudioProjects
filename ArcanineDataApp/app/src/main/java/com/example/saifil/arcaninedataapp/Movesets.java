package com.example.saifil.arcaninedataapp;

public class Movesets {

    private int _id; //unique id for each moveset
    private String _moveName; //name of each moveset

    public Movesets() {

    }

    public Movesets(String moveName) { //constructor
        this._moveName = moveName;
    }

    //setter are used to store (input) the data
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_moveName(String _moveName) {
        this._moveName = _moveName;
    }

    //getters are used to retrieve the data
    public int get_id() {
        return _id;
    }

    public String get_moveName() {
        return _moveName;
    }
}
