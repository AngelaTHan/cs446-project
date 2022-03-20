package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;

import uwaterloo.cs446group6.increpeable.Users.DB_User;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
//    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final String USER = "UserAccounts";
    private FirebaseClient firebaseClient;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
   // private ArrayList<String> registeredUsers = new ArrayList<>();

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
    StorageReference imagesRef = storageRef.child("images");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        initializeUI();
        setOnClickListeners();

        firebaseClient = FirebaseClient.getInstance();

        getRegisterUsers();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void toLoginUI() {
        for (View view: registerViews) {
            view.setVisibility(View.INVISIBLE);
        }
        for (View view: loginViews) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void toRegisterUI() {
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

        toLoginUI();
    }

    private void setOnClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loginUserAccount();
                /*
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent);
                */
                loginUserAccount(loginEmailInput.getText().toString(),
                        loginPasswordInput.getText().toString());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserAccount(registerEmailInput.getText().toString(), registerPasswordInput.getText().toString(), registerUsernameInput.getText().toString());
            }
        });
        toRegisterLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterUI();
            }
        });
        toLoginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginUI();
            }
        });
    }
    public boolean loginUserAccount(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");

                        // get current user
                        mDatabase.child("UserAccounts").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e(TAG, "Error getting all registered users data", task.getException());
                                }
                                else {
                                    DataSnapshot result_ds = task.getResult(); // result_ds is all users in database
                                    for (DataSnapshot ds : result_ds.getChildren()) {
                                        if (ds.child("email").getValue(String.class).equals(email)) {
                                            firebaseClient.setCurrentDBUser(ds.getValue(DB_User.class));
                                            break;
                                        }
                                    }
                                    //Log.i(TAG, "Received all users. User lengths: " + registeredUsers.size());
                                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        runOnUiThread( () -> {
                            Toast.makeText(MainActivity.this, "Login failed.",
                                Toast.LENGTH_SHORT).show();
                        });
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                }
            });
        return true;
    }

    public boolean registerUserAccount(String email, String password, String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.i("MainActivity", "Start to register user account with: " + email + " " + username);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    System.out.println("checkpoint 4");
                    if (task.isSuccessful()) {
                        Log.d("MainActivity", "createUserWithEmail:success");
                        // Insert user information into database
                        String user_key = UUID.randomUUID().toString();
                        DB_User save_new_DB_user = new DB_User(email, username, user_key);
                        save_new_DB_user.registerUser(mDatabase);
                        mDatabase.child("UserAccounts").child(user_key).setValue(save_new_DB_user);
                        firebaseClient.setCurrentDBUser(save_new_DB_user);
                        Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
                        startActivity(intent);
                    } else {
                        runOnUiThread( () -> {
                            Toast.makeText(MainActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        });
                        Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                    }
                    System.out.println("register completed");
                }
            });
        return true;
    }
    private void showErrorToast(Exception e) {
        runOnUiThread(
            () -> Toast.makeText(
                getApplicationContext(),
                e.getLocalizedMessage(),
                Toast.LENGTH_LONG).show()
        );
    }

    public void getRegisterUsers() {

    }
}
