package com.example.recipes;

import java.util.ArrayList;

import java.io.Serializable;

public class Recepie implements Serializable{
    private ArrayList <Ingredient> ingredients;
    private String Name;
    private String photoPath;

    private String URL;



    boolean isFavorites;




    public Recepie(){
        this.isFavorites = false;
    }
    public Recepie(String Name, String photoPath){
        this.Name = Name;
        this.photoPath = photoPath;
        this.isFavorites = false;
    }
    public Recepie(String Name,ArrayList<Ingredient> ingredients) {
        this.Name=Name;
        this.ingredients = ingredients;
        this.isFavorites = false;
    }
    public Recepie(String Name, String URL, String internet)
    {
        this.Name = Name;
        this.URL = URL;
        this.isFavorites = false;
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

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        this.isFavorites = favorites;
    }


}
