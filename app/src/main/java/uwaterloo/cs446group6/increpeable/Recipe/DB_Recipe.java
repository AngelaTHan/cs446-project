package uwaterloo.cs446group6.increpeable.Recipe;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DB_Recipe extends Recipe {
    // constructor
    public DB_Recipe() {}
    public DB_Recipe (DatabaseReference mDatabase, String key, String title, String location, String description, String authorKey, String getCoverImageName, ArrayList<String> steps, ArrayList<String> ingredients, Integer numLikes, Integer numCollects) {
        super(key, title, location, description, authorKey, getCoverImageName, steps, ingredients, numLikes, numCollects);
    }
    public DB_Recipe (Recipe recipe) {
        super (recipe.getKey(), recipe.getTitle(), recipe.getLocation(), recipe.getDescription(), recipe.getAuthorKey(),
                recipe.getCoverImageName(), recipe.getSteps(), recipe.getIngredients(), recipe.getNumLikes(), recipe.getNumCollects());
        this.numLikes = recipe.getNumLikes();
        this.numCollects = recipe.getNumCollects();
    }

    // Upload recipe into database
    public void uploadRecipe (DatabaseReference mDatabase) {
        mDatabase.child("Recipes").child(key).setValue(this);
    }

    // setters
    public void setTitle(DatabaseReference mDatabase, String title) {
        this.title = title;
        mDatabase.child("Recipes").child(key).child("title").setValue(title);
    }
    public void setDescription(DatabaseReference mDatabase, String description) {
        this.description = description;
        mDatabase.child("Recipes").child(key).child("description").setValue(description);
    }
    public void setLocation(DatabaseReference mDatabase, String location) {
        this.location = location;
        mDatabase.child("Recipes").child(key).child("location").setValue(location);
    }
    public void setCoverImageName(DatabaseReference mDatabase, String coverImageName) {
        this.coverImageName = coverImageName;
        mDatabase.child("Recipes").child(key).child("coverImageName").setValue(coverImageName);
    }
    public void setNumLikes(DatabaseReference mDatabase, int numLikes) {
        this.numLikes = numLikes;
        mDatabase.child("Recipes").child(key).child("numLikes").setValue(numLikes);
    }
    public void setNumCollects(DatabaseReference mDatabase, int numCollects) {
        this.numCollects = numCollects;
        Log.e("set collects", key + Integer.toString(numCollects));
        mDatabase.child("Recipes").child(key).child("numCollects").setValue(numCollects);
    }
    public void setTimeStamp (DatabaseReference mDatabase, String timeStamp) {
        this.timeStamp = timeStamp;
        mDatabase.child("Recipes").child(key).child("timeStamp").setValue(timeStamp);
    }

    // Adder and Deleters
    public void setSteps (DatabaseReference mDatabase, ArrayList<String> steps) {
        this.steps = steps;
        mDatabase.child("Recipes").child(key).child("steps").setValue(steps);
    }
    //need to change if change the ingredient structure -> please delete me after change
    public void addIngredient (DatabaseReference mDatabase, String ingredient) {
        this.ingredients.add(ingredient);
        mDatabase.child("Recipes").child(key).child("ingredient").setValue(ingredient);
    }
    //need to change if change the ingredient structure -> please delete me after change
    public void deleteIngredient (DatabaseReference mDatabase, ArrayList<String> ingredient, String followingID) {
        this.ingredients.remove(ingredient);
        mDatabase.child("Recipes").child(key).child("ingredients").setValue(ingredients); //may be a bug
    }
    public void setIngredients (DatabaseReference mDatabase, ArrayList<String> ingredients) {
        this.ingredients = ingredients;
        mDatabase.child("Recipes").child(key).child("ingredients").setValue(ingredients);
    }
    public void addComment (DatabaseReference mDatabase, ArrayList<String> comment) {
        this.comments.add(comment);
        mDatabase.child("Recipes").child(key).child("ingredient").setValue(comments);
    }
}
