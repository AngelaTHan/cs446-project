package uwaterloo.cs446group6.increpeable.Users;

import java.util.ArrayList;

public class User {
    protected String key;
    protected String email; // cannot be changed
    protected String username;
    protected String description="Enter User Bio Here.";
    protected String profileImageName="emptyprofileimg.jpeg";
    protected Integer numLikes = 0;
    protected Integer numFollowers = 0;
    protected Integer numFollowing = 0;
    protected ArrayList<String> myPostIDs = new ArrayList<String>();
    protected ArrayList<String> collectedPostIDs = new ArrayList<String>();
    protected ArrayList<String> followerIDs = new ArrayList<String>();
    protected ArrayList<String> followingIDs = new ArrayList<String>();
    protected ArrayList<String> likedPostIDs = new ArrayList<String>();

    public User() {}

    public User(String email, String username, String key) {
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
    public Integer getNumFollowing() { return numFollowing; }
    public ArrayList<String> getMyPostIDs() { return myPostIDs; }
    public ArrayList<String> getCollectedPostIDs() { return collectedPostIDs; }
    public ArrayList<String> getFollowerIDs() { return followerIDs; }
    public ArrayList<String> getFollowingIDs() { return followingIDs; }
    public ArrayList<String> getLikedPostIDs() { return likedPostIDs; }

}