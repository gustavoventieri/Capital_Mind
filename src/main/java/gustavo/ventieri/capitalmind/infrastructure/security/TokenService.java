package gustavo.ventieri.capitalmind.infrastructure.security;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import gustavo.ventieri.capitalmind.domain.user.User;

@Service
public class TokenService {
    

    // Variavek que obtem a senha do JWT
    @Value("${api.security.token.jwt.secret}")
    private String JWT_SECRET;


    // Metódo publico que gera o JWT
    public String genereateToken(User user){
        try {

            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            
            String token = JWT
                        .create()
                        .withIssuer("Capital_Mind")
                        .withSubject(user.getUserId().toString())
                        .withExpiresAt(this.generateExpirationDate())
                        .sign(algorithm);

            return token;
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error while autheticating");
        }
    }

    // Metódo publico que valida o JWT
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);

            return JWT
            .require(algorithm)
            .withIssuer("Capital_Mind")
            .build()
            .verify(token)
            .getSubject();
        } catch (JWTVerificationException error) {
            return null;
        }
    }


    // Metódo privado que gera o ecpiration time do JWT
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}
