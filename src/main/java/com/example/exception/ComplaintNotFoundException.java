package com.example.exception;

public class ComplaintNotFoundException extends Exception{
    public ComplaintNotFoundException(String message) {
        super(message);
    }
}
