package uwaterloo.cs446group7.increpeable;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.ktx.Firebase;

import uwaterloo.cs446group7.increpeable.databaseClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener {
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private static final String TAG = "Firebase";
    private static final String USER = "UserAccounts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Database Connection
        // If you're developing / debugging with no need of database, feel free to comment these out
        // to save time.

        // Switch to Login page. For development / debug purposes, feel free to change this to
        // your page.
//        Intent intent = new Intent(this, AuthenticationActivity.class);
//        startActivity(intent);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        System.out.println("signin_button: " + signInButton);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        initFirebase();
        System.out.println("init done. " + mGoogleSignInClient);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void testPush() {
        // Test push
        System.out.println("init firebase");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            System.out.println("current user is : " + currentUser);
            FirebaseDatabase.getInstance().getReference().child(USER)
                    .push()
                    .setValue(new User("Kevin", "abc123", "Hi, this is Kevin", ""));
        } else {
            System.out.println("curernt user is null");

        }
    }

    void initFirebase() {
        // Initialize google sign in client
        GoogleSignInOptions.Builder gsoBuilder =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail();
        GoogleSignInOptions gso = gsoBuilder.build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("received result");
        if (requestCode == RC_SIGN_IN) {
            System.out.println("in RC_SIGN_IN ");
            Task<GoogleSignInAccount> signInTask =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            System.out.println("signInTask: " + signInTask);
            // If Google ID authentication is successful, obtain a token for Firebase authentication.
            if (signInTask.isSuccessful()) {
                // Sign in succeeded, proceed with account
                GoogleSignInAccount acct = signInTask.getResult();
//                status.setText(getResources().getString(R.string.authenticating_label));
                AuthCredential credential = GoogleAuthProvider.getCredential(
                        acct.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this, task -> {
                            Log.d(TAG, "signInWithCredential:onComplete Successful: " + task.isSuccessful());
                            if (task.isSuccessful()) {
                                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (currentUser != null) {
                                    Log.i(TAG, "sign in success");
                                    testPush();
                                } else {
                                    Log.w(TAG, "sign in failed");
                                }
                            } else {
                                Log.w(TAG, "signInWithCredential:onComplete", task.getException());
                            }
                        });
            } else if (signInTask.isCanceled()) {
                String message = "Google authentication was canceled. "
                        + "Verify the SHA certificate fingerprint in the Firebase console.";
                Log.d(TAG, message);
                showErrorToast(new Exception(message));
            } else {
                System.out.println("signin task error ");

//                String message = "Google authentication status: " + signInTask.getResult();
                String message = "failed";
                Log.d(TAG, message);
                showErrorToast(new Exception(message));
            }
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
        switch (v.getId()) {
//            case R.id.sign_out_button:
//                signOut();
//                break;
            case R.id.sign_in_button:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                // Start authenticating with Google ID first.
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
//            case R.id.microphone_button:
//                translateAudioMessage(v);
//                break;
        }
    }



}
