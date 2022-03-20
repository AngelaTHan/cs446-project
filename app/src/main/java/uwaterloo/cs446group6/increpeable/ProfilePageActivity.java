package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.Users.User;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;
import uwaterloo.cs446group6.increpeable.TextChangedListener;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

public class ProfilePageActivity extends NotifyActivity {
    private static final int PICK_IMAGE = 100;
    // bottom navigation bar
    private ImageView home;
    private ImageView newPost;

    // user information
    private ImageView profileImage;
    private EditText username;
    private EditText userBio;

    // user stats
    private TextView followingCount;
    private TextView followersCount;
    private TextView likesCount;

    // view options
    private ImageView myPosts;
    private ImageView collections;

    // recipes
    private ImageView post1image;
    private TextView post1title;
    private TextView post1collects;
    private TextView post1likes;
    private String post1id;
    private ImageView post2image;
    private TextView post2title;
    private TextView post2collects;
    private TextView post2likes;
    private String post2id;
    private ImageView post3image;
    private TextView post3title;
    private TextView post3collects;
    private TextView post3likes;
    private String post3id;
    private ImageView post4image;
    private TextView post4title;
    private TextView post4collects;
    private TextView post4likes;
    private String post4id;
    private ImageView post5image;
    private TextView post5title;
    private TextView post5collects;
    private TextView post5likes;
    private String post5id;
    private ImageView post6image;
    private TextView post6title;
    private TextView post6collects;
    private TextView post6likes;
    private String post6id;

    // recipe counter
    int recipeCounter = 0;

    // my recipes
    ArrayList<Recipe> recipes;

    String convertNumber(int num){
        String result;
        if (num >= 1000000000){
            result = String.format("%.1f", num / 1000000000.0) + "B";
        } else if (num >= 1000000){
            result = String.format("%.1f", num / 1000000.0) + "M";
        } else if (num >= 1000){
            result = String.format("%.1f", num / 1000.0) + "K";
        } else {
            result = String.valueOf(num);
        }
        return result;
    }

    void initializeUI() {
        // setup firebase client
        firebaseClient = FirebaseClient.getInstance();
        currentUser = firebaseClient.getCacheUser();
        firebaseClient.setCurrentActivity(this);

        // profile information
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        userBio = findViewById(R.id.userBio);
        followingCount = findViewById(R.id.followingCount);
        followersCount = findViewById(R.id.followersCount);
        likesCount = findViewById(R.id.likesCount);

        // posts
        post1image = findViewById(R.id.post1image);
        post1title = findViewById(R.id.post1title);
        post1collects = findViewById(R.id.post1collects);
        post1likes = findViewById(R.id.post1likes);

        post2image = findViewById(R.id.post2image);
        post2title = findViewById(R.id.post2title);
        post2collects = findViewById(R.id.post2collects);
        post2likes = findViewById(R.id.post2likes);

        post3image = findViewById(R.id.post3image);
        post3title = findViewById(R.id.post3title);
        post3collects = findViewById(R.id.post3collects);
        post3likes = findViewById(R.id.post3likes);

        post4image = findViewById(R.id.post4image);
        post4title = findViewById(R.id.post4title);
        post4collects = findViewById(R.id.post4collects);
        post4likes = findViewById(R.id.post4likes);

        post5image = findViewById(R.id.post5image);
        post5title = findViewById(R.id.post5title);
        post5collects = findViewById(R.id.post5collects);
        post5likes = findViewById(R.id.post5likes);

        post6image = findViewById(R.id.post6image);
        post6title = findViewById(R.id.post6title);
        post6collects = findViewById(R.id.post6collects);
        post6likes = findViewById(R.id.post6likes);

        // load profile information
        Log.w("Profile Page", "set profile information");
        firebaseClient.getImageViewByName(profileImage, currentUser.getProfileImageName());
        username.setText(currentUser.getUsername());
        userBio.setText(currentUser.getDescription());
        followingCount.setText(convertNumber(currentUser.getNumFollowing()));
        followersCount.setText(convertNumber(currentUser.getNumFollowers()));
        likesCount.setText(convertNumber(currentUser.getNumLikes()));
    }

