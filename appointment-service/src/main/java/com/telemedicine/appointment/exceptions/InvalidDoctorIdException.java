package com.telemedicine.appointment.exceptions;
public class InvalidDoctorIdException extends Exception{
    public InvalidDoctorIdException(String message){
        super(message);
    }
}
