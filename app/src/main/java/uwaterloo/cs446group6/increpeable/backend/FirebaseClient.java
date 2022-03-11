package uwaterloo.cs446group6.increpeable.backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uwaterloo.cs446group6.increpeable.Users.DB_User;
import uwaterloo.cs446group6.increpeable.Users.User;

import java.util.ArrayList;

import uwaterloo.cs446group6.increpeable.Recipe.*;

public class FirebaseClient {
    private static FirebaseClient firebaseClient;

    private static final String LOG_TAG = "Firebase";
    private FileStorageEngine fileStorageEngine;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private int startFrom = 0;

    // Cache
    private uwaterloo.cs446group6.increpeable.Users.DB_User currentUser;
//    private DB_Recipe currentRecipe; we don't need that
    private ArrayList<DB_Recipe> currentRecipes = new ArrayList<>(); // Recipes for this class, can change values
    public ArrayList<Recipe> recipesToReturn = new ArrayList<>(); // Recipes return to the frontend, cannot change values

    private FirebaseClient() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.fileStorageEngine = new FileStorageEngine();
        this.mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseClient getInstance() {
        if (firebaseClient == null) {
            firebaseClient = new FirebaseClient();
        }
        return firebaseClient;
    }

    public void setCurrentDBUser(uwaterloo.cs446group6.increpeable.Users.DB_User user) {
        System.out.println("Setting curernt user in firebase client");
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // use cases: liked recipes, a user's own recipes, collected recipes
    // This is an async function, needs to wait a few seconds after calling it
//    public ArrayList<Recipe> getRecipesByID(ArrayList<String> ids, int arrayLength) {
    public void getRecipesByID(ArrayList<String> ids, int arrayLength) {
        if (!currentRecipes.isEmpty()) { currentRecipes.clear(); } // empty the arraylist
        mDatabase.child("Recipes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting recipes from firebase", task.getException());
                }
                else {
                    DataSnapshot result_ds = task.getResult(); // result_ds is all users in database
                    for (int i = 0; i < arrayLength; i++) {
                        for (DataSnapshot ds : result_ds.getChildren()) {
                            if (ds.child("key").getValue(String.class).equals(ids.get(i))) {
                                currentRecipes.add(ds.getValue(DB_Recipe.class));
                                recipesToReturn.add(ds.getValue(DB_Recipe.class));
                                break;
                            }
                        }
                    }
                    if (recipesToReturn.size() != arrayLength) {
                        Log.i(LOG_TAG, "cannot find some of the recipes by ids");
                    } else {
                        Log.i(LOG_TAG, " recipes saved to the arraylist");
                    }
                }
            }
        });
    }

    // use case: home page gets a list of recommended recipes
    // return a list of recipes. The array size is 3
    // This is an async function, needs to wait a few seconds after calling it
