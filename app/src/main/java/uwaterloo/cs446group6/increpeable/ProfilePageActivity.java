package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Users.User;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;

public class ProfilePageActivity extends NotifyActivity {
    private static final int PICK_IMAGE = 100;
    // bottom navigation bar
    private ImageView home;
    private ImageView newPost;

    // user information
    private ImageView profileImage;
    private EditText username;
    private EditText userBio;
    private ImageView myPosts;

    // user stats
    private TextView followingCount;
    private TextView followersCount;
    private TextView likesCount;

    // view options
    private ImageView collections;
    private ImageView post1image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        // setup firebase client
        firebaseClient = FirebaseClient.getInstance();
        currentUser = firebaseClient.getCacheUser();
        firebaseClient.setCurrentActivity(this);

        // profile information
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        userBio = findViewById(R.id.userBio);
        followingCount = findViewById(R.id.followingCount);
        followersCount = findViewById(R.id.followersCount);
        likesCount = findViewById(R.id.likesCount);

        // load profile information
        System.out.println("***" + currentUser.getProfileImageName());
        firebaseClient.getImageViewByName(profileImage, currentUser.getProfileImageName());
        username.setText(currentUser.getUsername());
        userBio.setText(currentUser.getDescription());
        followingCount.setText(String.valueOf(currentUser.getNumFollowing()));
        followersCount.setText(String.valueOf(currentUser.getNumFollowers()));
        likesCount.setText(String.valueOf(currentUser.getNumLikes()));

        // change profile image
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(GalleryIntent, PICK_IMAGE);
            }
        });

        // bottom bar interactions
        home = findViewById(R.id.home);
        newPost = findViewById(R.id.newPost);
        home.setOnClickListener(new View.OnClickListener() {    // go to homepage
            @Override
            public void onClick(View view) {
                Intent goHomeIntent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
                goHomeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goHomeIntent, 0);
            }
        });
        newPost.setOnClickListener(new View.OnClickListener() { // create new post
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(ProfilePageActivity.this, CreatePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });

        // my post and collection interaction
        myPosts = findViewById(R.id.myPosts);
        collections = findViewById(R.id.collections);
        myPosts.setColorFilter(Color.argb(255, 255, 0, 0));
        collections.setColorFilter(Color.argb(255, 0, 0, 0));
        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collections.setColorFilter(Color.argb(255, 0, 0, 0));
                myPosts.setColorFilter(Color.argb(255, 255, 0, 0));
            }
        });
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPosts.setColorFilter(Color.argb(255, 0, 0, 0));
                collections.setColorFilter(Color.argb(255, 255, 0, 0));
            }
        });

        // view post
        post1image = findViewById(R.id.post1image);
        post1image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goViewPostIntent = new Intent(ProfilePageActivity.this, ViewPageActivity.class);
                goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goViewPostIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
            String imageID = UUID.randomUUID().toString();
            firebaseClient.uploadImageView(profileImage, imageID);
            firebaseClient.setProfileImageName(imageID + ".jpg");
        }
    }
}