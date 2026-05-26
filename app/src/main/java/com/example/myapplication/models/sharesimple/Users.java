package com.example.myapplication.models.sharesimple;

public class Users {

    private String uid;
    private String userName;
    private String email;
    private String phoneNumber;

    public Users() {
        // Required empty constructor for Firebase
    }

    public Users(String uid, String userName, String email, String phoneNumber) {
        this.uid = uid;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
