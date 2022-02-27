package uwaterloo.cs446group7.increpeable.databaseClasses;

public class User {
    private String email;
    private String username;
    private String description;
    private String profileImageURL;
    private Integer numLikes = 0;
    private Integer numFollowers = 0;
//    private RealmList<ObjectId> myPostIDs;
//    private RealmList<ObjectId> collectedPostIDs;
//    private RealmList<ObjectId> followerIDs;
//    private RealmList<ObjectId> followingIDs;

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

//    public RealmList<ObjectId> getMyPostIDs() { return myPostIDs; }
//    public RealmList<ObjectId> getCollectedPostIDs() { return collectedPostIDs; }
//    public RealmList<ObjectId> getFollowerIDs() { return followerIDs; }
//    public RealmList<ObjectId> getFollowingIDs() { return followingIDs; }

    // setters
    public void setUsername(String username) { this.username = username; }
    public void setDescription(String description) { this.description = description; }
    public void setProfileImageURL(String profileImageURL) { this.profileImageURL = profileImageURL; }
    public void setNumLikes(Integer numLikes) { this.numLikes = numLikes; }
    public void setNumFollowers(Integer numFollowers) { this.numFollowers = numFollowers; }
    public void setEmail(String email) { this.email = email; }

//    public void setMyPostIDs(RealmList<ObjectId> myPostIDs) { this.myPostIDs = myPostIDs; }
//    public void setCollectedPostIDs(RealmList<ObjectId> collectedPostIDs) { this.collectedPostIDs = collectedPostIDs; }
//    public void setFollowerIDs(RealmList<ObjectId> followerIDs) { this.followerIDs = followerIDs; }
//    public void setFollowingIDs(RealmList<ObjectId> followingIDs) { this.followingIDs = followingIDs; }
} 