package com.telemedicine.registration.exceptions;

public class UserNameAndEmailAlreadyExistsException extends Exception{
    public UserNameAndEmailAlreadyExistsException(String message){
        super(message);
    }
}
