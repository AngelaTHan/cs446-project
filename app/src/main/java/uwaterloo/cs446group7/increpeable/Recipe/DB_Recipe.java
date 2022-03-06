package uwaterloo.cs446group7.increpeable.Recipe;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DB_Recipe extends Recipe {
    // constructor
    public DB_Recipe() {}
    public DB_Recipe (DatabaseReference mDatabase, String key, String title, String location, String description, String authorKey, String getCoverImageName, ArrayList<String> steps, ArrayList<ArrayList<String>> ingredients) {
        super(key, title, location, description, authorKey, getCoverImageName, steps, ingredients);
        mDatabase.child("recipe").child(key).setValue(this);
    }

    // setters
    public void setTitle(DatabaseReference mDatabase, String title) {
        this.title = title;
        mDatabase.child("recipe").child(key).child("title").setValue(title);
    }
    public void setDescription(DatabaseReference mDatabase, String description) {
        this.description = description;
        mDatabase.child("recipe").child(key).child("description").setValue(description);
    }
    public void setCoverImageName(DatabaseReference mDatabase, String coverImageName) {
        this.coverImageName = coverImageName;
        mDatabase.child("recipe").child(key).child("coverImageName").setValue(coverImageName);
    }
    public void setNumLikes(DatabaseReference mDatabase, int numLikes) {
        this.numLikes = numLikes;
        mDatabase.child("recipe").child(key).child("numLikes").setValue(numLikes);
    }
    public void setNumCollects(DatabaseReference mDatabase, int numCollects) {
        this.numCollects = numCollects;
        mDatabase.child("recipe").child(key).child("description").setValue(numCollects);
    }
    public void setTimeStamp (DatabaseReference mDatabase, String timeStamp) {
        this.timeStamp = timeStamp;
        mDatabase.child("recipe").child(key).child("timeStamp").setValue(timeStamp);
    }

    // Adder and Deleters
    public void setSteps (DatabaseReference mDatabase, ArrayList<String> steps) {
        this.steps = steps;
        mDatabase.child("recipe").child(key).child("steps").setValue(steps);
    }
    public void addIngredient (DatabaseReference mDatabase, ArrayList<String> ingredient) {
        this.ingredients.add(ingredient);
        mDatabase.child("recipe").child(key).child("ingredient").setValue(ingredient);
    }
    public void deleteIngredient (DatabaseReference mDatabase, ArrayList<String> ingredient, String followingID) {
        this.ingredients.remove(ingredient);
        mDatabase.child("recipe").child(key).child("ingredients").setValue(ingredients);
    }
    public void addComment (DatabaseReference mDatabase, ArrayList<String> comment) {
        this.comments.add(comment);
        mDatabase.child("recipe").child(key).child("ingredient").setValue(comments);
    }
}
