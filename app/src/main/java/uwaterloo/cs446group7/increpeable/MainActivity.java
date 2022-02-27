package uwaterloo.cs446group7.increpeable;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uwaterloo.cs446group7.increpeable.databaseClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "Firebase";
    private static final String USER = "UserAccounts";
//    private GoogleSignInClient mGoogleSignInClient;
//    private DatabaseReference mDatabase;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        getRegisteredUsers();
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
                loginUserAccount();
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
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
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
        System.out.println("login user account");
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

    private void showErrorToast(Exception e) {
        runOnUiThread(
            () -> Toast.makeText(
                getApplicationContext(),
                e.getLocalizedMessage(),
                Toast.LENGTH_LONG).show()
        );
    }
}
