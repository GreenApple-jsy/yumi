package com.example.yumi;

public class ChatData {
    private String userID;
    private String message;

    public ChatData(){}

    public ChatData(String userID, String message) {
        this.userID = userID;
        this.message = message;
    }

    public void setUserName(String userID) {
        this.userID = userID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }
}