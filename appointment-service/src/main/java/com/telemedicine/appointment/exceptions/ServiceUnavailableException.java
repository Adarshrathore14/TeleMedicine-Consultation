package com.telemedicine.appointment.exceptions;
public class ServiceUnavailableException extends Exception{
    public ServiceUnavailableException(String message){
        super(message);
    }
}
