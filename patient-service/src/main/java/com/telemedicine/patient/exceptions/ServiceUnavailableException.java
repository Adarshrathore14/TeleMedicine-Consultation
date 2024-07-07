package com.telemedicine.patient.exceptions;
public class ServiceUnavailableException extends Exception{
    public ServiceUnavailableException(String message){
        super(message);
    }
}
