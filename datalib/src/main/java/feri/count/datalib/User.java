package feri.count.datalib;

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

    public User() { }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
}