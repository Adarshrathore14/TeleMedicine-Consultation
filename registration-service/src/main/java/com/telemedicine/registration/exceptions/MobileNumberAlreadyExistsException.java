package com.telemedicine.registration.exceptions;

public class MobileNumberAlreadyExistsException extends Exception{
    public MobileNumberAlreadyExistsException(String message){
        super(message);
    }
}
