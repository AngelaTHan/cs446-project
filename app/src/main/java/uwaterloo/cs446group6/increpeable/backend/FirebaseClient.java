package uwaterloo.cs446group6.increpeable.backend;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uwaterloo.cs446group6.increpeable.databaseClasses.User;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import android.os.Handler;

import uwaterloo.cs446group7.increpeable.HomeActivity;
import uwaterloo.cs446group7.increpeable.MainActivity;
import uwaterloo.cs446group7.increpeable.Recipe.DB_Recipe;
import uwaterloo.cs446group7.increpeable.Recipe.Recipe;
import uwaterloo.cs446group7.increpeable.User.DB_User;
import uwaterloo.cs446group7.increpeable.User.User;

public class FirebaseClient {
    private static FirebaseClient firebaseClient;

    private static final String LOG_TAG = "Firebase";
    private FileStorageEngine fileStorageEngine;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private int startFrom = 0;

    // Cache
    private DB_User currentUser;
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

    public void setCurrentDBUser(DB_User user) {
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
//    public void refreshUser(String userid) {
//        DatabaseReference tmp = mDatabase.child("UserAccounts").child(userid);
//    }
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
