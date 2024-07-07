package com.telemedicine.registration.accountactivationandinitiation;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
@Data
@AllArgsConstructor
public class StringExpiration {
    private String generateKey;
    private Date issuedTime;
    private Date expirationTime;
}
