package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AlertDialog;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uwaterloo.cs446group6.increpeable.Recipe.DB_Recipe;
import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

class Post {
    private TextView name;
    private TextView intro;
    private TextView likeNum;
    private TextView collectNum;
    private ImageView img;
    private CardView postFrame;
    private Recipe recipe = null;
    private FirebaseClient firebaseClient;

    public Post(FirebaseClient firebaseClient, TextView name, TextView intro, TextView likeNum, TextView collectNum, ImageView img, CardView postFrame){
        this.firebaseClient = firebaseClient;
        this.name = name;
        this.intro = intro;
        this.likeNum = likeNum;
        this.collectNum = collectNum;
        this.img = img;
        this.postFrame = postFrame;
    }

    public void attachRecipe (Recipe recipe) {
        this.recipe = recipe;
    }

    public CardView getFrame(){
        return postFrame;
    }

    public Recipe getRecipe(){
        return recipe;
    }
    public void setUI() {
        if (recipe != null) {
            name.setText(recipe.getTitle());
            intro.setText(recipe.getDescription());
            likeNum.setText(String.valueOf(recipe.getNumLikes()));
            collectNum.setText(String.valueOf(recipe.getNumCollects()));
            firebaseClient.getImageViewByName(img, recipe.getCoverImageName());
        } else {
            Log.d("HomePage", "Recipe Not Attached");
        }
    }
}


public class HomePageActivity extends NotifyActivity {
    private ImageView profile;
    private ImageView createPost;
    private ImageView home;

    private TextView post1Name;
    private TextView post1Intro;
    private TextView post1LikeNum;
    private TextView post1CollectNum;
    private ImageView post1Image;
    private CardView post1Frame;

    private TextView post2Name;
    private TextView post2Intro;
    private TextView post2LikeNum;
    private TextView post2CollectNum;
    private ImageView post2Image;
    private CardView post2Frame;

    private TextView post3Name;
    private TextView post3Intro;
    private TextView post3LikeNum;
    private TextView post3CollectNum;
    private ImageView post3Image;
    private CardView post3Frame;

    private TextView post4Name;
    private TextView post4Intro;
    private TextView post4LikeNum;
    private TextView post4CollectNum;
    private ImageView post4Image;
    private CardView post4Frame;

    private TextView post5Name;
    private TextView post5Intro;
    private TextView post5LikeNum;
    private TextView post5CollectNum;
    private ImageView post5Image;
    private CardView post5Frame;

    private ScrollView scrollBox;
    private EditText searchText;
    private ImageView searchIcon;

    public ArrayList<Recipe> curRecipes;
    public ArrayList<Recipe> searchRecipes;
    public boolean gotFromServer = false;
    public ArrayList<Post> posts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Home Activity", "Enter Here");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        //attach UI to post objects
        post1Name = findViewById(R.id.post1Name);
        post1Intro = findViewById(R.id.post1Intro);
        post1LikeNum = findViewById(R.id.post1LikeNum);
        post1CollectNum = findViewById(R.id.post1CollectNum);
        post1Image = findViewById(R.id.post1Image);
        post1Frame = findViewById(R.id.post1);
        Post post1 = new Post(firebaseClient,post1Name,post1Intro,post1LikeNum,post1CollectNum,post1Image,post1Frame);

        post2Name = findViewById(R.id.post2Name);
        post2Intro = findViewById(R.id.post2Intro);
        post2LikeNum = findViewById(R.id.post2LikeNum);
        post2CollectNum = findViewById(R.id.post2CollectNum);
        post2Image = findViewById(R.id.post2Image);
        post2Frame = findViewById(R.id.post2);
        Post post2 = new Post(firebaseClient,post2Name,post2Intro,post2LikeNum,post2CollectNum,post2Image,post2Frame);

