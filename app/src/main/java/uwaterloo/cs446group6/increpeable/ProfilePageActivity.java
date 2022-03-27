package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Recipe.DB_Recipe;
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
    private ImageView profile;

    // scrollview
    private ScrollView posts;
    LinearLayout row1;
    LinearLayout row2;
    LinearLayout row3;

    // recipes
    private LinearLayout post1;
    private LinearLayout post2;
    private LinearLayout post3;
    private LinearLayout post4;
    private LinearLayout post5;
    private LinearLayout post6;
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
    Boolean onMyPost = true;
    int postsLoaded = 0;
    int recipeCounter = 0;
    int mypostCounter = 0;
    int collectedCounter = 0;

    // my recipes
    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<Recipe> myPost = new ArrayList<>();
    ArrayList<Recipe> collection = new ArrayList<>();

    public int dpToPx(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    private void initializeUI() {
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

        // scroll
        posts = findViewById(R.id.scrollView);
        row1 = findViewById(R.id.row1);
        row2 = findViewById(R.id.row2);
        row3 = findViewById(R.id.row3);

        // posts
        post1 = findViewById(R.id.post1);
        post1image = findViewById(R.id.post1image);
        post1title = findViewById(R.id.post1title);
        post1collects = findViewById(R.id.post1collects);
        post1likes = findViewById(R.id.post1likes);

        post2 = findViewById(R.id.post2);
        post2image = findViewById(R.id.post2image);
        post2title = findViewById(R.id.post2title);
        post2collects = findViewById(R.id.post2collects);
        post2likes = findViewById(R.id.post2likes);

        post3 = findViewById(R.id.post3);
        post3image = findViewById(R.id.post3image);
        post3title = findViewById(R.id.post3title);
        post3collects = findViewById(R.id.post3collects);
        post3likes = findViewById(R.id.post3likes);

        post4 = findViewById(R.id.post4);
        post4image = findViewById(R.id.post4image);
        post4title = findViewById(R.id.post4title);
        post4collects = findViewById(R.id.post4collects);
        post4likes = findViewById(R.id.post4likes);

        post5 = findViewById(R.id.post5);
        post5image = findViewById(R.id.post5image);
        post5title = findViewById(R.id.post5title);
        post5collects = findViewById(R.id.post5collects);
        post5likes = findViewById(R.id.post5likes);

        post6 = findViewById(R.id.post6);
        post6image = findViewById(R.id.post6image);
        post6title = findViewById(R.id.post6title);
        post6collects = findViewById(R.id.post6collects);
        post6likes = findViewById(R.id.post6likes);

        // bottom bar interactions
        home = findViewById(R.id.home);
        newPost = findViewById(R.id.newPost);
        profile = findViewById(R.id.profile);

        // my posts and collection button
        myPosts = findViewById(R.id.myPosts);
        collections = findViewById(R.id.collections);
        myPosts.setColorFilter(Color.argb(255, 255, 0, 0));
        collections.setColorFilter(Color.argb(255, 0, 0, 0));

        // load profile information
        Log.w("Profile Page", "set profile information");
        firebaseClient.getImageViewByName(profileImage, currentUser.getProfileImageName());
        username.setText(currentUser.getUsername());
        userBio.setText(currentUser.getDescription());
        followingCount.setText(Util.convertNumber(currentUser.getNumFollowing()));
        followersCount.setText(Util.convertNumber(currentUser.getNumFollowers()));
        likesCount.setText(Util.convertNumber(currentUser.getNumLikes()));
    }

    private void loadRecipes(){
        Log.w("Profile Page", "load recipes");

        postsLoaded = 0;

        int size = recipes.size();
        System.out.println("*********************" + Integer.toString(size));
        int counter = 0;
        if (size >= 6 + recipeCounter){
            post6.setVisibility(View.VISIBLE);
            firebaseClient.getImageViewByName(post6image, recipes.get(recipeCounter + 5).getCoverImageName());
            post6title.setText(recipes.get(recipeCounter + 5).getTitle());
            post6collects.setText(Util.convertNumber(recipes.get(recipeCounter + 5).getNumCollects()));
            post6likes.setText(Util.convertNumber(recipes.get(recipeCounter + 5).getNumLikes()));
            post6id = recipes.get(recipeCounter + 5).getKey();
            counter++;
        } else {
            post6.setVisibility(View.GONE);
        }
        if (size >= 5 + recipeCounter){
            ViewGroup.LayoutParams params = row3.getLayoutParams();
            params.height = dpToPx(250);
            row3.setLayoutParams(params);
            post5.setVisibility(View.VISIBLE);
            firebaseClient.getImageViewByName(post5image, recipes.get(recipeCounter + 4).getCoverImageName());
            post5title.setText(recipes.get(recipeCounter + 4).getTitle());
            post5collects.setText(Util.convertNumber(recipes.get(recipeCounter + 4).getNumCollects()));
            post5likes.setText(Util.convertNumber(recipes.get(recipeCounter + 4).getNumLikes()));
            post5id = recipes.get(recipeCounter + 4).getKey();
            counter++;
        } else {
            ViewGroup.LayoutParams params = row3.getLayoutParams();
            params.height = dpToPx(0);
            row3.setLayoutParams(params);
            post5.setVisibility(View.GONE);
        }
        if (size >= 4 + recipeCounter){
            post4.setVisibility(View.VISIBLE);
            firebaseClient.getImageViewByName(post4image, recipes.get(recipeCounter + 3).getCoverImageName());
            post4title.setText(recipes.get(recipeCounter + 3).getTitle());
            post4collects.setText(Util.convertNumber(recipes.get(recipeCounter + 3).getNumCollects()));
            post4likes.setText(Util.convertNumber(recipes.get(recipeCounter + 3).getNumLikes()));
            post4id = recipes.get(recipeCounter + 3).getKey();
            counter++;
        } else {
            post4.setVisibility(View.GONE);
        }
        if (size >= 3 + recipeCounter){
            ViewGroup.LayoutParams params = row2.getLayoutParams();
            params.height = dpToPx(250);
            row3.setLayoutParams(params);
            post3.setVisibility(View.VISIBLE);
            firebaseClient.getImageViewByName(post3image, recipes.get(recipeCounter + 2).getCoverImageName());
            post3title.setText(recipes.get(recipeCounter + 2).getTitle());
            post3collects.setText(Util.convertNumber(recipes.get(recipeCounter + 2).getNumCollects()));
            post3likes.setText(Util.convertNumber(recipes.get(recipeCounter + 2).getNumLikes()));
            post3id = recipes.get(recipeCounter + 2).getKey();
            counter++;
        } else {
            ViewGroup.LayoutParams params = row2.getLayoutParams();
            params.height = dpToPx(0);
            row3.setLayoutParams(params);
            post3.setVisibility(View.GONE);
        }
        if (size >= 2 + recipeCounter){
            post2.setVisibility(View.VISIBLE);
            firebaseClient.getImageViewByName(post2image, recipes.get(recipeCounter + 1).getCoverImageName());
            post2title.setText(recipes.get(recipeCounter + 1).getTitle());
            post2collects.setText(Util.convertNumber(recipes.get(recipeCounter + 1).getNumCollects()));
            post2likes.setText(Util.convertNumber(recipes.get(recipeCounter + 1).getNumLikes()));
            post2id = recipes.get(recipeCounter + 1).getKey();
            counter++;
        } else {
            post2.setVisibility(View.GONE);
        }
        if (size >= 1 + recipeCounter){
            ViewGroup.LayoutParams params = row1.getLayoutParams();
            params.height = dpToPx(250);
            row3.setLayoutParams(params);
            post1.setVisibility(View.VISIBLE);
            firebaseClient.getImageViewByName(post1image, recipes.get(recipeCounter + 0).getCoverImageName());
            post1title.setText(recipes.get(recipeCounter + 0).getTitle());
            post1collects.setText(Util.convertNumber(recipes.get(recipeCounter + 0).getNumCollects()));
            post1likes.setText(Util.convertNumber(recipes.get(recipeCounter + 0).getNumLikes()));
            post1id = recipes.get(recipeCounter + 0).getKey();
            counter++;
        } else {
            ViewGroup.LayoutParams params = row1.getLayoutParams();
            params.height = dpToPx(0);
            row3.setLayoutParams(params);
            post1.setVisibility(View.GONE);
        }
        postsLoaded += counter;
        posts.scrollTo(0,0);
    }

    private void setUserInformationListeners() {
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
    }

    private void setBottomBarListeners() {
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
        profile.setOnClickListener(new View.OnClickListener() { // refresh profile page
            @Override
            public void onClick(View view) {
                if (onMyPost){
                    firebaseClient.getRecipesByID(currentUser.getMyPostIDs());
                    mypostCounter = 0;
                } else {
                    firebaseClient.getRecipesByID(currentUser.getCollectedPostIDs());
                    collectedCounter = 0;
                }
                recipeCounter = 0;
            }
        });
    }

    private void setViewOptionListeners() {
        // my post and collection interaction
        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change icon colour
                collections.setColorFilter(Color.argb(255, 0, 0, 0));
                myPosts.setColorFilter(Color.argb(255, 255, 0, 0));
                System.out.println("$$$$$mypost " + currentUser.getMyPostIDs());

                // set variables
                collection.clear();
                collection.addAll(recipes);
                onMyPost = true;
                collectedCounter = recipeCounter;
                recipeCounter = mypostCounter;

                // load posts
                if (myPost == null ||  myPost.size() == 0) {   // reduce calls to database
                    firebaseClient.getRecipesByID(currentUser.getMyPostIDs());
                } else {
                    recipes.clear();
                    recipes.addAll(myPost);
                    loadRecipes();
                }
            }
        });
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change icon colour
                myPosts.setColorFilter(Color.argb(255, 0, 0, 0));
                collections.setColorFilter(Color.argb(255, 255, 0, 0));
                System.out.println("$$$$$collect " + currentUser.getCollectedPostIDs());

                // set variables
                myPost.clear();
                myPost.addAll(recipes);
                onMyPost = false;
                mypostCounter = recipeCounter;
                recipeCounter = collectedCounter;

                // load posts
                if (collection == null ||  collection.size() == 0) {   // reduce calls to database
                    firebaseClient.getRecipesByID(currentUser.getCollectedPostIDs());
                } else {
                    recipes.clear();
                    recipes.addAll(collection);
                    loadRecipes();
                }
            }
        });
    }

    private class TouchListenerImpl implements View.OnTouchListener {
        int touchBottom = 0;
        int touchTop = 0;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    int scrollY = view.getScrollY();
                    int height = view.getHeight();
                    int scrollViewMeasuredHeight = posts.getChildAt(0).getMeasuredHeight();
                    //Touch Top
                    if (scrollY == 0) {
                        touchBottom = 0;
                        touchTop++;
                        Log.e("Hi Top","Touch Top"+ touchTop);
                        if (touchTop >= 15){
                            touchTop = 0;
                            if (recipeCounter - 6 >= 0){
                                recipeCounter -= 6;
                                loadRecipes();
                            }
                        }
                    }
                    //Touch Bottom
                    if ((scrollY + height) == scrollViewMeasuredHeight) {
                        touchTop = 0;
                        touchBottom++;
                        Log.e("Hi Bottom","Touch Bottom"+ touchBottom);
                        if (touchBottom >= 15){
                            touchBottom = 0;
                            Log.e("load posts", String.valueOf(recipes.size()));
                            if (recipeCounter + postsLoaded < recipes.size()){
                                Log.e("load posts", "successful");
                                recipeCounter += postsLoaded;
                                loadRecipes();
                            }
                        }

                    }
                    break;

                default:
                    break;
            }
            return false;
        }
    }

    private void setViewPostListeners(){
        // view post
        post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postsLoaded >= 1) {
                    Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                    goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    firebaseClient.setCurrentRecipe(new DB_Recipe(recipes.get(recipeCounter)));
                    startActivityIfNeeded(goViewPostIntent, 0);
                }
            }
        });
        post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postsLoaded >= 2) {
                    Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                    goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    firebaseClient.setCurrentRecipe(new DB_Recipe(recipes.get(recipeCounter+1)));
                    startActivityIfNeeded(goViewPostIntent, 0);
                }
            }
        });
        post3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postsLoaded >= 3) {
                    Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                    goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    firebaseClient.setCurrentRecipe(new DB_Recipe(recipes.get(recipeCounter+2)));
                    startActivityIfNeeded(goViewPostIntent, 0);
                }
            }
        });
        post4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postsLoaded >= 4) {
                    Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                    goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    firebaseClient.setCurrentRecipe(new DB_Recipe(recipes.get(recipeCounter+3)));
                    startActivityIfNeeded(goViewPostIntent, 0);
                }
            }
        });
        post5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postsLoaded >= 5) {
                    Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                    goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    firebaseClient.setCurrentRecipe(new DB_Recipe(recipes.get(recipeCounter+4)));
                    startActivityIfNeeded(goViewPostIntent, 0);
                }
            }
        });
        post6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postsLoaded >= 2) {
                    Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                    goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    firebaseClient.setCurrentRecipe(new DB_Recipe(recipes.get(recipeCounter+5)));
                    startActivityIfNeeded(goViewPostIntent, 0);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        initializeUI();

        // set my posts
        System.out.println(currentUser.getMyPostIDs());
        firebaseClient.getRecipesByID(currentUser.getMyPostIDs());

        // set up listeners
        setUserInformationListeners();
        setBottomBarListeners();
        setViewOptionListeners();
        posts.setOnTouchListener(new TouchListenerImpl());
        setViewPostListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // pick profile picture from gallery successful
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
                recipes.clear();
                recipes.addAll(firebaseClient.getCacheRecipePosts());
                System.out.println("in notifyActivity. Here are the async results: " + recipes.size());
                loadRecipes();
                break;
            default:
                break;
                // Default case for all unused functions in firebaseClient
        }
    }
}