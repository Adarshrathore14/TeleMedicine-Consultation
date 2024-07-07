package com.telemedicine.patient.exceptions;
public class InvalidDoctorIdException extends Exception{
    public InvalidDoctorIdException(String message){
        super(message);
    }
}