    void loadRecipes(){
        Log.w("Profile Page", "load recipes");

        int size = recipes.size();
        System.out.println("********************* mypostrecipes" + Integer.toString(size));
        int counter = 0;
        if (size >= 6 + recipeCounter){
            firebaseClient.getImageViewByName(post6image, recipes.get(recipeCounter + 5).getCoverImageName());
            post6title.setText(recipes.get(recipeCounter + 5).getTitle());
            post6collects.setText(convertNumber(recipes.get(recipeCounter + 5).getNumCollects()));
            post6likes.setText(convertNumber(recipes.get(recipeCounter + 5).getNumLikes()));
            post6id = recipes.get(recipeCounter + 5).getKey();
            counter++;
        }
        if (size >= 5 + recipeCounter){
            firebaseClient.getImageViewByName(post5image, recipes.get(recipeCounter + 4).getCoverImageName());
            post5title.setText(recipes.get(recipeCounter + 4).getTitle());
            post5collects.setText(convertNumber(recipes.get(recipeCounter + 4).getNumCollects()));
            post5likes.setText(convertNumber(recipes.get(recipeCounter + 4).getNumLikes()));
            post5id = recipes.get(recipeCounter + 4).getKey();
            counter++;
        }
        if (size >= 4 + recipeCounter){
            firebaseClient.getImageViewByName(post4image, recipes.get(recipeCounter + 3).getCoverImageName());
            post4title.setText(recipes.get(recipeCounter + 3).getTitle());
            post4collects.setText(convertNumber(recipes.get(recipeCounter + 3).getNumCollects()));
            post4likes.setText(convertNumber(recipes.get(recipeCounter + 3).getNumLikes()));
            post4id = recipes.get(recipeCounter + 3).getKey();
            counter++;
        }
        if (size >= 3 + recipeCounter){
            firebaseClient.getImageViewByName(post3image, recipes.get(recipeCounter + 2).getCoverImageName());
            post3title.setText(recipes.get(recipeCounter + 2).getTitle());
            post3collects.setText(convertNumber(recipes.get(recipeCounter + 2).getNumCollects()));
            post3likes.setText(convertNumber(recipes.get(recipeCounter + 2).getNumLikes()));
            post3id = recipes.get(recipeCounter + 2).getKey();
            counter++;
        }
        if (size >= 3 + recipeCounter){
            firebaseClient.getImageViewByName(post2image, recipes.get(recipeCounter + 1).getCoverImageName());
            post2title.setText(recipes.get(recipeCounter + 1).getTitle());
            post2collects.setText(convertNumber(recipes.get(recipeCounter + 1).getNumCollects()));
            post2likes.setText(convertNumber(recipes.get(recipeCounter + 1).getNumLikes()));
            post2id = recipes.get(recipeCounter + 1).getKey();
            counter++;
        }
        if (size >= 1 + recipeCounter){
            firebaseClient.getImageViewByName(post1image, recipes.get(recipeCounter + 0).getCoverImageName());
            post1title.setText(recipes.get(recipeCounter + 0).getTitle());
            post1collects.setText(convertNumber(recipes.get(recipeCounter + 0).getNumCollects()));
            post1likes.setText(convertNumber(recipes.get(recipeCounter + 0).getNumLikes()));
            post1id = recipes.get(recipeCounter + 0).getKey();
            counter++;
        }
        recipeCounter += counter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        initializeUI();

        // set my posts
        System.out.println(currentUser.getMyPostIDs());
        firebaseClient.getRecipesByID(currentUser.getMyPostIDs());

        // change profile image
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(GalleryIntent, PICK_IMAGE);
            }
        });

        // change user name or user bio
        username.addTextChangedListener(new TextChangedListener<EditText>(username) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                System.out.println("*********************username changes");
                firebaseClient.setUsername(target.getText().toString());
            }
        });
        userBio.addTextChangedListener(new TextChangedListener<EditText>(userBio) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                System.out.println("*********************userBio changes");
                firebaseClient.setUserDescription(target.getText().toString());
            }
        });

        // bottom bar interactions
        home = findViewById(R.id.home);
        newPost = findViewById(R.id.newPost);
        home.setOnClickListener(new View.OnClickListener() {    // go to homepage
            @Override
            public void onClick(View view) {
                Intent goHomeIntent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
                goHomeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goHomeIntent, 0);
            }
        });
        newPost.setOnClickListener(new View.OnClickListener() { // create new post
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(ProfilePageActivity.this, CreatePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });

        // my post and collection interaction
        myPosts = findViewById(R.id.myPosts);
        collections = findViewById(R.id.collections);
        myPosts.setColorFilter(Color.argb(255, 255, 0, 0));
        collections.setColorFilter(Color.argb(255, 0, 0, 0));
        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collections.setColorFilter(Color.argb(255, 0, 0, 0));
                myPosts.setColorFilter(Color.argb(255, 255, 0, 0));
                firebaseClient.getRecipesByID(currentUser.getMyPostIDs());
            }
        });
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPosts.setColorFilter(Color.argb(255, 0, 0, 0));
                collections.setColorFilter(Color.argb(255, 255, 0, 0));
                firebaseClient.getRecipesByID(currentUser.getCollectedPostIDs());
            }
        });

        // view post
        post1image = findViewById(R.id.post1image);
        post1image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goViewPostIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
            String imageID = UUID.randomUUID().toString();
            firebaseClient.uploadImageView(profileImage, imageID);
            firebaseClient.setProfileImageName(imageID + ".jpg");
        }
    }

    @Override
    public void notifyActivity(ReturnFromFunction func_name) {
        switch(func_name) {
            case GET_RECIPES_BY_ID:
                recipes = firebaseClient.getCacheRecipePosts();
                System.out.println("in notifyActivity. Here are the async results: " + recipes.get(0).getTitle());
                loadRecipes();
                break;
            case GET_RECOMMENDED_POSTS:
                // Upload UI
                break;
            default:
                // Default case for all unused functions in firebaseClient
        }
    }
}