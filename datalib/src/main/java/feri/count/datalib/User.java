package feri.count.datalib;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    public static final String COLLECTION = "users";
    private String id;
    private String username;
    private String email;
    private String password;
    private double weight;
    private boolean weightLoss;
    private double dailyCalories;
    private String diet;
    private ArrayList<Entry> entries;

    public User() {
        entries = new ArrayList<>();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        entries = new ArrayList<>();
    }

    public User(User other) {
        this.id = other.id;
        this.username = other.username;
        this.email = other.email;
        this.password = other.password;
        this.weight = other.weight;
        this.weightLoss = other.weightLoss;
        this.dailyCalories = other.dailyCalories;
        this.diet = other.diet;
        this.entries = new ArrayList<>();

        for(int i = 0; i < other.entries.size(); i++)
            this.entries.add(other.entries.get(i));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(boolean weightLoss) {
        this.weightLoss = weightLoss;
    }

    public double getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(double dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public void addEntry(Entry entry){
        entries.add(entry);
    }

    public int findEntry(Entry entry){
        for (int i = 0; i < entries.size(); i++) {
            if (entry.getName().equals(entries.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public void removeIngredient(Entry entry){
        if(entries.remove(findEntry(entry)).getQuantity() - 1 == 0)
            entries.remove(findEntry(entry));
        else
            entries.remove(findEntry(entry)).decrementQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return this.email.equals(user.getEmail()) && this.username.equals(user.getUsername());
    }

}