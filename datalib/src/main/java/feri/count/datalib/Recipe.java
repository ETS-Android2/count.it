package feri.count.datalib;

import java.util.ArrayList;

public class Recipe {
    public static final String COLLECTION = "recipes";

    private String name;
    private String instructions;
    private ArrayList<Entry> ingredients;
    private double preparationTime;
    private double cookingTime;

    public Recipe() {
        ingredients = new ArrayList<>();
    }

    public Recipe(String name, String instructions, ArrayList<Entry> ingredients, double preparationTime, double cookingTime) {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public ArrayList<Entry> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Entry> ingredients) {
        this.ingredients = ingredients;
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }

    public double getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(double cookingTime) {
        this.cookingTime = cookingTime;
    }

    public boolean alreadyExists(Entry entry) {
        for (Entry e : ingredients) {
            if (entry.getName().equals(e.getName())) {
                return true;
            }
        }
        return false;
    }

    public int findEntry(Entry entry){
        for (int i = 0; i < ingredients.size(); i++) {
            if (entry.getName().equals(ingredients.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public void addIngredient(Entry entry){
        if(alreadyExists(entry))
            ingredients.get(findEntry(entry)).incrementQuantity();
        else
            ingredients.add(entry);
    }


    public void removeIngredient(Entry entry){
        if(ingredients.remove(findEntry(entry)).getQuantity() - 1 == 0)
            ingredients.remove(findEntry(entry));
        else
            ingredients.remove(findEntry(entry)).decrementQuantity();
    }
}
