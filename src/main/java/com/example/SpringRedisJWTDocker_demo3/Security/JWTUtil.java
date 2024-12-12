package com.example.SpringRedisJWTDocker_demo3.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_token}")
    private String secret;

    public String generateToken(String username){
        Date currentTime=Date.from(ZonedDateTime.now().plusHours(1).toInstant());

        return JWT.create()
                .withSubject("Client Info")
                .withClaim("username",username)
                .withIssuedAt(new Date())
                .withIssuer("Alexey O.")
                .withExpiresAt(currentTime)
                .sign(Algorithm.HMAC256(secret));
    }
    public String validateToken(String token){
        JWTVerifier verifer=JWT.require(Algorithm.HMAC256(secret))
                .withSubject("Client Info")
                .withIssuer("Alexey O.")
                .build();
        DecodedJWT decoded=verifer.verify(token);
        return decoded.getClaim("username").asString();
    }

}
