package com.example.myapp;

public class RatingLimitException extends Exception{
    public RatingLimitException(String mesaj){
        super(mesaj);
    }

    public RatingLimitException(String message, Throwable cause){
        super(message, cause);
    }
}
