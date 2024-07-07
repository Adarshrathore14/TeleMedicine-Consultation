package com.telemedicine.appointment.exceptions;
public class NoSchedulesAvailableException extends Exception{
    public NoSchedulesAvailableException(String message){
        super(message);
    }
}
