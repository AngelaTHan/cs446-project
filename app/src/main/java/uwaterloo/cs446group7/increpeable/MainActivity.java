package uwaterloo.cs446group7.increpeable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uwaterloo.cs446group7.increpeable.databaseClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "Firebase";
    private static final String USER = "UserAccounts";
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabase;
    private static ArrayList<String> registered_users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = new Intent(this, ViewPageActivity.class);
        startActivity(intent);

        // Initialize google sign in service
//        initGoogleLogin();

        // Fetch registered users from database; This will be used to check if current user
        // is a first-time user, and switch to an Activity accordingly.
//        getRegisteredUsers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Initialize Google login
    private void initGoogleLogin() {
        // init google login service
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        GoogleSignInOptions.Builder gsoBuilder =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail();
        GoogleSignInOptions gso = gsoBuilder.build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    // Get a list of registered users.
    private void getRegisteredUsers() {
        mDatabase.child(USER).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Fetching current users from the database before user login failed.");
                }
                else {
                    List<DataSnapshot> list = new ArrayList<>();
                    Iterator<DataSnapshot> it = task.getResult().getChildren().iterator();
                    while (it.hasNext()) {
                        User u = it.next().getValue(User.class);
                        registered_users.add(u.getEmail());
                    }
                    Log.i(TAG, "Fetched all users from the database. registered_users lengths: " + registered_users.size());
                }
            }
        });
    }


    // Google sign in activity result handler & firebase sign in
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> signInTask =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            // If Google ID authentication is successful, obtain a token for Firebase authentication.
            if (signInTask.isSuccessful()) {
                // Sign in succeeded, proceed with account
                GoogleSignInAccount acct = signInTask.getResult();
                AuthCredential credential = GoogleAuthProvider.getCredential(
                        acct.getIdToken(), null);
                // Now sign in to firebase.
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this, task -> {
                            Log.d(TAG, "signInWithCredential:onComplete Successful: " + task.isSuccessful());
                            if (task.isSuccessful()) {
                                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (currentUser != null) {
                                    Log.i(TAG, "Firebase sign in success");
                                    // Next step: check if current user is a first-time user.
                                    checkRegistered(currentUser);
                                } else {
                                    Log.e(TAG, "Firebase sign in failed");
                                }
                            } else {
                                Log.e(TAG, "Google signInWithCredential:onComplete", task.getException());
                            }
                        });
            } else if (signInTask.isCanceled()) {
                String message = "Google authentication was canceled. "
                        + "Verify the SHA certificate fingerprint in the Firebase console.";
                Log.d(TAG, message);
                showErrorToast(new Exception(message));
            } else {
                System.out.println("signin task error ");
                String message = "Google authentication status: " + signInTask.getResult();
                Log.d(TAG, message);
                showErrorToast(new Exception(message));
            }
        }
    }

    // After signed into google and firebase, check whether user is a first-time user.
    // First time user -> Personal profile page
    // Registered user -> Home page
    private void checkRegistered(FirebaseUser user) {
        String user_email = user.getEmail();
//        if (false){
        if (registered_users.contains(user_email)) {
            System.out.println("user found");
            // intent to home page, also passes user info
//            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
//            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
//            intent.putExtra("username", user.getDisplayName())
//            startActivity(intent);
        } else {
            // first time user
            User save_new_user = new User(user.getEmail(), user.getDisplayName(), "Click to edit your description...", user.getPhotoUrl().toString());
            mDatabase.child(USER)
                    .push()
                    .setValue(save_new_user);
            System.out.println("first time user saved");
//            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
//            startActivity(intent);
        }
    }

    private void showErrorToast(Exception e) {
        runOnUiThread(
            () -> Toast.makeText(
                getApplicationContext(),
                e.getLocalizedMessage(),
                Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }
}
