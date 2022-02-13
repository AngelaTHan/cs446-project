package uwaterloo.cs446group7.increpeable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        System.out.print("in authentication");
    }
}