package com.example.recipes;

import java.util.ArrayList;

public class User {


    private  String phone;

    private  String email;

    private ArrayList<Recepie> recepies;

    public User()
    {

    }
    public User(String phone, String email) {

        this.phone = phone;

        this.email = email;

        this.recepies = new ArrayList<Recepie>();
    }



    public String getPhone() {
        return phone;
    }


    public String getEmail() {
        return email;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Recepie> getRecepies() {
        return recepies;
    }

    public void setRecepies(ArrayList<Recepie> recepies) {
        this.recepies = recepies;
    }


    public void addRecipe(Recepie recipe)
    {
        recepies.add(recipe);

        /*if(!(recipe.getURL().isEmpty())) // Internet recipe
        {

        }
        else if (!(recipe.getImageName().isEmpty())) // Picture recipe
        {

        }
        else // Manually recipe
        {

        }*/
    }
}
