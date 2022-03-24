package uwaterloo.cs446group6.increpeable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePageActivity extends AppCompatActivity {
    private ImageButton backImageButton;
    private ImageButton saveImageButton;
    private ImageView imageToUpload;
    private Button uploadImageButton;
    private EditText recipeName;
    private EditText intro;
    private EditText ingredient;
    private EditText recipeSteps;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // profile user interactions
        backImageButton = findViewById(R.id.backImageButton);
        saveImageButton = findViewById(R.id.saveImageButton);
        imageToUpload = findViewById(R.id.imageToUpload);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        recipeName = findViewById(R.id.recipeName);
        intro = findViewById(R.id.intro);
        ingredient = findViewById(R.id.ingredient);
        recipeSteps = findViewById(R.id.recipeSteps);
        postButton = findViewById(R.id.postButton);

        // load defaults
        recipeName.setText("Beef Wellington");
        intro.setText("A traditional Beef Wellington consists of a beef tenderloin wrapped in layers of pâté, duxelles (a finely chopped mushroom mixture), parma ham, and puff pastry, then baked. Rumor has it that Beef Wellington got its name from Arthur Wellesley, the 1st Duke of Wellington, who counted the dish among his favorite recipes.");
        ingredient.setText("2 x 400g beef fillets\nOlive oil, for frying\n500g mixture of wild mushrooms, cleaned\n1 thyme sprig, leaves only\n500g puff pastry\nslices of Parma ham\n2 egg yolks, beaten with 1 tbsp water and a pinch of salt\nSea salt and freshly ground black pepper");
        recipeSteps.setText("Step 1:\nSeason beef liberally with salt and pepper on all sides. Use your hands to press salt and pepper in while rolling and shaping beef into a cylinder.\nStep 2: \nHeat a cast iron skillet and add grapeseed oil, then sear beef, cooking 1 minute per side.\nStep 3: \nRemove beef to sheet tray and pour the pan juices over it. While hot, brush Dijon mustard all over, and then allow to rest.\nStep 4: \nTake a damp towel and moisten your cutting board. Layer three pieces of plastic wrap overlapping each other on the board.");

        // set-profile-default
        imageToUpload.setImageDrawable(getResources().getDrawable(R.drawable.food));

        // post recipe interaction
        postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goPostIntent = new Intent(CreatePageActivity.this, HomePageActivity.class);
                goPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(goPostIntent, 0);
            }
        });
    }
}