package com.telemedicine.patient.feignresponse;
import com.telemedicine.patient.exceptions.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import java.io.IOException;
@Component
public class FeignExceptionHandler implements ErrorDecoder{
    @Override
    public Exception decode(String methodKey, Response response){
        int statusCode = response.status();
        String exceptionMessage=getErrorMessage(response);
        if(statusCode==404){
            if(exceptionMessage.contains("doctor")){
                return new InvalidDoctorIdException(exceptionMessage);
            } else if (exceptionMessage.contains("no schedules")) {
                return new NoSchedulesAvailableException(exceptionMessage);
            } else if (exceptionMessage.contains("slot Id")) {
                return new InvalidSlotIdException(exceptionMessage);
            }
        }
        if(statusCode== 409 && (exceptionMessage.contains("already booked"))) {
                return new AppointmentAlreadyExistsException(exceptionMessage);

        }
        return new ServiceUnavailableException("service unavailable");
        //(if the Feign service is dependent on Another Feign if that feign is not available means That is why
        //In our case Patient->Appointment->Billing (If the Billing is down, Appointment will throw ServiceUnavailableError).To handle that in the Patient That is why.
        //Note this is not for circuit breaker handling between patient and appointment. But for (appointment-Billing)
    }
    private String getErrorMessage(Response response){
        String message="";
        try{
            message = new String(response.body().asInputStream().readAllBytes());
        }
        catch (IOException exception){
            throw new DecodingException(exception.getLocalizedMessage());
        }
        return message;
    }
}
