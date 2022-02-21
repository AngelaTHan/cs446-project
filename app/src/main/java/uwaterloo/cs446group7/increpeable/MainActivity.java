package uwaterloo.cs446group7.increpeable;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Database Connection
        // If you're developing / debugging with no need of database, feel free to comment these out
        // to save time.


        // Switch to Login page. For development / debug purposes, feel free to change this to
        // your page.
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
