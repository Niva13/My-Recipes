package com.example.recipes.fragments;

import android.widget.ListView;

import com.example.recipes.Recepie;

public class DataModel {

    private String name;
    private Recepie TheRecipe;
    private int _id;

    private Object object;

    public DataModel() {

    }

    public DataModel(String name,Object object){
        this.name = name;
        this.object = object;
    }

    public DataModel(String name, Recepie theRecipe, int _id) {
        this.name = name;
        this.TheRecipe = theRecipe;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recepie getTheRecipe() {
        return TheRecipe;
    }

    public void setTheRecipe(Recepie theRecipe) {
        this.TheRecipe = theRecipe;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
