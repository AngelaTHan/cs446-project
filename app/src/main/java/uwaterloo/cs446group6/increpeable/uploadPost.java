package uwaterloo.cs446group6.increpeable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;

public class uploadPost extends NotifyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        String key = UUID.randomUUID().toString();
        String title = "Beef Wellington";
        String location = "London, UK";
        String description = "A traditional Beef Wellington consists of a beef tenderloin wrapped in layers of pâté, duxelles (a finely chopped mushroom mixture), parma ham, and puff pastry, then baked. Rumor has it that Beef Wellington got its name from Arthur Wellesley, the 1st Duke of Wellington, who counted the dish among his favorite recipes.";
        String authorKey = currentUser.getKey();
        String getCoverImageName  = key + ".jpeg";
        ArrayList<String> steps = new ArrayList<>();
        //steps.add();
        ArrayList<ArrayList<String>> ingredients = new ArrayList<>(); // Array of <amount, ingredient, unit> <5, salt, g>
        ArrayList<String> i1 = new ArrayList<>();
        //i1.add();
        ingredients.add(i1);

        Recipe curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);

        firebaseClient.createNewPost(curR);
        String imageID = getCoverImageName;
        //firebaseClient.uploadImageView(profileImage, imageID);
        //firebaseClient.setRecipeCoverImageName(key, imageID);



    }
}