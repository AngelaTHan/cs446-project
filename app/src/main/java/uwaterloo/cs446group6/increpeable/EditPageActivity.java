package uwaterloo.cs446group6.increpeable;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

public class EditPageActivity extends NotifyActivity {
    private ImageButton backButton;
    private EditText titleView;
    private EditText descriptionView;
    private EditText ingredientEditTextView;
    private EditText stepEditTextView;
    private LinearLayout ingredientsView;
    private LinearLayout stepsView;
    private Button updateRecipeButton;
    private Button addIngredientButton;
    private Button addStepButton;

    private String title;
    private String description;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;

    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // views
        backButton = findViewById(R.id.backButton);
        titleView = findViewById(R.id.recipeName);
        descriptionView = findViewById(R.id.description);
        ingredientEditTextView = findViewById(R.id.ingredient);
        ingredientsView = findViewById(R.id.inputIngredient);
        stepEditTextView = findViewById(R.id.step);
        stepsView = findViewById(R.id.inputStep);
        updateRecipeButton = findViewById(R.id.updateRecipeButton);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        addStepButton = findViewById(R.id.addStepButton);

        // grab data
        currentRecipe = firebaseClient.getCurrentRecipe();

        // set data
        titleView.setText(currentRecipe.getTitle());
        descriptionView.setText(currentRecipe.getDescription());
        ingredients = currentRecipe.getIngredients();
        for (String ingredient : ingredients) {
            EditText ingredientEditText = new EditText(this);
            ingredientEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ingredientEditText.setText(ingredient);
            ingredientsView.addView(ingredientEditText);
        }
        steps = currentRecipe.getSteps();
        for (String step : steps) {
            EditText stepEditText = new EditText(this);
            stepEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            stepEditText.setText(step);
            stepsView.addView(stepEditText);
        }

        // set up listeners for adding ingredients
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditTextView(ingredientsView);
            }
        });

        // set up listeners for adding steps
        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditTextView(stepsView);
            }
        });

        // back, will not save changes
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // updates the recipe
        updateRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleView.getText().toString();
                description = descriptionView.getText().toString();
                ingredients = getAllChildrenText(ingredientsView);
                steps = getAllChildrenText(stepsView);

                if (title.isEmpty()) {
                    titleView.setError("Please fill the required field");
                }
                if (description.isEmpty()) {
                    descriptionView.setError("Please fill the required field");
                }
                if (ingredients.isEmpty()) {
                    ingredientEditTextView.setError("Please fill the required field");
                }
                if (steps.isEmpty()) {
                    stepEditTextView.setError("Please fill the required field");
                }

                if (!(title.isEmpty() && description.isEmpty() && ingredients.isEmpty() && steps.isEmpty())) {
                    Recipe recipe = new Recipe(currentRecipe.getKey(), title, currentRecipe.getLocation(), description, currentRecipe.getAuthorKey(), currentRecipe.getCoverImageName(), steps, ingredients, currentRecipe.getNumLikes(), currentRecipe.getNumCollects());
                    firebaseClient.updatePost(recipe);
                }
            }
        });
    }

    // getAllChildrenText() gets all of the children in the LinearLayout
    private ArrayList<String> getAllChildrenText(LinearLayout parentView) {
        ArrayList<String> childrenText = new ArrayList<String>();
        // save only the non-empty text
        for (int i = 0; i < parentView.getChildCount(); i++) {
            EditText child = (EditText) parentView.getChildAt(i);
            String text = child.getText().toString();
            if (!text.isEmpty() && !text.trim().isEmpty()) {
                childrenText.add(text);
            }
        }
        return childrenText;
    }

    // addEditTextView() adds an edit text view
    private void addEditTextView(LinearLayout parentView) {
        EditText lastIngredient = (EditText) parentView.getChildAt(parentView.getChildCount()-1);
        String lastIngredientText = lastIngredient.getText().toString();

        // add only the last edit text field is filled
        if (!lastIngredientText.isEmpty() && !lastIngredientText.trim().isEmpty()) {
            EditText editText = new EditText(getBaseContext());
            editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            if (parentView.getResources().getResourceEntryName(parentView.getId()).toString() == "inputIngredient") {
                editText.setHint("Enter ingredient");
            } else {
                editText.setHint("Enter step");
            }
            parentView.addView(editText);
        } else {
            lastIngredient.setError("Please fill before adding another");
        }
    }

    @Override
    public void notifyActivity(ReturnFromFunction func_name) {
        switch(func_name) {
            case UPDATE_POST:
                Intent goViewPostIntent = new Intent(EditPageActivity.this, ViewPageActivity.class);
                goViewPostIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(goViewPostIntent);
                finish();
                break;
            default:
                break;
            // Default case for all unused functions in firebaseClient
        }
    }
}
