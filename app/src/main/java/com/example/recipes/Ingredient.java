package com.example.recipes;

public class Ingredient {
    private String IngName;
    private String Amount;
    private String Unit;

    public Ingredient(){

    }
    public Ingredient(String ingName, String amount, String unit) {
        IngName = ingName;
        Amount = amount;
        Unit = unit;
    }

    public String getIngName() {
        return IngName;
    }

    public void setIngName(String ingName) {
        IngName = ingName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
