package com.telemedicine.authentication.util;
import com.telemedicine.authentication.configurations.JwtConfiguration;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import com.telemedicine.authentication.repository.AuthenticationRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class GenerateJwtToken {
	private JwtConfiguration jwtConfiguration;
	private AuthenticationRepository authenticationRepository;
	public String generateToken(String name)
	{
		AuthenticationEntity authenticationEntity;
		authenticationEntity = authenticationRepository.findByUserName(name).orElse(new AuthenticationEntity());
		if(authenticationEntity.getUserId()==null){
			authenticationEntity = authenticationRepository.findByEmail(name).orElse(new AuthenticationEntity());
		}
		String userId = authenticationEntity.getUserId();
		String userRole = authenticationEntity.getRole();
		String userName = authenticationEntity.getUserName();
		Map<String,String> claims = new HashMap<>();
		claims.put("userId",userId);
		claims.put("role", userRole);
		return createToken(claims,userName);
	}
	private String createToken(Map<String, String> claims, String username) {
		return Jwts.builder()
				.setClaims(claims) 
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration((new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(jwtConfiguration.getExpirationTime()))))
				.signWith(getsignkey(),SignatureAlgorithm.HS256)
				.compact();
	}
	Key getsignkey() {
		byte[] keys = Decoders.BASE64.decode(jwtConfiguration.getSecretKey());
		return Keys.hmacShaKeyFor(keys);
	}
}