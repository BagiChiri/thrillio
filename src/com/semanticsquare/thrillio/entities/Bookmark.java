package com.semanticsquare.thrillio.entities;

import com.semanticsquare.thrillio.constants.KidFriendlyStatus;

public abstract class Bookmark {
    private long id;
    private String title;
    private String profileURL;
    private KidFriendlyStatus kidFriendlyStatus = KidFriendlyStatus.UNKNOWN;
    private User kidFriendlyMarkedBy;
    private User sharedBy;

    public User getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(User sharedBy) {
        this.sharedBy = sharedBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public abstract boolean isKidFriendlyEligible();

    public KidFriendlyStatus getKidFriendlyStatus() {
        return kidFriendlyStatus;
    }

    public void setKidFriendlyStatus(KidFriendlyStatus kidFriendlyStatus) {
        this.kidFriendlyStatus = kidFriendlyStatus;
    }

    public User getKidFriendlyMarkedBy() {
        return kidFriendlyMarkedBy;
    }

    public void setKidFriendlyMarkedBy(User user) {
        this.kidFriendlyMarkedBy = user;
    }
}
