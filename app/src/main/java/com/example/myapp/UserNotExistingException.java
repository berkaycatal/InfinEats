package com.example.myapp;

public class UserNotExistingException extends Exception{
    public UserNotExistingException(String mesaj){
        super(mesaj);
    }
}
