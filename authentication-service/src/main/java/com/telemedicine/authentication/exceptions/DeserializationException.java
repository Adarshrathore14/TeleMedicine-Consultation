package com.telemedicine.authentication.exceptions;

public class DeserializationException extends RuntimeException{
    public DeserializationException(String message){
        super(message);
    }
}
