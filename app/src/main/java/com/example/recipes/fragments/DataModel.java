package com.example.recipes.fragments;

import com.example.recipes.Recepie;
import java.io.Serializable;
public class DataModel implements Serializable {

    private String name;

    private Object object;

    private Recepie recepie;

    public DataModel() {

    }

    public DataModel(String name, Recepie recepie) {
        this.name = name;
        this.recepie = recepie;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Object getObject() {
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Recepie getRecepie() {
        return recepie;
    }

    public void setRecepie(Recepie recepie) {
        this.recepie = recepie;
    }

    public DataModel getDatamodel(){
        return this;
    }


}