package uwaterloo.cs446group6.increpeable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ViewPageActivity extends AppCompatActivity {
    private Boolean hasFollowed = false;
    private Boolean hasLiked = false;
    private Boolean hasCollected= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ColorStateList redStateList = ContextCompat.getColorStateList(getBaseContext(), R.color.red);
        ColorStateList darkGrayStateList = ContextCompat.getColorStateList(getBaseContext(), R.color.dark_gray);
        ColorStateList yellowStateList = ContextCompat.getColorStateList(getBaseContext(), R.color.yellow);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Button followButton = findViewById(R.id.follow);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (hasFollowed) {
                    followButton.setText("Follow");
                    followButton.setBackgroundTintList(redStateList);
                    hasFollowed = false;
                } else {
                    followButton.setText("Following");
                    followButton.setBackgroundTintList(darkGrayStateList);
                    hasFollowed = true;
                }
            }
        });

        LinearLayout likeButton = findViewById(R.id.like);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                ImageView likeIcon = findViewById(R.id.likeIcon);
                if (hasLiked) {
                    likeIcon.setBackgroundResource(R.drawable.like);
                    likeIcon.setBackgroundTintList(darkGrayStateList);
                    hasLiked = false;
                } else {
                    likeIcon.setBackgroundResource(R.drawable.like_solid);
                    likeIcon.setBackgroundTintList(redStateList);
                    hasLiked = true;
                }
            }
        });

        LinearLayout collectButton = findViewById(R.id.collect);
        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                ImageView collectIcon = findViewById(R.id.collectIcon);
                if (hasCollected) {
                    collectIcon.setBackgroundResource(R.drawable.collect);
                    collectIcon.setBackgroundTintList(darkGrayStateList);
                    hasCollected = false;
                } else {
                    collectIcon.setBackgroundResource(R.drawable.collect_solid);
                    collectIcon.setBackgroundTintList(yellowStateList);
                    hasCollected = true;
                }
            }
        });
    }
}
