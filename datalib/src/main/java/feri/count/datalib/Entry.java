package feri.count.datalib;

public class Entry {
    private String name;
    private String meal;
    private String time;
    private double quantity;
    private double carbs;
    private double protein;
    private double fats;
    private boolean custom;

    public Entry() {
    }

    public Entry(String name, String meal, String time, double quantity, double carbs, double protein, double fats, boolean custom) {
        this.name = name;
        this.meal = meal;
        this.time = time;
        this.quantity = quantity;
        this.carbs = carbs;
        this.protein = protein;
        this.fats = fats;
        this.custom = custom;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity(){
        this.quantity += 1;
    }

    public void dencrementQuantity(){
        this.quantity -= 1;
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

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }
}
