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

//        String key = UUID.randomUUID().toString();
//        String title = "Beef Wellington";
//        String location = "London, UK";
//        String description = "A traditional Beef Wellington consists of a beef tenderloin wrapped in layers of pâté, duxelles (a finely chopped mushroom mixture), parma ham, and puff pastry, then baked. Rumor has it that Beef Wellington got its name from Arthur Wellesley, the 1st Duke of Wellington, who counted the dish among his favorite recipes.";
//        String authorKey = currentUser.getKey();
//        //String authorKey = "2138921738";
//        String getCoverImageName  = key + ".jpeg";
//        ArrayList<String> steps = new ArrayList<>();
//        steps.add("Wrap each piece of beef tightly in a triple layer of cling film to set its shape, then chill overnight.");
//        steps.add("Remove the cling film, then quickly sear the beef fillets in a hot pan with a little olive oil for 30-60 seconds until browned all over and rare in the middle. Remove from the pan and leave to cool.");
//        steps.add("Finely chop the mushrooms and fry in a hot pan with a little olive oil, the thyme leaves and some seasoning. When the mushrooms begin to release their juices, continue to cook over a high heat for about 10 minutes until all the excess moisture has evaporated and you are left with a mushroom paste (known as a duxelle). Remove the duxelle from the pan and leave to cool.");
//        steps.add("Cut the pastry in half, place on a lightly floured surface and roll each piece into a rectangle large enough to envelop one of the beef fillets. Chill in the refrigerator.");
//        steps.add("Lay a large sheet of cling film on a work surface and place 4 slices of Parma ham in the middle, overlapping them slightly, to create a square. Spread half the duxelle evenly over the ham.");
//        steps.add("Season the beef fillets, then place them on top of the mushroom-covered ham. Using the cling film, roll the Parma ham over the beef, then roll and tie the cling film to get a nice, evenly thick log. Repeat this step with the other beef fillet, then chill for at least 30 minutes.");
//        steps.add("Brush the pastry with the egg wash. Remove the cling film from the beef, then wrap the pastry around each ham-wrapped fillet. Trim the pastry and brush all over with the egg wash. Cover with cling film and chill for at least 30 minutes.");
//        steps.add("When you are ready to cook the beef wellingtons, score the pastry lightly and brush with the egg wash again, then bake at 200°C/Gas 6 for 15-20 minutes until the pastry is golden brown and cooked. Rest for 10 minutes before carving.");
//        steps.add("Serve the beef wellingtons sliced.");
//
//        ArrayList<String> ingredients = new ArrayList<>(); // Array of <amount, ingredient, unit> <5, salt, g>
//        //i1.add();
//        ingredients.add("2 x 400g beef fillets");
//        ingredients.add("Olive oil, for frying");
//        ingredients.add("500g mixture of wild mushrooms, cleaned");
//        ingredients.add("1 thyme sprig, leaves only");
//        ingredients.add("500g puff pastry");
//        ingredients.add("slices of Parma ham");
//        ingredients.add("2 egg yolks, beaten with 1 tbsp water and a pinch of salt");
//        ingredients.add("Sea salt and freshly ground black pepper");
//
//        Recipe curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);
//
//
//        firebaseClient.createNewPost(curR);
//        String imageID = getCoverImageName;
//
//        t = findViewById(R.id.textBox);
//        t.setText("Recipe Created Successfully");
//        i = findViewById(R.id.image);
//        i.setImageResource(R.drawable.post_image_1);
//
//        firebaseClient.uploadImageView(i, imageID);
//        firebaseClient.setRecipeCoverImageName(key, imageID);

        firebaseClient.modifyCollectPost("c453c0b2-2c60-48a0-a4d4-2026532e2dfa", true);

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