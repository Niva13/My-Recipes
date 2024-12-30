package com.example.recipes;

import java.util.ArrayList;

public class Recepie {
    private ArrayList <Ingredient> ingredients;
    private int image;
    private String URL;




    public Recepie(){

    }
    public Recepie(int image){
        this.image = image;
    }
    public Recepie(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public Recepie(String URL){
        this.URL = URL;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

}
