package uwaterloo.cs446group7.increpeable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {
    private ImageView profile;
    private ImageView createPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // bottom bar interactions
        profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfileIntent = new Intent(HomeActivity.this, ProfilePageActivity.class);
                goProfileIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goProfileIntent, 0);
            }
        });

        // create recipe interaction
        createPost = findViewById(R.id.postButton);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(HomeActivity.this, CreatePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });
    }
}