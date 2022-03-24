package uwaterloo.cs446group6.increpeable;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;

public class UploadPostActivity extends NotifyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        firebaseClient.modifyCollectPost("d8837c3d-e1d6-48de-ac68-4efe2c51d81a", true);

//        String key = UUID.randomUUID().toString();
//        String title = "Beef Wellington";
//        String location = "London, UK";
//        String description = "A traditional Beef Wellington consists of a beef tenderloin wrapped in layers of pâté, duxelles (a finely chopped mushroom mixture), parma ham, and puff pastry, then baked. Rumor has it that Beef Wellington got its name from Arthur Wellesley, the 1st Duke of Wellington, who counted the dish among his favorite recipes.";
//        String authorKey = currentUser.getKey();
//        String getCoverImageName  = key + ".jpeg";
//        firebaseClient.setRecipeCoverImageName(gordon, getCoverImageName);
//        ArrayList<String> ingredients = new ArrayList<>();
//        ingredients.add("2 x 400g beef fillets");
//        ingredients.add("Olive oil, for frying");
//        ingredients.add("500g mixture of wild mushrooms, cleaned");
//        ingredients.add("1 thyme sprig, leaves only");
//        ingredients.add("500g puff pastry");
//        ingredients.add("slices of Parma ham");
//        ingredients.add("2 egg yolks, beaten with 1 tbsp water and a pinch of salt");
//        ingredients.add("Sea salt and freshly ground black pepper");
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
//        Recipe curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);
//        firebaseClient.createNewPost(curR);
//
//        key = UUID.randomUUID().toString();
//        title = "Potluck Chopped Salad";
//        location = "Waterloo, Canada";
//        description = "This style of salad is a staple of Hmong potlucks. It was one of the first things that new Hmong immigrants to the Midwest learned to make from the families who sponsored them in the 1980s and ’90s.";
//        authorKey = currentUser.getKey();
//        getCoverImageName  = key + ".jpeg";
//        firebaseClient.setRecipeCoverImageName(gordon, getCoverImageName);
//        ingredients = new ArrayList<>();
//        ingredients.add("1  large eggs");
//        ingredients.add("1/2 garlic clove, finely grated");
//        ingredients.add("1/4 cup buttermilk");
//        ingredients.add("1  cup mayonnaise");
//        ingredients.add("1  tsp. Aji-No-Moto umami seasoning (MSG; optional)");
//        ingredients.add(" 1  romaine heart");
//        ingredients.add("1  small carrot, peeled, cut into matchsticks");
//        ingredients.add(" 1  medium radish");
//        steps = new ArrayList<>();
//        steps.add("Bring a medium pot of water to a boil. Carefully lower eggs into water with a slotted spoon and cook 8 minutes. Transfer eggs to a bowl of ice water and let cool. Remove eggs from ice water, pat dry, and peel. Slice eggs crosswise and set aside for serving.");
//        steps.add("Whisk garlic, buttermilk, mayonnaise, dill, and Aji-No-Moto seasoning (if using) in a large bowl to combine; season with salt and pepper. Add romaine, carrot, radish, and herbs and toss to coat; season with salt.");
//        steps.add("To serve, tuck egg slices in and around salad.");
//        curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);
//        firebaseClient.createNewPost(curR);
//
//        key = UUID.randomUUID().toString();
//        title = "Carbonara";
//        location = "Waterloo, Canada";
//        description = "This dish is a deli egg-bacon-and-cheese-on-a-roll that has been pasta-fied, fancified, fetishized and turned into an Italian tradition that, like many inviolate Italian traditions, is actually far less old than the Mayflower.";
//        authorKey = currentUser.getKey();
//        getCoverImageName  = key + ".jpeg";
//        firebaseClient.setRecipeCoverImageName(gordon, getCoverImageName);
//        ingredients = new ArrayList<>();
//        ingredients.add("Salt");
//        ingredients.add("2 large eggs and 2 large yolks, room temperature");
//        ingredients.add("1 ounce (about 1/3 packed cup) grated pecorino Romano, plus additional for serving");
//        ingredients.add("1 ounce (about 1/3 packed cup) grated Parmesan");
//        ingredients.add("Coarsely ground black pepper");
//        ingredients.add("1 tablespoon olive oil");
//        ingredients.add("3 1/2 ounces of slab guanciale (see recipe), pancetta or bacon, sliced into pieces about 1/4 inch thick by 1/3 inch square");
//        ingredients.add("12 ounces spaghetti (about 3/4 box)");
//        steps = new ArrayList<>();
//        steps.add("Place a large pot of lightly salted water (no more than 1 tablespoon salt) over high heat, and bring to a boil. Fill a large bowl with hot water for serving, and set aside.");
//        steps.add("In a mixing bowl, whisk together the eggs, yolks and pecorino and Parmesan. Season with a pinch of salt and generous black pepper.");
//        steps.add("Set the water to boil. Meanwhile, heat oil in a large skillet over medium heat, add the pork, and sauté until the fat just renders, on the edge of crispness but not hard. Remove from heat and set aside.");
//        steps.add("Add pasta to the water and boil until a bit firmer than al dente. Just before pasta is ready, reheat guanciale in skillet, if needed. Reserve 1 cup of pasta water, then drain pasta and add to the skillet over low heat. Stir for a minute or so.");
//        steps.add("Empty serving bowl of hot water. Dry it and add hot pasta mixture. Stir in cheese mixture, adding some reserved pasta water if needed for creaminess. Serve immediately, dressing it with a bit of additional grated pecorino and pepper.");
//        curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);
//        firebaseClient.createNewPost(curR);
//
//        key = UUID.randomUUID().toString();
//        title = "Slow Cooker Beef Stew";
//        location = "Waterloo, Canada";
//        description = "This easy slow cooker beef stew recipe made with potatoes, carrots, celery, broth, herbs, and spices is hearty and comforting. You won't be slow to say 'yum'!";
//        authorKey = currentUser.getKey();
//        getCoverImageName  = key + ".jpeg";
//        firebaseClient.setRecipeCoverImageName(gordon, getCoverImageName);
//        ingredients = new ArrayList<>();
//        ingredients.add("2 pounds beef stew meat, cut into 1-inch pieces");
//        ingredients.add("1/4 cup all-purpose flour");
//        ingredients.add("1/2 teaspoon salt");
//        ingredients.add("1/2 teaspoon ground black pepper");
//        ingredients.add("1 1/2 cups beef broth");
//        ingredients.add("4 medium carrots, sliced");
//        ingredients.add("3 medium potatoes, diced");
//        ingredients.add("1 medium onion, chopped");
//        ingredients.add("1 stalk celery, chopped");
//        ingredients.add("1 teaspoon Worcestershire sauce");
//        ingredients.add("1 teaspoon ground paprika");
//        ingredients.add("1 clove garlic, minced");
//        ingredients.add("1 large bay leaf");
//        ingredients.add("");
//        ingredients.add("");
//        steps = new ArrayList<>();
//        steps.add("Place meat in slow cooker.");
//        steps.add("Mix flour, salt, and pepper together in a small bowl. Pour over meat, and stir until meat is coated.");
//        steps.add("Add beef broth, carrots, potatoes, onion, celery, Worcestershire sauce, paprika, garlic, and bay leave; stir to combine.");
//        steps.add("Cover, and cook until beef is tender enough to cut with a spoon, on Low for 8 to 12 hours, or on High for 4 to 6 hours.");
//        curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);
//        firebaseClient.createNewPost(curR);
//
//        key = UUID.randomUUID().toString();
//        title = "Honey Butter Toast";
//        location = "Toronto, Canada";
//        description = "Super good honey toast which taste so delicious for breakfast. It is one of the easiest recipe you could ever make. The more butter you add the more tasty it gets. Instead of having jam toast you can enjoy this honey toast for a variation.";
//        authorKey = currentUser.getKey();
//        getCoverImageName  = key + ".jpeg";
//        firebaseClient.setRecipeCoverImageName(gordon, getCoverImageName);
//        ingredients = new ArrayList<>();
//        ingredients.add("1 slice bread");
//        ingredients.add("1/ 2 tablespoon butter");
//        ingredients.add("1 teaspoon honey");
//        steps = new ArrayList<>();
//        steps.add("Toast the bread. Spread with honey, then top with the butter.");
//        curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);
//        firebaseClient.createNewPost(curR);
//
//        key = UUID.randomUUID().toString();
//        title = "Good Old Fashioned Pancakes";
//        location = "Toronto, Canada";
//        description = "This is a great recipe that I found in my Grandma's recipe book. Judging from the weathered look of this recipe card, this was a family favorite.";
//        authorKey = currentUser.getKey();
//        getCoverImageName  = key + ".jpeg";
//        firebaseClient.setRecipeCoverImageName(gordon, getCoverImageName);
//        ingredients = new ArrayList<>();
//        ingredients.add("1 12 cups all-purpose flour");
//        ingredients.add("3 1/2 teaspoons baking powder ");
//        ingredients.add("1/4teaspoon salt, or more to taste");
//        ingredients.add("1 tablespoon white sugar");
//        ingredients.add("1 1/4 cups milk");
//        ingredients.add("1 egg");
//        ingredients.add("3 tablespoons butter, melted");
//        steps = new ArrayList<>();
//        steps.add("In a large bowl, sift together the flour, baking powder, salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; mix until smooth.");
//        steps.add("Heat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Brown on both sides and serve hot.");
//        curR = new Recipe(key,title,location,description,authorKey,getCoverImageName,steps,ingredients);
//        firebaseClient.createNewPost(curR);
    }
}

