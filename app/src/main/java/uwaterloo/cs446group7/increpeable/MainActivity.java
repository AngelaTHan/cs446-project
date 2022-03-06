package uwaterloo.cs446group7.increpeable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import uwaterloo.cs446group7.increpeable.backend.FirebaseClient;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {
//    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "Firebase";
    private static final String USER = "UserAccounts";
    private FirebaseClient firebaseClient;

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
                try {
                    FutureTask<Boolean> ft = new FutureTask<>(() -> firebaseClient.loginUserAccount(
                            registerEmailInput.getText().toString(),
                            registerPasswordInput.getText().toString()));
                    ExecutorService executor = Executors.newFixedThreadPool(1);
                    executor.submit(ft);
                    ft.get();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    showErrorToast(e);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FutureTask<Boolean> ft = new FutureTask<>(() -> firebaseClient.registerUserAccount(
                        registerEmailInput.getText().toString(),
                        registerPasswordInput.getText().toString(),
                        registerUsernameInput.getText().toString()));
                    ExecutorService executor = Executors.newFixedThreadPool(1);
                    executor.submit(ft);
                    ft.get();
                    Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    showErrorToast(e);
                }
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

    private void showErrorToast(Exception e) {
        runOnUiThread(
            () -> Toast.makeText(
                getApplicationContext(),
                e.getLocalizedMessage(),
                Toast.LENGTH_LONG).show()
        );
    }

}
