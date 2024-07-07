package com.telemedicine.Video_Call_Service.exception;

public class VideoConsultationNotFoundException extends RuntimeException {

    public VideoConsultationNotFoundException(String message) {
        super(message);
    }

    public VideoConsultationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
