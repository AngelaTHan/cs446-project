package uwaterloo.cs446group6.increpeable.backend;

import static java.lang.Math.min;
import static java.lang.Math.max;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import uwaterloo.cs446group6.increpeable.NotifyActivity;
import uwaterloo.cs446group6.increpeable.Recipe.DB_Recipe;
import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.Users.DB_User;
import uwaterloo.cs446group6.increpeable.Users.User;

public class FirebaseClient {
    // Local variables
    private static FirebaseClient firebaseClient;
    private static final String LOG_TAG = "Firebase";
    private FileStorageEngine fileStorageEngine;
    private DatabaseReference mDatabase;
    private int startFrom = 0;
    private NotifyActivity currentActivity;

    // Cache; can be accessed with Cache Getters
    private DB_User currentUser;
    private ArrayList<DB_Recipe> currentRecipes = new ArrayList<>(); // Recipes for this class, can change values
    private DB_Recipe currentRecipe;
    private DB_User currentRecipeAuthor;


    //  ==================================================================================
    //  ================================ Cache Getters ===================================
    //  ==================================================================================
    // Cache getters to be called when async call finishes
    // (i.e. when NotifyActivity() is called)
    // These functions don't cost money


    // return currentUser in cache
    public User getCacheUser() { return currentUser; }

    // return currentRecipes in cache
    public ArrayList<Recipe> getCacheRecipePosts() {
        ArrayList<Recipe> recipesToReturn = new ArrayList<>();
        // convert Recipes into DB_Recipes
        currentRecipes.forEach(recipe -> recipesToReturn.add(recipe));
        return recipesToReturn;
    }

    // return currentRecipe in cache
    public Recipe getCurrentRecipe () {
        return currentRecipe;
    }

    // return currentRecipeAuthor in cache
    public User getCurrentRecipeAuthor() { return currentRecipeAuthor; }


    //  ==================================================================================
    //  ================ setCurrentActivity and setCurrentDBUser =========================
    //  ==================================================================================


    // Set the current activity
    // Note: This must be called once the activity is loaded before performing
    // any database transactions
    public void setCurrentActivity(NotifyActivity ac) { currentActivity = ac; }

    // Called once logged in by MainActivity
    public void setCurrentDBUser(DB_User user) { currentUser = user; }

    // Set the currentRecipe field in cache
    public void setCurrentRecipe (Recipe recipe) {
        this.currentRecipe = new DB_Recipe(recipe);
    }

    //  ==================================================================================
    //  ===================== getRecipesByID and getRecommendedPosts =====================
    //  ==================================================================================
    // Use case: retrieve a list of posts by the user, or retrieve a list of recommended
    // posts. Both of these are async calls. Thus, NotifyActivity needs to be modified to
    // retrieve the recipes


