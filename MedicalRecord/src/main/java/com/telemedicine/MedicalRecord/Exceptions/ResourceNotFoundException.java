package com.telemedicine.MedicalRecord.Exceptions;

public class ResourceNotFoundException extends RuntimeException{

	 public ResourceNotFoundException(String message){
	        super(message);
	    }
}