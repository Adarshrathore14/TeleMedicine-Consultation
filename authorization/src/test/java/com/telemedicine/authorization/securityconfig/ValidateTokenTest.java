package com.telemedicine.authorization.securityconfig;
import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
public class ValidateTokenTest {
    private ValidateToken validateToken;
    private String dummyToken;

    @Before
    public void setUp() {
        validateToken = new ValidateToken();
        String jwtSecret = "AbcHospitalFSKfo+3f6jDrc3fOeJ2vtiLoB+Pn/zNpBv1gSykXb0I0jPVH58uoSt/aBzPtw+eq";
        Map<String,String> claims = new HashMap<>();
        claims.put("userId","admin");
        claims.put("role","Admin");
        dummyToken = Jwts
                .builder()
                .setClaims(claims)
                .setSubject("admin")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)), SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    public void extractUserName() {
        assertEquals("admin",validateToken.extractUserName(dummyToken));
    }

    @Test
    public void getExpirationDate() {
        Date expirationDate = validateToken.getExpirationDate(dummyToken);
        assertNotNull(expirationDate);
    }


    @Test
    public void extractRole() {
        String role = validateToken.extractRole(dummyToken);
        assertEquals("Admin",role);
        assertNotEquals("User",role);
    }

    @Test
    public void extractAllClaims() {
        Claims claims = validateToken.extractAllClaims(dummyToken);
        assertNotNull(claims);
        assertEquals("Admin",claims.get("role"));
        assertEquals("admin",claims.get("userId"));
    }

    @Test
    public void getSignkey() {
        Key key = validateToken.getSignkey();
        assertNotNull(key);
    }

    @Test
    public void isTokenExpired() {
        boolean tokenExpired = validateToken.isTokenExpired(dummyToken);
        assertFalse(tokenExpired);
    }

    @Test
    public void tokenValidation() {
        boolean tokenValidation = validateToken.isTokenExpired(dummyToken);
        assertFalse(tokenValidation);
    }
}