    // Get some recipes by keys;
    // async function;
    // Will call currentActivity.notifyActivity (ReturnFromFunction.GET_RECIPES_BY_ID)
    // when posts are retrieved from firebase.
    public void getRecipesByID(ArrayList<String> keys) {
        if (!currentRecipes.isEmpty()) { currentRecipes.clear(); } // empty the arraylist
        mDatabase.child("Recipes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting recipes from firebase", task.getException());
                }
                else {
                    DataSnapshot result_ds = task.getResult();
                    for (DataSnapshot ds : result_ds.getChildren()) {
                        if (keys.contains(ds.child("key").getValue(String.class))) {
                            currentRecipes.add(ds.getValue(DB_Recipe.class));
                        }
                    }
                    if (currentRecipes.size() != keys.size()) {
                        Log.e(LOG_TAG, "cannot find some of the recipes by ids" + currentRecipes.size() + " " + keys.size());
                    } else {
                        Log.i(LOG_TAG, "recipes saved to the arraylist" + currentRecipes.size());
                    }
                    currentActivity.notifyActivity(ReturnFromFunction.GET_RECIPES_BY_ID);
                }
            }
        });
    }

    // get n recommended recipes for the home page;
    // async function;
    // Will call currentActivity.notifyActivity (ReturnFromFunction.GET_RECOMMENDED_POSTS)
    // when posts are retrieved from firebase.
    public void getRecommendedRecipes(int size) {
        currentRecipes.clear();
        // start to get data from firebase
        mDatabase.child("Recipes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting recipes from firebase", task.getException());
                }
                else {
                    DataSnapshot result_ds = task.getResult(); // result_ds is all users in database
                    ArrayList<DB_Recipe> returnedRecipes = new ArrayList<>();
                    result_ds.getChildren().forEach(ds -> {
                        returnedRecipes.add(ds.getValue(DB_Recipe.class));
                    });
                    int sizeToGet = min(returnedRecipes.size() - startFrom, size);
                    List<DB_Recipe> recipeSubset = returnedRecipes.subList(startFrom, startFrom + sizeToGet); // bug here: need to fix: returnedRecipes.subList(startFrom - 1, sizeToGet);
                    currentRecipes.addAll(recipeSubset);
                    startFrom += sizeToGet;
                    currentActivity.notifyActivity(ReturnFromFunction.GET_RECOMMENDED_POSTS);
                }
            }
        });
    }

    // returns a list of recipes that contains the keywords in their title;
    // async function;
    // Will call currentActivity.notifyActivity (ReturnFromFunction.GET_RECOMMENDED_POSTS)
    // when posts are retrieved from firebase.
    public void getRecipesByKeywords(String keyWords) {
        if (!currentRecipes.isEmpty()) { currentRecipes.clear(); } // empty the arraylist
        mDatabase.child("Recipes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting recipes from firebase", task.getException());
                }
                else {
                    DataSnapshot result_ds = task.getResult();
                    for (DataSnapshot ds : result_ds.getChildren()) {
                        DB_Recipe curRecipe = ds.getValue(DB_Recipe.class);
                        // Search for title
                        if (curRecipe.getTitle().contains(keyWords)) {
                            currentRecipes.add(curRecipe);
                        } else {
                            // Search for ingredients
                            for (String ingredient : curRecipe.getIngredients()) {
                                if (ingredient.contains(keyWords)) {
                                    currentRecipes.add(curRecipe);
                                    break;
                                }
                            }
                        }
                    }
                    if (currentRecipes.size() == 0) {
                        Log.e(LOG_TAG, "cannot find any recipes that contains " + keyWords);
                    } else {
                        Log.i(LOG_TAG, " recipes saved to the arraylist");
                    }
                    currentActivity.notifyActivity(ReturnFromFunction.GET_RECIPES_BY_TITLE);
                }
            }
        });
    }

    // get the author of the current recipe, and save it to currentRecipeAuthor;
    // async function;
    // Will call currentActivity.notifyActivity (ReturnFromFunction.GET_RECOMMENDED_POSTS)
    // when posts are retrieved from firebase.
    public void setCurrentRecipeAuthor() {
        // Set current recipe author information
        mDatabase.child("UserAccounts").child(currentRecipe.getAuthorKey())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting data", task.getException());
                } else {
                    Log.d(LOG_TAG, "get User success");
                    currentRecipeAuthor = task.getResult().getValue(DB_User.class);
                    currentActivity.notifyActivity(ReturnFromFunction.GET_CURRENT_RECIPE_AUTHOR);
                }
            }
        });
    }


    //  ==================================================================================
    //  ========================= refreshUser and refreshRecipes =========================
    //  ==================================================================================
    // Use case: reload user and recipes from firebase to get updated information
    // affected by ** OTHER ** users.
    // - E.g., you may want to refresh your UI to check if someone has liked your post.
    // You do not need to call these functions if the change is made by you.
    // - E.g. You do not need to refresh recipes if you just posted a new recipe.


    // async function;
    // Will call currentActivity.notifyActivity (ReturnFromFunction.REFRESH_USER)
    // when user is retrieved from firebase.
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
                    currentActivity.notifyActivity(ReturnFromFunction.REFRESH_USER);
                }
            }
        });
    }

    // async function. update currentRecipes and recipesToReturn
    // Will call currentActivity.notifyActivity (ReturnFromFunction.GET_RECIPES_BY_ID)
    // when recipes are retrieved from firebase.
    // Note: It doesn't use ReturnFromFunction.REFRESH_RECIPES!
    public void refreshRecipes() {
        if (currentRecipes.isEmpty()) {
            Log.d(LOG_TAG, "cannot refresh recipes because currentRecipes is empty");
            return;
        }
        ArrayList<String> ids = new ArrayList<>();
        for (DB_Recipe recipe : currentRecipes) {
            ids.add(recipe.getKey());
        }
        getRecipesByID(ids);
    }


    //  ==================================================================================
    //  ============== modifyLikePost, modifyCollectPost, modifyFollowAuthor==============
    //  ==================================================================================
    // Use case: used in the recipe post page, when viewing a recipe created by
    // another author.


    // async function; UI does not need to wait for this to finish
    // Thus, UI activity's NotifyActivity won't be called
    // There's no check for a false like / unlike. Please do not like the post when the
    // post is already liked, and vice versa
    // isLike == true  => like the post
    // isLike == false => unlike the post
    // Always cost money
    public void modifyLikePost(String postKey, boolean isLike) {
        // increase the numLike in the recipe
        DB_Recipe recipe = new DB_Recipe();
        for (int i = 0; i < currentRecipes.size(); i++) {
            if (postKey.equals(currentRecipes.get(i).getKey())) {
                recipe = currentRecipes.get(i);
                if (isLike) {
                    recipe.setNumLikes(mDatabase, recipe.getNumLikes() + 1);
                } else {
                    recipe.setNumLikes(mDatabase, recipe.getNumLikes() - 1);
                }
                break;
            }
        }

        // update current recipe cache
        if (postKey.equals(currentRecipe.getKey())) {
            currentRecipe = recipe;
        }

        // update post author numLike
        mDatabase.child("UserAccounts").child(recipe.getAuthorKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting data at likePost", task.getException());
                } else {
                    DB_User tmp = task.getResult().getValue(DB_User.class);
                    if (isLike) {
                        tmp.setNumLikes(mDatabase, tmp.getNumLikes() + 1);
                    } else {
                        tmp.setNumLikes(mDatabase, tmp.getNumLikes() - 1);
                    }
                }
            }
        });

        // add likeID to current User
        if (isLike) {
            currentUser.addLikedPostID(mDatabase, postKey);
        } else {
            currentUser.deleteLikedPostID(mDatabase, postKey);
        }
    }

    // async function; UI does not need to wait for this to finish
    // Thus, UI activity's NotifyActivity won't be called
    // There's no check for a false collect / uncollect. Please do not collect the post when the
    // post is already collected, and vice versa
    // isCollect == true  => collect
    // isCollect == false => uncollect
    public void modifyCollectPost(String article_id, boolean isCollect) {
        // increase the numCollects in the recipe
        DB_Recipe recipe = new DB_Recipe();
        for (int i = 0; i < currentRecipes.size(); i++) {
            if (article_id.equals(currentRecipes.get(i).getKey())) {
                recipe = currentRecipes.get(i);
                Log.e("Collect", "before" + recipe.getNumCollects());
                if (isCollect) {
                    recipe.setNumCollects(mDatabase, recipe.getNumCollects() + 1);
                } else {
                    recipe.setNumCollects(mDatabase, recipe.getNumCollects() - 1);
                }
                break;
            }
        }

        // update current recipe cache
        if (article_id.equals(currentRecipe.getKey())) {
            currentRecipe = recipe;
        }

        // add the collected recipe to current user's collectedRecipes list
        if (isCollect) {
            currentUser.addCollectedPostID(mDatabase, recipe.getKey());
        } else {
            currentUser.deleteCollectedPostID(mDatabase, recipe.getKey());
        }

    }

    // async function; UI does not need to wait for this to finish
    // Thus, UI activity's NotifyActivity won't be called
    // There's no check for a false follow / unfollow. Please do not follow the author when the
    // author is already followed, and vice versa
    // isFollow == true  => follow
    // isFollow == false => unfollow
    public void modifyFollowAuthor(String idToFollow, boolean isFollow) {
        // add the id of user that the current user wants to follow to its followingIDs array
        if (isFollow) {
            currentUser.addFollowingsID(mDatabase, idToFollow);
            currentUser.setNumFollowing(mDatabase, currentUser.getNumFollowers() + 1);
        } else {
            currentUser.deleteFollowingsID(mDatabase, idToFollow);
            currentUser.setNumFollowing(mDatabase, currentUser.getNumFollowers() - 1);
        }

        // add the id of the currentUser to the idToFollow's follower array
        mDatabase.child("UserAccounts").child(idToFollow).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(LOG_TAG, "Error getting data at followAuthor", task.getException());
                } else {
                    DB_User tmp = task.getResult().getValue(DB_User.class);
                    if (isFollow) {
                        tmp.addFollowersID(mDatabase, currentUser.getKey());
                        tmp.setNumFollowers(mDatabase, tmp.getNumFollowers() + 1);
                    } else {
                        tmp.deleteFollowersID(mDatabase, currentUser.getKey());
                        tmp.setNumFollowers(mDatabase, tmp.getNumFollowers() - 1);
                    }
                }
            }
        });
    }


    //  ==================================================================================
    //  ============================ createNewPost, addComment ===========================
    //  ==================================================================================


    // Synchronized function; no need to prepare a case in NotifyActivity
    // This also sets currentRecipes in local cache to the new post.
    // Always cost money
    public void createNewPost(Recipe newPost) {
        // empty the arraylist
        if (!currentRecipes.isEmpty()) {
            currentRecipes.clear();
        }

        // save newPost locally
        DB_Recipe recipe = new DB_Recipe(newPost); // cast Recipe to DB_Recipe
        currentRecipes.add(recipe);

        // save newPost to firebase
        mDatabase.child("Recipes").child(newPost.getKey()).setValue(newPost);
        currentUser.addMyPostID(mDatabase, newPost.getKey());
        currentActivity.notifyActivity(ReturnFromFunction.CREATE_NEW_POST);
    }

    // Async function; UPDATE_POST in NotifyActivity
    // This also updates currentRecipes in local cache to the updated post.
    // Always cost money
    public void updatePost(Recipe updatedRecipe) {
        DB_Recipe recipe = new DB_Recipe(updatedRecipe);
        currentRecipe = recipe;

        // update recipe cache
        for (int i = 0; i < currentRecipes.size(); i++) {
            if (currentRecipes.get(i).getKey().equals(updatedRecipe.getKey())) {
                currentRecipes.set(i, recipe);
                break;
            }
        }

        // update firebase
        currentRecipe.setTitle(mDatabase, recipe.getTitle());
        currentRecipe.setDescription(mDatabase, recipe.getDescription());
        currentRecipe.setIngredients(mDatabase, recipe.getIngredients());
        currentRecipe.setSteps(mDatabase, recipe.getSteps());
        currentActivity.notifyActivity(ReturnFromFunction.UPDATE_POST);
    }

    // Synchronized function; no need to prepare a case in NotifyActivity
    public void addComment(String postID, String comment, String username) {
        Long tsLong = System.currentTimeMillis()/1000;
        String time = tsLong.toString();
        ArrayList<String> newComment = new ArrayList<>();
        newComment.add(comment);
        newComment.add(time);
        newComment.add(username);

        for (DB_Recipe dr : currentRecipes) {
            if (dr.getKey().equals(postID)) {
                dr.addComment(mDatabase, newComment);
                break;
            }
        }

        currentActivity.notifyActivity(ReturnFromFunction.CREATE_NEW_POST);
    }


    //  ==================================================================================
    //  ======================= getImageViewByName, uploadImageView ======================
    //  ==================================================================================
    // Use case: download or upload an image in any activity.


    // async function
    // Will call currentActivity.notifyActivity(ReturnFromFunction.GET_IMAGE_VIEW_BY_NAME)
    // and set the imageView when download is completed.
    public void getImageViewByName(ImageView imageView, String name) {
        fileStorageEngine.downloadImage(imageView, name, currentActivity);
    }

    // async function
    // Will call currentActivity.notifyActivity(ReturnFromFunction.UPLOAD_IMAGEVIEW)
    // when the upload is complete
    public void uploadImageView(ImageView iv, String name) {
        fileStorageEngine.uploadImage(iv, name, currentActivity);
    }

    // sync function
    public void setProfileImageName(String imageName) {
        currentUser.setProfileImageName(mDatabase, imageName);
    }

    // sync function
    public void setRecipeCoverImageName(String recipeID, String imageName) {
        for (DB_Recipe recipe : currentRecipes) {
            if (recipe.getKey().equals(recipeID)) {
                recipe.setCoverImageName(mDatabase, imageName);
                break;
            }
        }
    }


    //  ==================================================================================
    //  ===================== setUsername and setUserDescription =====================
    //  ==================================================================================


    // set current user's username
    public void setUsername (String name) {
        currentUser.setUsername(mDatabase, name);
    }

    // set current user's description
    public void setUserDescription (String description) {
        currentUser.setDescription(mDatabase, description);
    }


    //  ==================================================================================
    //  ===================== Singleton Constructor and getInstance() ====================
    //  ==================================================================================


    private FirebaseClient() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.fileStorageEngine = new FileStorageEngine();
    }

    public static FirebaseClient getInstance() {
        if (firebaseClient == null) {
            firebaseClient = new FirebaseClient();
        }
        return firebaseClient;
    }
}
