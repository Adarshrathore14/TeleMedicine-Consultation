package com.telemedicine.appointment.exceptions;

public class AppointmentAlreadyExistsException extends Exception {
    public AppointmentAlreadyExistsException(String message) {
        super(message);
    }
}
