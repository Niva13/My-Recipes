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
    public Recepie(String Name, String URL, String internet)
    {
        this.Name = Name;
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


    /*public String getIngByString()
    {
        ArrayList<Ingredient> NewIngredients = new ArrayList<>();
        NewIngredients = this.ingredients;

        String stringIngredients = "";

        for (Ingredient NewIngredient : NewIngredients)
        {
            String ingredientName = NewIngredient.getIngName();
            String ingredientAmount = NewIngredient.getAmount();
            String ingredientUnit = NewIngredient.getUnit();

            stringIngredients = "Name: " + ingredientName + "\n" + "Amount: " + ingredientAmount + "\n" + "Unit: " + ingredientUnit + "\n\n";

        }

        return stringIngredients;
    }*/

}
