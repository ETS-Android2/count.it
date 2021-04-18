package feri.count.datalib;

import java.util.ArrayList;

public class Entry {
    public static final String COLLECTION = "entries";
    public static final int SERVING = 100;

    private String id;
    private String name;
    private ArrayList<String> mealTypes;
    private String meal;
    private String date;
    private String time;
    private String type;
    private double quantity;
    private double carbs;
    private double protein;
    private double fats;
    private double calories;
    private boolean custom;

    public Entry() {
    }

    public Entry(String name, ArrayList<String> mealTypes, String meal, String date, String time, String type,
                 double quantity, double carbs, double protein, double fats, double calories,
                 boolean custom) {
        this.name = name;
        this.mealTypes = mealTypes;
        this.meal = meal;
        this.time = time;
        this.date = date;
        this.type = type;
        this.quantity = quantity;
        this.carbs = carbs;
        this.protein = protein;
        this.fats = fats;
        this.calories = calories;
        this.custom = custom;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public ArrayList<String> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(ArrayList<String> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String time) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity(){
        quantity += 1;
        calories += calories;
    }

    public void decrementQuantity(){
        quantity -= 1;
        calories -= calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    @Override
    public String toString() {
        String ret =  this.id + ";"
                + name + ";"
                + meal + ";"
                + date + ";"
                + time + ";"
                + type + ";"
                + String.valueOf(quantity) + ";"
                + String.valueOf(carbs) + ";"
                + String.valueOf(protein) + ";"
                + String.valueOf(fats) + ";"
                + String.valueOf(calories) + ";"
                + String.valueOf(custom) + ";";

        for(int i = 0; i < mealTypes.size(); i++)
            ret += mealTypes.get(i).toString() + ";";

        return ret;
    }
}
