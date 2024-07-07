package com.telemedicine.authorization.securityconfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
@Component
public class ValidateToken {
    public String extractUserName(String token) {
        return extractAllClaims(token).get("userId").toString();
    }


    public Date getExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role").toString();
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    Key getSignkey() {
        String jwtSecret = "AbcHospitalFSKfo+3f6jDrc3fOeJ2vtiLoB+Pn/zNpBv1gSykXb0I0jPVH58uoSt/aBzPtw+eq";
        byte[] keys = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keys);
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public boolean tokenValidation(String token) {
        return !isTokenExpired(token);
    }
}
