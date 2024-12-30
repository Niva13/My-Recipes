package com.example.recipes.fragments;

import android.widget.ListView;

public class DataModel {

    private String name;
    private ListView TheRecipe;
    private int _id;

    public DataModel() {

    }

    public DataModel(String name, ListView theRecipe, int _id) {
        this.name = name;
        TheRecipe = theRecipe;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListView getTheRecipe() {
        return TheRecipe;
    }

    public void setTheRecipe(ListView theRecipe) {
        TheRecipe = theRecipe;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
