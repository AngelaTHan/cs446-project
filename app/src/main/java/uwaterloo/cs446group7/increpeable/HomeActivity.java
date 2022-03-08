package uwaterloo.cs446group7.increpeable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import uwaterloo.cs446group7.increpeable.Recipe.DB_Recipe;
import uwaterloo.cs446group7.increpeable.Recipe.Recipe;
import uwaterloo.cs446group7.increpeable.User.User;
import uwaterloo.cs446group7.increpeable.backend.FirebaseClient;

public class HomeActivity extends AppCompatActivity {
    private ImageView profile;
    private ImageView createPost;

    FirebaseClient firebaseClient;
    User currentUser;
    ArrayList<Recipe> currentRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // bottom bar interactions
        profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfileIntent = new Intent(HomeActivity.this, ProfilePageActivity.class);
                goProfileIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goProfileIntent, 0);
            }
        });

        // create recipe interaction
        createPost = findViewById(R.id.postButton);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(HomeActivity.this, CreatePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });

        // Example: uses firebaseClient to get data
//        firebaseClient = FirebaseClient.getInstance();
//        currentUser = firebaseClient.getCurrentUser();
////        firebaseClient.getRecommendedRecipes(1);
//        ArrayList<String> ids = new ArrayList<>();
//        ids.add("-MxbcLk3zwIqHMUsKeWE");
//        firebaseClient.getRecipesByID(ids, 1);
//        System.out.println("checkpoint 6");
////        try {
////            Thread.sleep(10000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                // yourMethod();
//                currentRecipes = firebaseClient.getUpdatedRecipeList();
//                System.out.println("checkpoint 7");
//                System.out.println("Recipe size " + currentRecipes.size());
//                System.out.println("Recipe content " + currentRecipes.get(0).getTitle());
//            }
//        }, 5000);
////        currentRecipes = firebaseClient.getUpdatedRecipeList();
//        System.out.println("checkpoint 8");


    }
}