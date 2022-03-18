package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

public class HomePageActivity extends NotifyActivity {
    private ImageView profile;
    private ImageView createPost;
    private ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        // bottom bar interactions
        profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfileIntent = new Intent(uwaterloo.cs446group6.increpeable.HomePageActivity.this, ProfilePageActivity.class);
                goProfileIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goProfileIntent, 0);
            }
        });

        // create recipe interaction
        createPost = findViewById(R.id.postButton);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(uwaterloo.cs446group6.increpeable.HomePageActivity.this, CreatePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });

        home = findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(uwaterloo.cs446group6.increpeable.HomePageActivity.this, uploadPost.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });

        // Todo for every Activity:

        // 1. Change Inheritance. See line 13
        // - Before:   HomePageActivity inherits AppCompatActivity
        // - Now:      HomePageActivity inherits NotifyActivity, and NotifyActivity inherits AppCompatActivity
        // - Benefit:  You will now have the some useful local variables. Please check NotifyActivity for details

        // 2. Try out the following example to fetch something asynchronous data
        // - first you must call firebaseClient.setCurrentActivity(this)
        //   - setCurrentActivity is already called in onCreate() in NotifyActivity; may need to call in onResume, etc.
        // - start by making an async call (e.g. firebaseClient.getRecipesByID(ids)
        // - after the call, do nothing and wait for it to finish
        // - once it finishes, it will call your notifyActivity() with an enum for the name of the function (see line 78)
        // - modify your notifyActivity function to handle the result from firebaseClient

        // 3. Some more notes
        // - some of the functions in firebaseClient are synchronized, others are synchronized.
        // - some of the async functions also do not call notifyActivity since it's not needed.
        // - please check FirebaseClient for more details.


        // Example 1 - perform an async call - get some recipes by id from firebase
        // At login page, use username = lichen@gmail.com and password = abc123.
        // A successful login will open the HomePageActivity, call this onCreate functions, and run the following:
        ArrayList<String> ids = new ArrayList<>();
        ids.add("-MxbcLk3zwIqHMUsKeWE");
        firebaseClient.setCurrentActivity(this); // this is already called in NotifyActivity's onCreate. You might need this in onResume, etc.
        firebaseClient.getRecipesByID(ids);
        // Rest of the example is in notifyActivity()

        // Example 2 - create and upload a new recipe
        // Recipe tmp = new Recipe(...all hardcoded information)
        // firebaseClient.createNewPost(tmp);
    }

    @Override
    public void notifyActivity(ReturnFromFunction func_name) {
        switch(func_name) {
            case GET_RECIPES_BY_ID:
                ArrayList<Recipe> currentRecipes = firebaseClient.getCacheRecipePosts();
                System.out.println("in notifyActivity. Here are the async results: " + currentRecipes.get(0).getTitle());
                break;
            case GET_RECOMMENDED_POSTS:
                // Upload UI
                break;
            default:
                // Default case for all unused functions in firebaseClient
        }
    }
}