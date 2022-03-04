package uwaterloo.cs446group6.increpeable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ViewPageActivity extends AppCompatActivity {
    private Boolean hasFollowed = false;
    private Boolean hasLiked = false;
    private Boolean hasCollected= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Button followButton = findViewById(R.id.follow);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                int red = ContextCompat.getColor(getBaseContext(), R.color.red);
                int darkGray = ContextCompat.getColor(getBaseContext(), R.color.dark_gray);
                if (hasFollowed) {
                    followButton.setText("Follow");
                    followButton.setBackgroundColor(red);
                    hasFollowed = false;
                } else {
                    followButton.setText("Following");
                    followButton.setBackgroundColor(darkGray);
                    hasFollowed = true;
                }
            }
        });
    }

}
