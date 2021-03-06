package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Recipe.DB_Recipe;
import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.Users.User;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

public class ViewProfileActivity extends NotifyActivity {
    private static final int PICK_IMAGE = 100;
    private User viewUser;

    // bottom navigation bar
    private ImageView home;
    private ImageView newPost;
    private ImageView profile;

    // user information
    private ImageView profileImage;
    private EditText username;
    private EditText userBio;

    // user stats
    private TextView followingCount;
    private TextView followersCount;
    private TextView likesCount;

    // view options
    private Button followButton;
    private Boolean hasFollowed;
    private ColorStateList redStateList;
    private ColorStateList darkGrayStateList;

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
    private ImageView post2image;
    private TextView post2title;
    private TextView post2collects;
    private TextView post2likes;
    private ImageView post3image;
    private TextView post3title;
    private TextView post3collects;
    private TextView post3likes;
    private ImageView post4image;
    private TextView post4title;
    private TextView post4collects;
    private TextView post4likes;
    private ImageView post5image;
    private TextView post5title;
    private TextView post5collects;
    private TextView post5likes;
    private ImageView post6image;
    private TextView post6title;
    private TextView post6collects;
    private TextView post6likes;

    // recipe counter
    Boolean onMyPost = true;
    int postsLoaded = 0;
    int recipeCounter = 0;
    int mypostCounter = 0;
    int collectedCounter = 0;

    // my recipes
    ArrayList<Recipe> recipes = new ArrayList<>();

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

        // colors
        redStateList = ContextCompat.getColorStateList(this, R.color.red);
        darkGrayStateList = ContextCompat.getColorStateList(this, R.color.dark_gray);

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

        // follow button
        followButton = findViewById(R.id.followButton);
        viewUser = firebaseClient.getCurrentRecipeAuthor();
        hasFollowed = currentUser.getFollowingIDs().contains(viewUser.getKey());
        if (hasFollowed) {
            followButton.setText("Following");
            followButton.setBackgroundTintList(darkGrayStateList);
        } else {
            followButton.setText("Follow");
            followButton.setBackgroundTintList(redStateList);
        }

        // load profile information
        Log.w("Profile Page", "set profile information");
        firebaseClient.getImageViewByName(profileImage, viewUser.getProfileImageName());
        username.setText(viewUser.getUsername());
        userBio.setText(viewUser.getDescription());
        followingCount.setText(Util.convertNumber(viewUser.getNumFollowing()));
        followersCount.setText(Util.convertNumber(viewUser.getNumFollowers()));
        likesCount.setText(Util.convertNumber(viewUser.getNumLikes()));
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

    private void setBottomBarListeners() {
        home.setOnClickListener(new View.OnClickListener() {    // go to homepage
            @Override
            public void onClick(View view) {
                Intent goHomeIntent = new Intent(ViewProfileActivity.this, HomePageActivity.class);
                goHomeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goHomeIntent, 0);
            }
        });
        newPost.setOnClickListener(new View.OnClickListener() { // create new post
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(ViewProfileActivity.this, CreatePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() { // refresh profile page
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(ViewProfileActivity.this, ProfilePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });
    }

    private void setFollowListeners() {
        // follow button
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (hasFollowed) {
                    followButton.setText("Follow");
                    followButton.setBackgroundTintList(redStateList);
                    hasFollowed = false;
                } else {
                    followButton.setText("Following");
                    followButton.setBackgroundTintList(darkGrayStateList);
                    hasFollowed = true;
                }
                firebaseClient.modifyFollowAuthor(viewUser.getKey(), hasFollowed);
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
                    Intent goViewPostIntent = new Intent(ViewProfileActivity.this, ViewPageActivity.class);
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
                    Intent goViewPostIntent = new Intent(ViewProfileActivity.this, ViewPageActivity.class);
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
                    Intent goViewPostIntent = new Intent(ViewProfileActivity.this, ViewPageActivity.class);
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
                    Intent goViewPostIntent = new Intent(ViewProfileActivity.this, ViewPageActivity.class);
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
                    Intent goViewPostIntent = new Intent(ViewProfileActivity.this, ViewPageActivity.class);
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
                    Intent goViewPostIntent = new Intent(ViewProfileActivity.this, ViewPageActivity.class);
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

        setContentView(R.layout.activity_viewprofile);

        initializeUI();

        // set my posts
        System.out.println(currentUser.getMyPostIDs());
        firebaseClient.getRecipesByID(currentUser.getMyPostIDs());

        // set up listeners
        setBottomBarListeners();
        setFollowListeners();
        posts.setOnTouchListener(new TouchListenerImpl());
        setViewPostListeners();
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