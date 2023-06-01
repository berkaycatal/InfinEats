package com.example.myapp.models;

public class Comment {
    private String key;
    private String content;
    private double rating;

    private String userId;

    public Comment() {

    }

    public Comment(String userId,String key, String content, double rating) {
        this.userId = userId;
        this.key = key;
        this.content = content;
        this.rating = rating;
    }

    public String getUserId(){return userId;}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
