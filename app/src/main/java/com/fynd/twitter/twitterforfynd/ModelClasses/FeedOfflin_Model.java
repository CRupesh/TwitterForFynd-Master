package com.fynd.twitter.twitterforfynd.ModelClasses;

import io.realm.RealmObject;

public class FeedOfflin_Model extends RealmObject {

    private String userFeed;

    private String userName;

    private String imageUrl;

    private String noOfLikes;

    private String noOfRetweeted;


    public String getUserFeed() {
        return userFeed;
    }

    public void setUserFeed(String userFeed) {
        this.userFeed = userFeed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(String noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public String getNoOfRetweeted() {
        return noOfRetweeted;
    }

    public void setNoOfRetweeted(String noOfRetweeted) {
        this.noOfRetweeted = noOfRetweeted;
    }
}
