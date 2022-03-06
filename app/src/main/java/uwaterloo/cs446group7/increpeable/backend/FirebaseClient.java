package uwaterloo.cs446group7.increpeable.backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
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
        this.mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseClient getInstance() {
        if (firebaseClient == null) {
            firebaseClient = new FirebaseClient();
        }
        return firebaseClient;
    }

    public void setCurrentDBUser(DB_User user) {
        this.currentDBUser = user;
    }

    public void refreshUser(String userid) {
        DatabaseReference tmp = mDatabase.child("UserAccounts").child(userid);
    }
}
