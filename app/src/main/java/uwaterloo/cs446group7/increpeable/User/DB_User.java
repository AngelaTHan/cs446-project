package uwaterloo.cs446group7.increpeable.User;

import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class DB_User {
    private String key;
    private String email; // cannot be changed
    private String username;
    private String description="";
    private String profileImageName="";
    private Integer numLikes = 0;
    private Integer numFollowers = 0;
    private ArrayList<String> myPostIDs = new ArrayList<String>();
    private ArrayList<String> collectedPostIDs = new ArrayList<String>();
    private ArrayList<String> followerIDs = new ArrayList<String>();
    private ArrayList<String> followingIDs = new ArrayList<String>();

    public DB_User() {}

    public DB_User(String email, String username, String key) {
        this.email = email;
        this.username = username;
        this.key = key;
    }

    // getters
    public String getKey() { return key; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getDescription() { return description; }
    public String getProfileImageName() { return profileImageName; }
    public Integer getNumLikes() { return numLikes; }
    public Integer getNumFollowers() { return numFollowers; }
    public ArrayList<String> getMyPostIDs() { return myPostIDs; }
    public ArrayList<String> getCollectedPostIDs() { return collectedPostIDs; }
    public ArrayList<String> getFollowerIDs() { return followerIDs; }
    public ArrayList<String> getFollowingIDs() { return followingIDs; }

    // Register myself into database
    public void registerUser(DatabaseReference mDatabase) {
        mDatabase.child("UserAccounts").child(key).setValue(this);
    }

    // setters
    public void setUsername(DatabaseReference mDatabase, String username) {
        this.username = username;
        mDatabase.child("UserAccounts").child(key).child("username").setValue(username);
    }
    public void setDescription(DatabaseReference mDatabase, String description) {
        this.description = description;
        mDatabase.child("UserAccounts").child(key).child("description").setValue(description);
    }
    public void setProfileImageName(DatabaseReference mDatabase, String profileImageURL) {
        this.profileImageName = profileImageURL;
        mDatabase.child("UserAccounts").child(key).child("profileImageURL").setValue(profileImageURL);
    }
    public void setNumLikes(DatabaseReference mDatabase, Integer numLikes) {
        this.numLikes = numLikes;
        mDatabase.child("UserAccounts").child(key).child("numLikes").setValue(numLikes);
    }
    public void setNumFollowers(DatabaseReference mDatabase, Integer numFollowers) {
        this.numFollowers = numFollowers;
        mDatabase.child("UserAccounts").child(key).child("numFollowers").setValue(numFollowers);
    }

    // Adders & Deleters
    public void addMyPostID(DatabaseReference mDatabase, String postID) {
        myPostIDs.add(postID);
        mDatabase.child("UserAccounts").child(key).child("myPostIDs").setValue(myPostIDs);
    }
    public void addCollectedPostID(DatabaseReference mDatabase, String postID) {
        collectedPostIDs.add(postID);
        mDatabase.child("UserAccounts").child(key).child("collectedPostIDs").setValue(collectedPostIDs);
    }
    public void deleteCollectedPostID(DatabaseReference mDatabase, String postID) {
        collectedPostIDs.remove(postID);
        mDatabase.child("UserAccounts").child(key).child("collectedPostIDs").setValue(collectedPostIDs);
    }
    public void addFollowersID(DatabaseReference mDatabase, String followerID) {
        followerIDs.add(followerID);
        mDatabase.child("UserAccounts").child(key).child("followerIDs").setValue(followerIDs);
    }
    public void deleteFollowersID(DatabaseReference mDatabase, String followerID) {
        followerIDs.remove(followerID);
        mDatabase.child("UserAccounts").child(key).child("followerIDs").setValue(followerIDs);
    }
    public void addFollowingsID(DatabaseReference mDatabase, String followingID) {
        followingIDs.add(followingID);
        mDatabase.child("UserAccounts").child(key).child("followingIDs").setValue(followingIDs);
    }
    public void deleteFollowingsID(DatabaseReference mDatabase, String followingID) {
        followingIDs.remove(followingID);
        mDatabase.child("UserAccounts").child(key).child("followingIDs").setValue(followingIDs);
    }
} 