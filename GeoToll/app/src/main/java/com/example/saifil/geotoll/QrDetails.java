package com.example.saifil.geotoll;

public class QrDetails {

    private String userID;
    private String tollID;
    private String photoUrl;
    private int status; //single / return
    private int cost;

    public QrDetails() {
    }

    public QrDetails(String userID, String tollID, String photoUrl, int status, int cost) {
        this.userID = userID;
        this.tollID = tollID;
        this.photoUrl = photoUrl;
        this.status = status;
        this.cost = cost;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTollID(String tollID) {
        this.tollID = tollID;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getUserID() {
        return userID;
    }

    public String getTollID() {
        return tollID;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getStatus() {
        return status;
    }

    public int getCost() {
        return cost;
    }
}
