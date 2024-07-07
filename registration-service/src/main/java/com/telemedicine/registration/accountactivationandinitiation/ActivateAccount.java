package com.telemedicine.registration.accountactivationandinitiation;
import com.telemedicine.registration.configurations.AccountActivationConfiguration;
import com.telemedicine.registration.configurations.HospitalMessageConfiguration;
import com.telemedicine.registration.entity.PatientEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ActivateAccount {
    @NonNull
    private final AccountActivationConfiguration accountActivationConfiguration;
    @NonNull
    private final HospitalMessageConfiguration hospitalMessageConfiguration;
    private final List<PatientEntity> patientList = new ArrayList<>();
    public String generateAccountActivationToken(String patientName, PatientEntity patient){
        Map<String,String> claims = new HashMap<>();
        claims.put("patientName",patientName);
        return createAccountActivationToken(claims,patient);
    }
    private String createAccountActivationToken(Map<String, String> claims, PatientEntity patient) {
        patientList.add(patient);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(hospitalMessageConfiguration.getCode())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ accountActivationConfiguration.getExpirationTime()))
                .signWith(getsignkey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getsignkey() {
        byte[] keys = Decoders.BASE64.decode(accountActivationConfiguration.getSecretKey());
        return Keys.hmacShaKeyFor(keys);
    }
    public PatientEntity getPatientByPatientName(String patientName){
        PatientEntity patient = patientList.stream().filter(patientEntity -> patientEntity.getPatientName().equals(patientName))
                .toList().get(0);
        patientList.removeIf(patientEntity -> patientEntity.getPatientName().equals(patientName));
        return patient;
    }
}