//    public ArrayList<Recipe> getRecommendedRecipes(int size) {
    public void getRecommendedRecipes(int size) {
        // empty the arraylist
        if (!currentRecipes.isEmpty()) {
            currentRecipes.clear();
            recipesToReturn.clear();
        }
        // start to get data from firebase
        mDatabase.child("Recipes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting recipes from firebase", task.getException());
                }
                else {
                    DataSnapshot result_ds = task.getResult(); // result_ds is all users in database
                    int count = 0;
                    for (DataSnapshot ds : result_ds.getChildren()) {
                        if (count < (size + startFrom) && count >= startFrom) {
                            count ++;
                            currentRecipes.add(ds.getValue(DB_Recipe.class));
                            recipesToReturn.add(ds.getValue(DB_Recipe.class));
                        } else if (count < startFrom) {
                            count ++;
                        } else { break; }
                    }
                    startFrom += size;
                    Log.i(LOG_TAG, count + " recipes saved to the arraylist");
                }
            }
        });
    }

    public ArrayList<Recipe> getUpdatedRecipeList() {
        return recipesToReturn;
    }

    // async function. update currentUser
    public void refreshUser() {
        String userid = currentUser.getKey();
        mDatabase.child("UserAccounts").child(userid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting data", task.getException());
                } else {
                    Log.d(LOG_TAG, "refresh User success");
                    currentUser = task.getResult().getValue(DB_User.class);
                }
            }
        });
    }
    // async function. update currentRecipes and recipesToReturn
    public void refreshRecipes() {
        if (currentRecipes.isEmpty()) {
            Log.d(LOG_TAG, "cannot refresh recipes because currentRecipes is empty");
            return;
        }
        ArrayList<String> ids = new ArrayList<>();
        for (DB_Recipe recipe : currentRecipes) {
            ids.add(recipe.getKey());
        }
        getRecipesByID(ids, currentRecipes.size());
    }
    // async function
    public void likePost(String article_id) {
        // increase the numLike in the recipe
        DB_Recipe recipe = new DB_Recipe();
        for (int i = 0; i < currentRecipes.size(); i++) {
            if (article_id.equals(currentRecipes.get(i).getKey())) {
                recipe = currentRecipes.get(i);
                recipe.setNumLikes(mDatabase, recipe.getNumLikes() + 1);
                // update the recioes to return list
                recipesToReturn.remove(i);
                recipesToReturn.add(i, recipe);
                break;
            }
        }
        // update post author numLike
        mDatabase.child("UserAccounts").child(recipe.getAuthorKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting data at likePost", task.getException());
                } else {
                    DB_User tmp = task.getResult().getValue(DB_User.class);
                    tmp.setNumLikes(mDatabase, tmp.getNumLikes() + 1);
                }
            }
        });
    }
    // async function
    public void collectPost(String article_id) {
        // increase the numCollects in the recipe
        DB_Recipe recipe = new DB_Recipe();
        for (int i = 0; i < currentRecipes.size(); i++) {
            if (article_id.equals(currentRecipes.get(i).getKey())) {
                recipe = currentRecipes.get(i);
                recipe.setNumLikes(mDatabase, recipe.getNumCollects() + 1);
                // update the recioes to return list
                recipesToReturn.remove(i);
                recipesToReturn.add(i, recipe);
                break;
            }
        }
        // add the collected recipe to current user's collectedRecipes list
        currentUser.addCollectedPostID(mDatabase, recipe.getKey());
    }
    public void followAuthor(String idToFollow) {
        // add the id of user that the current user wants to follow to its followingIDs array
        currentUser.addFollowingsID(mDatabase, idToFollow);
        // add the id of the currentUser to the idToFollow's follower array
        mDatabase.child("UserAccounts").child(idToFollow).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting data at followAuthor", task.getException());
                } else {
                    DB_User tmp = task.getResult().getValue(DB_User.class);
                    tmp.addFollowersID(mDatabase, currentUser.getKey());
                    // increase the number of followers for the idToFollow user
                    tmp.setNumFollowers(mDatabase, tmp.getNumFollowers() + 1);
                }
            }
        });
    }
    // async function.
    public void createNewPost(Recipe newPost) {
        // empty the arraylist
        if (!currentRecipes.isEmpty()) {
            currentRecipes.clear();
            recipesToReturn.clear();
        }
        // save newPost locally
        DB_Recipe recipe = (DB_Recipe) newPost; // cast Recipe to DB_Recipe
        currentRecipes.add(recipe);
        recipesToReturn.add(newPost);
        // save newPost to firebase
        mDatabase.child("Recipes").child(newPost.getKey()).setValue(newPost);
    }
    // async function
    public void addComment(String postID, String comment, String time, String username) {
        ArrayList<String> newComment = new ArrayList<>();
        newComment.add(comment);
        newComment.add(time);
        newComment.add(username);
        for (DB_Recipe dr : currentRecipes) {
            if (dr.getKey().equals(postID)) {
                dr.addComment(mDatabase, newComment);
            }
        }
    }
}


// example of pushing a new recipe to firebase
//ArrayList<String> steps = new ArrayList<String>();
//steps.add("step 1");
//        steps.add("step 2");
//        ArrayList<ArrayList<String>> ingreds = new ArrayList<ArrayList<String>>();
//ArrayList<String> in1 = new ArrayList<>();
//in1.add("oil");
//in1.add("23");
//in1.add("g");
//ArrayList<String> in2 = new ArrayList<>();
//in2.add("salt");
//in2.add("3");
//in2.add("kg");
//ingreds.add(in1);
//ingreds.add(in2);
//DB_Recipe newrecipe = new DB_Recipe(mDatabase,  mDatabase.push().getKey(), "title", "Waterloo", "description a c d", "-Mx73lr9beDn8OE4WpcS", "image name", steps, ingreds);
//mDatabase.child("Recipes").child(newrecipe.getKey()).setValue(newrecipe);
