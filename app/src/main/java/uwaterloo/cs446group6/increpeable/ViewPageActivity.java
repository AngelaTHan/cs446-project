package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.Users.User;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

public class ViewPageActivity extends NotifyActivity {
    private Boolean hasFollowed;
    private Boolean hasLiked;
    private Boolean hasCollected;

    private Recipe currentRecipe;
    private User currentRecipeAuthor;

    private ImageButton backButton;
    private ImageButton editPostButton;
    private ImageView viewPostAuthorProfileView;
    private Button followButton;
    private LinearLayout likeButton;
    private ImageView likeIcon;
    private LinearLayout collectButton;
    private ImageView collectIcon;

    private ColorStateList redStateList;
    private ColorStateList darkGrayStateList;
    private ColorStateList yellowStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // buttons
        backButton = findViewById(R.id.backFromView);
        editPostButton = findViewById(R.id.editPost);
        viewPostAuthorProfileView = findViewById(R.id.viewPostAuthorProfile);
        followButton = findViewById(R.id.follow);
        likeButton = findViewById(R.id.like);
        likeIcon = findViewById(R.id.likeIcon);
        collectButton = findViewById(R.id.collect);
        collectIcon = findViewById(R.id.collectIcon);

        // colors
        redStateList = ContextCompat.getColorStateList(this, R.color.red);
        darkGrayStateList = ContextCompat.getColorStateList(this, R.color.dark_gray);
        yellowStateList = ContextCompat.getColorStateList(this, R.color.yellow);

        currentRecipe = firebaseClient.getCurrentRecipe();
        firebaseClient.setCurrentRecipeAuthor();
        if (currentRecipe.getAuthorKey().equals(currentUser.getKey())) {
            editPostButton.setVisibility(View.VISIBLE);
            followButton.setVisibility(View.GONE);
        }

        // load recipe header
        ImageView imageCover = findViewById(R.id.viewPostImage);
        firebaseClient.getImageViewByName(imageCover, currentRecipe.getCoverImageName());
        TextView recipeTitle = findViewById(R.id.viewPostTitle);
        recipeTitle.setText(currentRecipe.getTitle());
        TextView recipeLikesAndCollects = findViewById(R.id.viewPostLikesAndCollects);
        recipeLikesAndCollects.setText("Likes " + Util.convertNumber(currentRecipe.getNumLikes()) + "      Collects " + Util.convertNumber(currentRecipe.getNumCollects()));
        TextView recipeLocation = findViewById(R.id.viewPostLocation);
        recipeLocation.setText(currentRecipe.getLocation());

        // Load recipe details
        TextView recipeDescription = findViewById(R.id.viewPostDescription);
        recipeDescription.setText(currentRecipe.getDescription());

        ArrayList<String> ingredients = currentRecipe.getIngredients();
        LinearLayout recipeIngredients = findViewById(R.id.viewPostIngredients);
        for (String ingredient : ingredients) {
            TextView textView = new TextView(this);
            textView.setText(ingredient);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            recipeIngredients.addView(textView);
        }

        ArrayList<String> steps = currentRecipe.getSteps();
        LinearLayout recipeSteps = findViewById(R.id.viewPostSteps);
        for (int counter = 0; counter < steps.size(); counter++) {
            TextView headerView = new TextView(this);
            headerView.setText("Step " + String.valueOf(counter+1));
            headerView.setTextColor(Color.BLACK);
            headerView.setTypeface(null, Typeface.BOLD);
            headerView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            recipeSteps.addView(headerView);

            TextView textView = new TextView(this);
            textView.setText(steps.get(counter)+'\n');
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            recipeSteps.addView(textView);
        }

        // initialize booleans
        hasLiked = currentUser.getLikedPostIDs().contains(currentRecipe.getKey());
        hasCollected = currentUser.getCollectedPostIDs().contains(currentRecipe.getKey());

        // back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                finish();
            }
        });

        // edit button
        editPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent goEditPostIntent = new Intent(ViewPageActivity.this, EditPageActivity.class);
                goEditPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goEditPostIntent, 0);
                finish();
            }
        });

        // profile image
        viewPostAuthorProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent goProfilePostIntent = new Intent(ViewPageActivity.this, ViewProfileActivity.class);
                goProfilePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goProfilePostIntent, 0);
                finish();
            }
        });

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
                firebaseClient.modifyFollowAuthor(currentRecipeAuthor.getKey(), hasFollowed);
                currentRecipe = firebaseClient.getCurrentRecipe();
            }
        });

        // like button
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (hasLiked) {
                    likeIcon.setBackgroundResource(R.drawable.like);
                    likeIcon.setBackgroundTintList(darkGrayStateList);
                    hasLiked = false;
                } else {
                    likeIcon.setBackgroundResource(R.drawable.like_solid);
                    likeIcon.setBackgroundTintList(redStateList);
                    hasLiked = true;
                }
                firebaseClient.modifyLikePost(currentRecipe.getKey(), hasLiked);
                currentRecipe = firebaseClient.getCurrentRecipe();
                recipeLikesAndCollects.setText("Likes " + Util.convertNumber(currentRecipe.getNumLikes()) + "      Collects " + Util.convertNumber(currentRecipe.getNumCollects()));
            }
        });

        // collect button
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (hasCollected) {
                    collectIcon.setBackgroundResource(R.drawable.collect);
                    collectIcon.setBackgroundTintList(darkGrayStateList);
                    hasCollected = false;
                } else {
                    collectIcon.setBackgroundResource(R.drawable.collect_solid);
                    collectIcon.setBackgroundTintList(yellowStateList);
                    hasCollected = true;
                }
                firebaseClient.modifyCollectPost(currentRecipe.getKey(), hasCollected);
                currentRecipe = firebaseClient.getCurrentRecipe();
                recipeLikesAndCollects.setText("Likes " + Util.convertNumber(currentRecipe.getNumLikes()) + "      Collects " + Util.convertNumber(currentRecipe.getNumCollects()));
            }
        });

        // initialized buttons
        if (hasLiked) {
            likeIcon.setBackgroundResource(R.drawable.like_solid);
            likeIcon.setBackgroundTintList(redStateList);
        } else {
            likeIcon.setBackgroundResource(R.drawable.like);
            likeIcon.setBackgroundTintList(darkGrayStateList);
        }
        if (hasCollected) {
            collectIcon.setBackgroundResource(R.drawable.collect_solid);
            collectIcon.setBackgroundTintList(yellowStateList);
        } else {
            collectIcon.setBackgroundResource(R.drawable.collect);
            collectIcon.setBackgroundTintList(darkGrayStateList);
        }
    }

    @Override
    public void notifyActivity(ReturnFromFunction func_name) {
        switch(func_name) {
            case GET_CURRENT_RECIPE_AUTHOR:
                currentRecipeAuthor = firebaseClient.getCurrentRecipeAuthor();
                ImageView userProfile = findViewById(R.id.viewPostAuthorProfile);
                firebaseClient.getImageViewByName(userProfile, currentRecipeAuthor.getProfileImageName());
                TextView userName = findViewById(R.id.viewPostAuthorName);
                userName.setText(currentRecipeAuthor.getUsername());

                hasFollowed = currentUser.getFollowingIDs().contains(currentRecipeAuthor.getKey());
                if (hasFollowed) {
                    followButton.setText("Following");
                    followButton.setBackgroundTintList(darkGrayStateList);
                } else {
                    followButton.setText("Follow");
                    followButton.setBackgroundTintList(redStateList);
                }
                break;
            default:
                break;
            // Default case for all unused functions in firebaseClient
        }
    }
}
