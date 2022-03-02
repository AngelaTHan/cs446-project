package uwaterloo.cs446group7.increpeable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class homeActivity extends AppCompatActivity {
    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // bottom bar interactions
        profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHomeIntent = new Intent(homeActivity.this, ProfilePageActivity.class);
                startActivity(goHomeIntent);
            }
        });
    }
}