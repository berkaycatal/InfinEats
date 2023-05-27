package com.example.myapp;

public class Comment {
    private String key, rating, userId;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "key='" + key + '\'' +
                ", rating='" + rating + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
