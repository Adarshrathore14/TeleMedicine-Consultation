package com.telemedicine.payment.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.telemedicine.payment.dto.ErrorModel;

import feign.FeignException.FeignClientException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class AppExceptionHandler {
	
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleNotFoundException(ResourceNotFoundException ex,final HttpServletRequest request) {
    	ErrorModel errorResponse = new ErrorModel(request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(value=FeignClientException.class)
   	public ResponseEntity<ErrorModel> handleExceptionCall2(FeignClientException e,final HttpServletRequest request)
   	{
    	System.out.println(e.getMessage());
    	  String responseBody=e.getMessage();
    	  Pattern pattern = Pattern.compile("\"errorMessage\":\"(.*?)\"");
          Matcher matcher = pattern.matcher(responseBody);
          String errorMessage=null;
          if (matcher.find()) 
        	  errorMessage = matcher.group(1);
          ErrorModel errorResponse = new ErrorModel(request.getRequestURI(),errorMessage);
   		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

   	}
}
