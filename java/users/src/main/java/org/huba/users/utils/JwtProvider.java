package org.huba.users.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.huba.users.config.JwtAuthentication;
import org.huba.users.model.Privilege;
import org.huba.users.model.User;
import org.huba.users.utils.MyConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtProvider {
    private final SecretKey jwtAccessSecret;

    public JwtProvider(@Value("${jwt.secret.access}") String secret) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }


    public Claims getAccessClaims(String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public UUID getId() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        return UUID.fromString(authentication.getFirstName());
    }


    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public String generateAccessToken(User user) {
        final Date accessExpiration = new Date(System.currentTimeMillis() + (MyConstants.ACCESS_TOKEN_EXP));
        return "Bearer " + Jwts.builder()
                .setSubject(null)
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("id", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("secondName", user.getSecondName())
                .claim("thirdName", user.getThirdName())
                .claim("privileges", user.getPrivileges().stream().map(Privilege::toClaim).collect(Collectors.toSet()))
                .compact();
    }
}
