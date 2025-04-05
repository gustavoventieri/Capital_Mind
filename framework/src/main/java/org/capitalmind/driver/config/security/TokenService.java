package org.capitalmind.driver.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.capitalmind.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {

    @Value("${spring.security.jwt.password}")
    private String jwtPassword;
    
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtPassword);

            String token = JWT.create()
            .withIssuer("Capital Mind")
            .withSubject(user.getUserId().toString())
            .withExpiresAt(this.generateExpirationDate())
            .sign(algorithm);

            return token;
        } catch (JWTCreationException exc) {
            throw new RuntimeException("Error while autheticating");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtPassword);

            return JWT.require(algorithm)
            .withIssuer("Capital Mind")
            .build()
            .verify(token)
            .getSubject();

            
        } catch (JWTVerificationException exc) {
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00"));
    }

}
