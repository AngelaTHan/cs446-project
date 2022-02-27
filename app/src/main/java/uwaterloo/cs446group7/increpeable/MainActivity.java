package uwaterloo.cs446group7.increpeable;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import io.realm.Realm;

import uwaterloo.cs446group7.increpeable.backend.RealmClient;
public class MainActivity extends AppCompatActivity {
    RealmClient realmClient; // Database Client
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Database Connection
        // If you're developing / debugging with no need of database, feel free to comment these out
        // to save time.
        Realm.init(this);
        RealmClient realmClient = new RealmClient();

        // Switch to Login page. For development / debug purposes, feel free to change this to
        // your page.
        Intent intent = new Intent(this, homeActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmClient.finalize();
    }
}