package uwaterloo.cs446group6.increpeable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.Users.User;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;

public class NotifyActivity extends AppCompatActivity {
    protected FirebaseClient firebaseClient;
    protected User currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseClient = FirebaseClient.getInstance();
        currentUser = firebaseClient.getCacheUser();
        firebaseClient.setCurrentActivity(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        firebaseClient.setCurrentActivity(this);
    }

    // To be overwritten
    public void notifyActivity(ReturnFromFunction c) {
    }
}
