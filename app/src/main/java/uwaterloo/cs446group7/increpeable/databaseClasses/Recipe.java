package uwaterloo.cs446group7.increpeable.databaseClasses;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String title;
    private String location;
    private String description;
    private String authorID; // use this to get the corresponding user object
    private List<String> steps;
    private List<Pair<String, Integer>> ingredients = new ArrayList<Pair<String, Integer>>();
    private String key;

    // constructor
    public Recipe() {}
    public Recipe(String title, String location, String description, String authorID, List<String> steps, List<Pair<String, Integer>> ingredients) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.authorID = authorID;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    // getters
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getAuthorID() { return authorID; }
    public List<String> getSteps() { return steps; }
    public List<Pair<String, Integer>> getIngredients() { return ingredients; }
    public String getKey() { return key; }

    // setters
    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setDescription(String description) { this.description = description; }
    public void setAuthorID(String authorID) { this.authorID = authorID; }
    public void setSteps(List<String> steps) { this.steps = steps; }
    public void setIngredients(List<Pair<String, Integer>> ingredients) {this.ingredients = ingredients; }
    public void setKey(String key) { this.key = key; }
}
