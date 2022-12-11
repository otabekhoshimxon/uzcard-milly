package uz.uzcard.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {




    private static final String SECRET_KEY="keyword";



    public static String encodeId(String id){


        JwtBuilder jwtBuilder= Jwts.builder();
        jwtBuilder.claim("id",id);
        jwtBuilder.setIssuedAt(Date.from(Instant.now()));//start time
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(60*60*1000)*12));//end time
        jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
        return jwtBuilder.compact();
    }


    public static String decodeId(String token){
        Claims claims=Jwts.parser().
                setSigningKey(SECRET_KEY).
                parseClaimsJws(token).
                getBody();
        return (String) claims.get("id");
    }
}
