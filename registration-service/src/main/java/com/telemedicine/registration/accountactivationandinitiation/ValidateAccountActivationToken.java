package com.telemedicine.registration.accountactivationandinitiation;
import com.telemedicine.registration.configurations.AccountActivationConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
@Component
@AllArgsConstructor
public class ValidateAccountActivationToken {
    private final AccountActivationConfiguration accountActivationConfiguration;
    private Claims extractAllClaims(String accountActivationToken)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getsignkey())
                .build()
                .parseClaimsJws(accountActivationToken)
                .getBody();
    }
    private Date getExpirationDate(String accountActivationToken)
    {
        return extractAllClaims(accountActivationToken).getExpiration();
    }
    public String extractPatientName(String accountActivationToken){
        return extractAllClaims(accountActivationToken).get("patientName").toString();
    }
    public boolean validateToken(String accountActivationToken){
        return !getExpirationDate(accountActivationToken).before(new Date());
    }

    private Key getsignkey() {
        byte[] keys = Decoders.BASE64.decode(accountActivationConfiguration.getSecretKey());
        return Keys.hmacShaKeyFor(keys);
    }
}
