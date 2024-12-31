package com.example.recipes;

import java.util.ArrayList;

public class Recepie {
    private ArrayList <Ingredient> ingredients;
    private String Name;
    private String photoPath;

    private String URL;




    public Recepie(){

    }
    public Recepie(String Name, String photoPath){
        this.Name = Name;
        this.photoPath = photoPath;
    }
    public Recepie(String Name,ArrayList<Ingredient> ingredients) {
        this.Name=Name;
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

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void addIngredient(Ingredient ingredient)
    {
        ingredients.add(ingredient);
    }

}
