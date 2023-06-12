package com.bravo.johny.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import static com.bravo.johny.config.ProjectConfig.*;
import static java.util.Arrays.stream;

public class JWTUtils {

    private JWTUtils(){}

    public static String generateJWTAccessToken(HttpServletRequest request, User user){

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public static String generateJWTAccessToken(HttpServletRequest request, com.bravo.johny.dto.User user){

        return JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", Collections.singletonList(user.getRole().getRoleName()))
                .sign(getAlgorithm());
    }

    public static String generateJWTRefreshToken(HttpServletRequest request, User user){

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(getAlgorithm());
    }

    public static void verifyJWTToken(String authorizationHeader){

        var token = authorizationHeader.substring("Bearer ".length());
        var algorithm = getAlgorithm();
        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(token);
        var username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public static Algorithm getAlgorithm(){
        return Algorithm.HMAC256(SECRET.getBytes());
    }
}
