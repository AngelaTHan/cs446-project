package uwaterloo.cs446group7.increpeable;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uwaterloo.cs446group7.increpeable.databaseClasses.Recipe;
import uwaterloo.cs446group7.increpeable.databaseClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "Firebase";
    private static final String USER = "UserAccounts";
//    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
//    private static ArrayList<String> registered_users = new ArrayList<>();

    private TextView registerPasswordLabel;
    private EditText registerPasswordInput;
    private TextView registerEmailLabel;
    private EditText registerEmailInput;
    private TextView registerUsernameLabel;
    private EditText registerUsernameInput;

    private TextView loginPasswordLabel;
    private EditText loginPasswordInput;
    private TextView loginEmailLabel;
    private EditText loginEmailInput;

    private Button loginButton;
    private Button registerButton;

    private TextView registerAccountLabel;
    private TextView loginAccountLabel;

    private TextView toRegisterLabel;
    private TextView toLoginLabel;
    ArrayList<View> registerViews = new ArrayList<>();
    ArrayList<View> loginViews = new ArrayList<>();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    // Create a child reference
    // imagesRef now points to "images"
    StorageReference imagesRef = storageRef.child("images");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //firebase add a new recipe
        System.out.println("############TEST#START##################");
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        User save_new_user = new User("hahaha@gmail.com", "image image", "Click to edit your description...", "");
        String user_key = mDatabase.child("UserAccounts")
                .push()
                .getKey();
        save_new_user.setKey(user_key);
        mDatabase.child("UserAccounts").child(user_key).setValue(save_new_user);
        System.out.println("start saving Image");
        Uri file = Uri.fromFile(new File("IMG_1482.jpg"));
        save_new_user.setProfileImageURL(file.toString());
        Map<String, Object> update = new HashMap<String, Object> ();
        update.put("", file.toString());
//        mDatabase.child("UserAccounts").child(user_key).child("profileImageURL").setValue(file.toString());
        // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();
        storageRef.child("images").putFile(file, metadata);
//        mDatabase.child("UserAccounts").child(user_key).child("profileImage").setValue(file);
//        mDatabase.child("images").child(tmp).setValue(file);
//        riversRef.putFile(file);

        super.onCreate(savedInstanceState);

        //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);

        // Initialize google sign in service
//        initGoogleLogin();

//        Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
////        intent.putExtra("username", user.getDisplayName())
//        startActivity(intent);
//
//
// Fetch registered users from database; This will be used to check if current user
        // is a first-time user, and switch to an Activity accordingly.
//        getRegisteredUsers();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        initializeUI();
        setOnClickListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void toLogin() {
        for (View view: registerViews) {
            view.setVisibility(View.INVISIBLE);
        }
        for (View view: loginViews) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void toRegister() {
        for (View view: loginViews) {
            view.setVisibility(View.INVISIBLE);
        }
        for (View view: registerViews) {
            view.setVisibility(View.VISIBLE);
        }
    }

    void initializeUI() {
        registerPasswordLabel = findViewById(R.id.RegisterPasswordLabel);
        registerPasswordInput = findViewById(R.id.RegisterPasswordInput);

        registerEmailLabel = findViewById(R.id.RegisterEmailLabel);
        registerEmailInput = findViewById(R.id.RegisterEmailInput);
        registerUsernameLabel = findViewById(R.id.RegisterUsernameLabel);
        registerUsernameInput = findViewById(R.id.RegisterUsernameInput);

        loginPasswordLabel = findViewById(R.id.LoginPasswordLabel);
        loginPasswordInput = findViewById(R.id.LoginPasswordInput);
        loginEmailLabel = findViewById(R.id.LoginEmailLabel);
        loginEmailInput = findViewById(R.id.LoginEmailInput);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        registerAccountLabel = findViewById(R.id.RegisterAccountLabel);
        loginAccountLabel = findViewById(R.id.LoginAccountLabel);

        toRegisterLabel = findViewById(R.id.toRegisterLabel);
        toLoginLabel = findViewById(R.id.toLoginLabel);

        registerViews.add(registerPasswordLabel);
        registerViews.add(registerPasswordInput);
        registerViews.add(registerEmailLabel);
        registerViews.add(registerEmailInput);
        registerViews.add(registerUsernameLabel);
        registerViews.add(registerUsernameInput);
        registerViews.add(registerButton);
        registerViews.add(registerAccountLabel);
        registerViews.add(registerUsernameInput);
        registerViews.add(toLoginLabel);

        loginViews.add(loginPasswordLabel);
        loginViews.add(loginPasswordInput);
        loginViews.add(loginEmailLabel);
        loginViews.add(loginEmailInput);
        loginViews.add(loginButton);
        loginViews.add(toRegisterLabel);
        loginViews.add(loginAccountLabel);

        toLogin();
    }

    private void setOnClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loginUserAccount();
                Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserAccount();
            }
        });
        toRegisterLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegister();
            }
        });
        toLoginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLogin();
            }
        });
    }

    private void registerUserAccount() {
        String email = registerEmailInput.getText().toString();
        String password = registerPasswordInput.getText().toString();
        String username = registerUsernameInput.getText().toString();
        Log.i(TAG, "Start to register user account with: " + email + " " + password);
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Insert user information into database
                        User save_new_user = new User(email, username, "Click to edit your description...", "");
                        String user_key = mDatabase.child(USER)
                                .push()
                                .getKey();
                        save_new_user.setKey(user_key);
                        mDatabase.child("UserAccounts").child(user_key).setValue(save_new_user);

//                        FirebaseClient();
//                        Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
//                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        showErrorToast(task.getException());
                    }
                }
            });
    }


    private void loginUserAccount() {
        String email = loginEmailInput.getText().toString();
        String password = loginPasswordInput.getText().toString();
        Log.i(TAG, "Start to login user account with: " + email + " " + password);
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        showErrorToast(task.getException());
                    }
                }
            });
    }

    // After signed into google and firebase, check whether user is a first-time user.
    // First time user -> Personal profile page
    // Registered user -> Home page
    private void checkRegistered(FirebaseUser user) {
        String user_email = user.getEmail();
//        if (false){
//        if (registered_users.contains(user_email)) {
            System.out.println("user found");
            // intent to home page, also passes user info
//            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
//            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
//            intent.putExtra("username", user.getDisplayName())
//            startActivity(intent);
//        } else {
            // first time user
            User save_new_user = new User(user.getEmail(), user.getDisplayName(), "Click to edit your description...", user.getPhotoUrl().toString());
            String user_key = mDatabase.child(USER)
                    .push()
                    .getKey();
            save_new_user.setKey(user_key);
            mDatabase.child("UserAccounts").child(user_key).setValue(save_new_user);
            System.out.println("first time user saved");
            save_new_user.setUsername("change to Kevin");
            System.out.println("changed username");
//            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
//            startActivity(intent);
//        }
    }

    private void showErrorToast(Exception e) {
        runOnUiThread(
            () -> Toast.makeText(
                getApplicationContext(),
                e.getLocalizedMessage(),
                Toast.LENGTH_LONG).show()
        );
    }
}
