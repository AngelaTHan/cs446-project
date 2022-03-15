package uwaterloo.cs446group6.increpeable.Users;

import com.google.firebase.database.DatabaseReference;

import uwaterloo.cs446group6.increpeable.Users.User;

public class DB_User extends User {
    public DB_User() {}

    public DB_User(String email, String username, String key) {
        super(email, username, key);
    }

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
    public void setNumFollowing(DatabaseReference mDatabase, Integer numFollowing) {
        this.numFollowers = numFollowing;
        mDatabase.child("UserAccounts").child(key).child("numFollowing").setValue(numFollowing);
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
    public void addLikedPostID(DatabaseReference mDatabase, String likeID) {
        likedPostIDs.add(likeID);
        mDatabase.child("UserAccounts").child(key).child("likedPostIDs").setValue(followingIDs);
    }
    public void deleteLikedPostID(DatabaseReference mDatabase, String likeID) {
        likedPostIDs.remove(likeID);
        mDatabase.child("UserAccounts").child(key).child("likedPostIDs").setValue(followingIDs);
    }
} 