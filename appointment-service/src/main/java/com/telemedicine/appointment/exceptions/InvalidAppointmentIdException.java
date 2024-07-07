package com.telemedicine.appointment.exceptions;
public class InvalidAppointmentIdException extends Exception{
    public InvalidAppointmentIdException(String message){
        super(message);
    }
}
