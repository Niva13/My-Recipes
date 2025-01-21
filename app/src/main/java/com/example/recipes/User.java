package com.example.recipes;

import java.util.HashMap;
import java.util.Map;

public class User {
    private  String phone;
    private  String email;
    private Map<String, Recepie> recipes; // Map to hold recipes by name

    public User() {
        // Default constructor required for Firebase
    }

    public User(String phone, String email) {
        this.phone = phone;
        this.email = email;
        this.recipes = new HashMap<>();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Recepie> getRecipes() {
        return recipes;
    }

    public void setRecipes(Map<String, Recepie> recipes) {
        this.recipes = recipes;
    }
}
