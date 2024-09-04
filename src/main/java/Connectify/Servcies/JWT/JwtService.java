package Connectify.Servcies.JWT;


import Connectify.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;


    private SecretKey secretKey() {

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(User user) {

        String token = Jwts.builder()
                .signWith(secretKey())
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .compact();

        return token;

    }


    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public Long getUserIdFormToken(String token) {

        return Long.valueOf(getClaims(token).getSubject());

    }

    public String getUserNameFormToken(String token) {

        return getClaims(token).get("email", String.class);
    }

}
