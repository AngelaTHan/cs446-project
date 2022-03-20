package uwaterloo.cs446group6.increpeable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.backend.FirebaseClient;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

public class uploadPost extends NotifyActivity {
    private TextView t;
    private ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_post);


        String key = UUID.randomUUID().toString();
        String title = "Good Old Fashioned Pancakes";
        String location = "Toronto, Canada";
        String description = "This is a great recipe that I found in my Grandma's recipe book. Judging from the weathered look of this recipe card, this was a family favorite.";
        String authorKey = currentUser.getKey();
        //String authorKey = "2138921738";
        String getCoverImageName  = key + ".jpeg";
        ArrayList<String> steps = new ArrayList<>();
        steps.add("In a large bowl, sift together the flour, baking powder, salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; mix until smooth.");
        steps.add("Heat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Brown on both sides and serve hot.");

        ArrayList<String> ingredients = new ArrayList<>(); // Array of <amount, ingredient, unit> <5, salt, g>
        //i1.add();
        ingredients.add("1 12 cups all-purpose flour");
        ingredients.add("3 1/2 teaspoons baking powder ");
        ingredients.add("1/4teaspoon salt, or more to taste");
        ingredients.add("1 tablespoon white sugar");
        ingredients.add("1 1/4 cups milk");
        ingredients.add("1 egg");
        ingredients.add("3 tablespoons butter, melted");

        Recipe curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);

//

//

//


        firebaseClient.createNewPost(curR);
        String imageID = getCoverImageName;

        t = findViewById(R.id.textBox);
        t.setText("Recipe Created Successfully");
        i = findViewById(R.id.image);
        i.setImageResource(R.drawable.good_old_fashioned_pancakes);

        firebaseClient.uploadImageView(i, imageID);
        firebaseClient.setRecipeCoverImageName(key, imageID);
    }

    /*
    public void notifyActivity(ReturnFromFunction c) {
        switch(c){
            case CREATE_NEW_POST:

                break;
            case UPLOAD_IMAGEVIEW:
                break;
            default:

        }
    }
     */
}