        post3Name = findViewById(R.id.post3Name);
        post3Intro = findViewById(R.id.post3Intro);
        post3LikeNum = findViewById(R.id.post3LikeNum);
        post3CollectNum = findViewById(R.id.post3CollectNum);
        post3Image = findViewById(R.id.post3Image);
        post3Frame = findViewById(R.id.post3);
        Post post3 = new Post(firebaseClient,post3Name,post3Intro,post3LikeNum,post3CollectNum,post3Image,post3Frame);

        post4Name = findViewById(R.id.post4Name);
        post4Intro = findViewById(R.id.post4Intro);
        post4LikeNum = findViewById(R.id.post4LikeNum);
        post4CollectNum = findViewById(R.id.post4CollectNum);
        post4Image = findViewById(R.id.post4Image);
        post4Frame = findViewById(R.id.post4);
        Post post4 = new Post(firebaseClient,post4Name,post4Intro,post4LikeNum,post4CollectNum,post4Image,post4Frame);

        post5Name = findViewById(R.id.post5Name);
        post5Intro = findViewById(R.id.post5Intro);
        post5LikeNum = findViewById(R.id.post5LikeNum);
        post5CollectNum = findViewById(R.id.post5CollectNum);
        post5Image = findViewById(R.id.post5Image);
        post5Frame = findViewById(R.id.post5);
        Post post5 = new Post(firebaseClient,post5Name,post5Intro,post5LikeNum,post5CollectNum,post5Image,post5Frame);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);

        //need to fix here
//        scrollBox = findViewById(R.id.postScollWrapper);
//        scrollBox.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (!scrollBox.canScrollVertically(1)){
//                    refreshHome(posts);
//                }
//            }
//        });

        //set all five post's onclick listener -> transfer to ViewPageActivity
        posts.forEach(curPost -> {
            curPost.getFrame().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe curR = curPost.getRecipe();
                    if (curR != null){
                        Intent goProfileIntent = new Intent(uwaterloo.cs446group6.increpeable.HomePageActivity.this, ViewPageActivity.class);
                        //For Angie: get current recipe directly from firebase client by using: getCurrentRecipe()
                        firebaseClient.setCurrentRecipe(new DB_Recipe(curR));
                        goProfileIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(goProfileIntent, 0);
                    } else {
                        return;
                    }
                }
            });
        });


        //first time to refresh the home Post when login
        refreshHome(posts);


        //search box listener
        searchText = findViewById(R.id.searchText);
        searchIcon = findViewById(R.id.searchIcon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchItem = searchText.getText().toString();
                firebaseClient.getRecipesByKeywords(searchItem);

            }
        });






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

        //this need to change that refresh current page
        home = findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshHome(posts);
            }
        });

    }

    public void refreshHome(ArrayList<Post> posts){
        firebaseClient.getRecommendedRecipes(5);
    }

    @Override
    public void notifyActivity(ReturnFromFunction func_name) {
        switch(func_name) {
            case GET_RECIPES_BY_ID:
                ArrayList<Recipe> currentRecipes = firebaseClient.getCacheRecipePosts();
                System.out.println("in notifyActivity. Here are the async results: " + currentRecipes.get(0).getTitle());
                break;
            case GET_RECOMMENDED_POSTS:
                curRecipes = firebaseClient.getCacheRecipePosts();
                Log.e("Hello", "Hi " + curRecipes.size());
                if (curRecipes.size() >= 5){
                    for (int i = 0; i < 5; i++){
                        posts.get(i).attachRecipe(curRecipes.get(i));
                    }

                    posts.forEach(curPost -> {
                        curPost.setUI();
                    });

                } else {
                    //need to promt msg to user on the bottom
                    new AlertDialog.Builder(HomePageActivity.this)
                            .setTitle("No More Recipes")
                            .setMessage("You Have Reached the Bottom, Please Come Back Later")
                            .setPositiveButton("OK",null)
                            .show();
                    return;
                }
                break;
            case GET_RECIPES_BY_TITLE:
                searchRecipes = firebaseClient.getCacheRecipePosts();
                break;
            default:
                // Default case for all unused functions in firebaseClient
        }
    }
}