package uwaterloo.cs446group7.increpeable.databaseClasses;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String email;
    private String username;
    private String description;
    private String profileImageURL;
    private Integer numLikes = 0;
    private Integer numFollowers = 0;
    private String key;
    private ArrayList<String> postIDs = new ArrayList<String>();

    public User() {}

    public User(String email, String username, String description, String profileImageURL) {
        this.email = email;
        this.username = username;
        this.description = description;
        this.profileImageURL = profileImageURL;
    }

    // getters
    public String getUsername() { return username; }
    public String getDescription() { return description; }
    public String getProfileImageURL() { return profileImageURL; }
    public Integer getNumLikes() { return numLikes; }
    public Integer getNumFollowers() { return numFollowers; }
    public String getEmail() { return email; }
    public String getKey() { return key; }
    public ArrayList<String> getPostIDs() { return postIDs; }

    // setters
    public void setUsername(String username) {
        this.username = username;
        Map<String, Object> postValues = new HashMap<String,Object>();
        postValues.put("description", description);
        postValues.put("email", email);
        postValues.put("key", key);
        postValues.put("numFollowers", numFollowers);
        postValues.put("numLikes", numLikes);
        postValues.put("profileImageURL", profileImageURL);
        postValues.put("username", username);
        FirebaseDatabase.getInstance().getReference().child("UserAccounts").child(key).updateChildren(postValues);
    }
    public void setDescription(String description) { this.description = description; }
    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
        Map<String, Object> postValues = new HashMap<String,Object>();
        postValues.put("description", description);
        postValues.put("email", email);
        postValues.put("key", key);
        postValues.put("numFollowers", numFollowers);
        postValues.put("numLikes", numLikes);
        postValues.put("profileImageURL", profileImageURL);
        postValues.put("username", username);
        FirebaseDatabase.getInstance().getReference().child("UserAccounts").child(key).updateChildren(postValues);
    }
    public void setNumLikes(Integer numLikes) { this.numLikes = numLikes; }
    public void setNumFollowers(Integer numFollowers) { this.numFollowers = numFollowers; }
    public void setEmail(String email) { this.email = email; }
    public void setKey(String key) { this.key = key; } // this function should only be called once
    public void setPostIDs(ArrayList<String> postIDs) { this.postIDs = postIDs; }

} 