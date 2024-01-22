package co.com.cetus.learning.crudjpa.controller;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.cetus.learning.crudjpa.utils.ConstantsJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthController {

    private AuthenticationManager auth;

    AuthController(AuthenticationManager auth) {
        this.auth = auth;
    }

    @PostMapping(value = "login", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login(@RequestParam("user") String user, @RequestParam("password") String password) {
        try {

            Authentication authentication = auth.authenticate(new UsernamePasswordAuthenticationToken(user, password));
            return new ResponseEntity<>(this.getToken(authentication), HttpStatus.OK);
        } catch (Exception e) {
            log.error("algo sucedio", e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String getToken(Authentication auth) {

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(auth.getName())
                .signWith(Keys.hmacShaKeyFor(ConstantsJWT.CLAVE.getBytes()))
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + ConstantsJWT.TIEMPO_VIDA))
                .compact();

    }

}
