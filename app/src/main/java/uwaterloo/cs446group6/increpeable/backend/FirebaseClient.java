package uwaterloo.cs446group6.increpeable.backend;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import uwaterloo.cs446group6.increpeable.databaseClasses.User;

public class FirebaseClient {
//            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);

    private static final String TAG = "Firebase";
    private static FirebaseClient firebaseClient;
    private DatabaseReference mDatabase;
    private User currentUser;

    public FirebaseClient(User user) {
        this.currentUser = user;
    }

    public static FirebaseClient getInstance() {
        if (firebaseClient == null) {
            Log.e(TAG, "Firebase Client accessed before initialized");
        }
        return firebaseClient;
    }

    public User getCurrentUser() { return currentUser; }
//    public User getCurrentUser() { return currentUser; }

}
