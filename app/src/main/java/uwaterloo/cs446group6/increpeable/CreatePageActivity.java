package uwaterloo.cs446group6.increpeable;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.error.ANError;
//import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.UUID;

import uwaterloo.cs446group6.increpeable.Recipe.Recipe;
import uwaterloo.cs446group6.increpeable.backend.ReturnFromFunction;

public class CreatePageActivity extends NotifyActivity {
    public static final int PICK_IMAGE = 100;

    private ImageButton backButton;
    private ImageButton saveButton;
    private Button selectImageButton;
    private LinearLayout imageSectionView;
    private ImageView imageToUploadView;
    private String imageName;
    private Button uploadImageButton;
    private TextView imageErrorView;
    private EditText titleView;
    private TextView locationView;
    private EditText descriptionView;
    private EditText ingredientEditTextView;
    private EditText stepEditTextView;
    private LinearLayout ingredientsView;
    private LinearLayout stepsView;
    private Button postButton;
    private Uri pathOfFile;
    private Button addIngredientButton;
    private Button addStepButton;

    private String key;
    private String title;
    private String location;
    private String description;
    private String authorKey;
    private String coverImageName;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;

    // location variables
    private String longitude;
    private String latitude;

    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // views
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
        imageSectionView = findViewById(R.id.imageSection);
        imageToUploadView = findViewById(R.id.imageToUpload);
        selectImageButton = findViewById(R.id.selectImageButton);
        titleView = findViewById(R.id.recipeName);
        locationView = findViewById(R.id.location);
        descriptionView = findViewById(R.id.description);
        ingredientEditTextView = findViewById(R.id.ingredient);
        ingredientsView = findViewById(R.id.inputIngredient);
        stepEditTextView = findViewById(R.id.step);
        stepsView = findViewById(R.id.inputStep);
        postButton = findViewById(R.id.postButton);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        addStepButton = findViewById(R.id.addStepButton);

        // initialize variables
        key = UUID.randomUUID().toString();
        authorKey = currentUser.getKey();
        coverImageName  = key + ".jpg";

        // get location helper
        ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }

//        AndroidNetworking.initialize(getApplicationContext());
//        AndroidNetworking.get("http://dev.virtualearth.net/REST/v1/Locations/" + latitude + ", " + longitude + "?key={AnnVlNDWt7a8pHmWJQ_OoqVkOrl_RJie4cb6exfB3cgTBOi3MlHlUVo0fn97nejJ}")
//                .build()
//                .getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // do anything with response
//                        System.out.println(response);
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        System.out.println(error);
//                    }
//                });

        // set up listeners
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditTextView(ingredientsView);
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditTextView(stepsView);
            }
        });

        // upload photo method
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageErrorView != null) {
                    imageErrorView.setVisibility(View.GONE);
                    imageErrorView = null;
                }
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE);
            }
        });

        // post final recipe
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleView.getText().toString();
                location = locationView.getText().toString();
                description = descriptionView.getText().toString();
                ingredients = getAllChildrenText(ingredientsView);
                steps = getAllChildrenText(stepsView);

                if (imageToUploadView.getDrawable() == null) {
                    imageErrorView = new TextView(getBaseContext());
                    imageErrorView.setId(View.generateViewId());
                    imageErrorView.setText("Please upload an image");
                    imageErrorView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    imageErrorView.setTextColor(Color.RED);
                    imageSectionView.addView(imageErrorView);
                }
                if (title.isEmpty()) {
                    titleView.setError("Please fill the required field");
                }
                if (location.isEmpty()) {
                    locationView.setError("Please fill the required field");
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

                if (!(imageToUploadView.getDrawable() != null && title.isEmpty() && location.isEmpty() && description.isEmpty() && ingredients.isEmpty() && steps.isEmpty())) {
                    Recipe recipe = new Recipe(key, title, location, description, authorKey, coverImageName, steps, ingredients);
                    firebaseClient.createNewPost(recipe);
                    firebaseClient.setRecipeCoverImageName(key, coverImageName);
                    firebaseClient.uploadImageView(imageToUploadView, key);
                }
            }
        });

    }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imageToUploadView.setImageURI(imageUri);
        }
    }

    @Override
    public void notifyActivity(ReturnFromFunction func_name) {
        switch(func_name) {
            case UPLOAD_IMAGEVIEW:
                finish();
                break;
            default:
                break;
            // Default case for all unused functions in firebaseClient
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        if (ActivityCompat.checkSelfPermission(
                CreatePageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                CreatePageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                locationView.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }



}