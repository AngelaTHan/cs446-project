package uwaterloo.cs446group7.increpeable.backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group7.increpeable.MainActivity;
import uwaterloo.cs446group7.increpeable.Recipe.DB_Recipe;
import uwaterloo.cs446group7.increpeable.User.DB_User;

public class FirebaseClient {
    private static FirebaseClient firebaseClient;

    private static final String LOG_TAG = "Firebase";
    private FileStorageEngine fileStorageEngine;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    // Cache
    private DB_User currentDBUser;
    private DB_Recipe currentRecipe;
    private ArrayList<DB_Recipe> currentRecipes;

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

//    public DB_User getCurrentUser() { return currentDBUser; }

    public boolean loginUserAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LOG_TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(LOG_TAG, "signInWithEmail:failure", task.getException());
                    }
                }
            });
        return true;
    }

    public boolean registerUserAccount(String email, String password, String username) {
        Log.i(LOG_TAG, "Start to register user account with: " + email + " " + username);
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(LOG_TAG, "createUserWithEmail:success");

                        // Insert user information into database
                        String user_key = UUID.randomUUID().toString();
                        DB_User save_new_DB_user = new DB_User(mDatabase, email, username, user_key);
                        mDatabase.child("UserAccounts").child(user_key).setValue(save_new_DB_user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                    }
                }
            });
        return true;
    }
}


//        List<String> steps = new ArrayList<String>();
//        steps.add("buy a duck");
//        steps.add("clean the duck");
//        steps.add("cook the duck");
//        List<Pair<String, Integer>> ingredients = new ArrayList<Pair<String, Integer>>();
//        ingredients.add(new Pair<String, Integer>("salt", 20));
//        ingredients.add(new Pair<String, Integer>("paper", 2));
//        ingredients.add(new Pair<String, Integer>("sugar", 30));
//        Recipe newRecipe = new Recipe("Rost Duck", "Waterloo",
//                "This recipe teaches you how to make rost duck.", "ABCD123D", steps, ingredients);
//        String postKey = mDatabase.child("Recipes").push().getKey();
//        mDatabase.child("Recipes").child(postKey).setValue(newRecipe);
//        // firebase change array entry (Ex: steps) of an existing recipe
//        Map<String, Object> postValues = new HashMap<String,Object>();
//        List<String> curSteps = newRecipe.getSteps();
//        curSteps.remove(1);
//        curSteps.add(1, "this should be the second step");
//        newRecipe.setSteps(curSteps);
//        postValues.put(postKey, newRecipe);
//        mDatabase.child("Recipes").updateChildren(postValues);
// firebase delete an array entry from an existing recipe
//        Map<String, Object> removeValue = new HashMap<String,Object>();
//        List<String> changedSteps = newRecipe.getSteps();
//        changedSteps.remove(1);
//        postValues.put(postKey, changedSteps);
//        mDatabase.child("Recipes").child(postKey).child("steps").setValue(changedSteps);
//        mDatabase.child("Recipes").child(postKey).child("steps").child("1").removeValue(); this is not a good practice!
// firebase add a new user - Not correct. Debugging.
//        User save_new_user = new User("hahaha@gmail.com", "image image", "Click to edit your description...", "");
//        String user_key = mDatabase.child("UserAccounts")
//                .push()
//                .getKey();
//        save_new_user.setKey(user_key);
//        mDatabase.child("UserAccounts").child(user_key).setValue(save_new_user);

