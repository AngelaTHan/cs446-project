package uwaterloo.cs446group7.increpeable.Recipe;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Recipe {
    protected String key;
    protected String title;
    protected String location;
    protected String description;
    protected String authorKey; // use this to get the corresponding user object
    protected String coverImageName;
    protected Integer numLikes = 0;
    protected Integer numCollects = 0;
    protected String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

    protected ArrayList<String> steps = new ArrayList<>();
    protected ArrayList<ArrayList<String>> ingredients = new ArrayList<>(); // Array of <amount, unit, ingredient>
    protected ArrayList<ArrayList<String>> comments = new ArrayList<>(); // Array of <username, comment>

    // constructor
    public Recipe() {}
    public Recipe(String key, String title, String location, String description, String authorKey, String getCoverImageName, ArrayList<String> steps, ArrayList<ArrayList<String>> ingredients) {
        this.key = key;
        this.title = title;
        this.location = location;
        this.description = description;
        this.authorKey = authorKey;
        this.coverImageName = getCoverImageName;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    // getters
    public String getKey() { return key; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getCoverImageName () { return coverImageName;}
    public String getAuthorKey () { return authorKey;}
    public int getNumLikes () { return numLikes;}
    public int getNumCollects () { return numCollects;}
    public String timeStamp() { return timeStamp; }
    public ArrayList<String> steps() { return steps; }
    public ArrayList<ArrayList<String>> getIngredients() { return ingredients; }
    public ArrayList<ArrayList<String>> getComments() { return comments; }
}
