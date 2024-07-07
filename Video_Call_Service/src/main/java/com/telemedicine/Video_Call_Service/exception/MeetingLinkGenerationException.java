package com.telemedicine.Video_Call_Service.exception;

public class MeetingLinkGenerationException extends RuntimeException {

    public MeetingLinkGenerationException(String message) {
        super(message);
    }

    public MeetingLinkGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
