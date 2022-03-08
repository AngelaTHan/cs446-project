package uwaterloo.cs446group7.increpeable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import uwaterloo.cs446group7.increpeable.User.User;
import uwaterloo.cs446group7.increpeable.backend.FirebaseClient;

public class ProfilePageActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private ImageView profileImage;
    private EditText username;
    private EditText userBio;
    private ImageView home;
    private ImageView newPost;
    private ImageView post1image;

    private User currentUser;
    private FirebaseClient firebaseClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // profile user interactions
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        userBio = findViewById(R.id.userBio);
        newPost = findViewById(R.id.newPost);
        post1image = findViewById(R.id.post1image);

        // load profile information
        profileImage.setImageDrawable(getResources().getDrawable(R.drawable.gordon));
        username.setText("Gordon Ramsay");
        userBio.setText("Gordon is a British chef, restaurateur, television personality, and " +
                "writer. His global restaurant group was founded in 2000 and has been awarded " +
                "16 Michelin stars overall.");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(GalleryIntent, PICK_IMAGE);
            }
        });

        // bottom bar interactions
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHomeIntent = new Intent(ProfilePageActivity.this, HomeActivity.class);
                goHomeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goHomeIntent, 0);
            }
        });

        // create recipe interaction
        newPost = findViewById(R.id.newPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCreatePostIntent = new Intent(ProfilePageActivity.this, CreatePageActivity.class);
                goCreatePostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goCreatePostIntent, 0);
            }
        });

        // view beef wellington recipe interaction
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
        }
    }
}