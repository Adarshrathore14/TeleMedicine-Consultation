package com.telemedicine.registration.accountactivationandinitiation;
import com.telemedicine.registration.configurations.HospitalMessageConfiguration;
import com.telemedicine.registration.configurations.ScheduledTimeConfiguration;
import com.telemedicine.registration.exceptions.InvalidMobileNumberException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
@RequiredArgsConstructor
public class PreRegistrationKey {
    @NonNull
    private ScheduledTimeConfiguration scheduledTimeConfiguration;
    @NonNull
    private HospitalMessageConfiguration hospitalMessageConfiguration;
    @NonNull
    private Random randomCodeGenerator;
    private  static final String SCHEDULED_DURATION = "0 */3 * * * *";
    private final Map<String,StringExpiration> activationCodes = new HashMap<>();

    public String generateActivationCode(String mobileNumber){
        String activationCode = hospitalMessageConfiguration.getCode()+
                randomCodeGenerator.nextInt(9999-1000+1)+1000;
        StringExpiration stringExpiration = new StringExpiration(activationCode,new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()+ scheduledTimeConfiguration.getActivationCodeExpirationTime()));
        addActivationCodes(mobileNumber,stringExpiration);
        return activationCode;
    }
    @Scheduled(cron = SCHEDULED_DURATION)
    private void deleteActivationCode(){
        activationCodes.values().removeIf(stringExpiration -> stringExpiration.getExpirationTime().after(
                new Date(System.currentTimeMillis())));
    }
    public boolean checkCode(String mobileNumber,String code) throws InvalidMobileNumberException {
        StringExpiration stringExpiration = getActivationCode(mobileNumber);
        if(stringExpiration==null){
            throw new InvalidMobileNumberException("enter a correct mobile number");
        }
        return stringExpiration.getGenerateKey().equals(code);
    }
    private void addActivationCodes(String mobileNumber,StringExpiration stringExpiration){
        activationCodes.put(mobileNumber,stringExpiration);
    }
    private StringExpiration getActivationCode(String mobileNumber){
        return activationCodes.get(mobileNumber);
    }
}
