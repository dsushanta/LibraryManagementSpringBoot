package com.bravo.johny.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bravo.johny.dto.LoginResponse;
import com.bravo.johny.dto.User;
import com.bravo.johny.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.bravo.johny.utils.CommonUtils.returnErrorInResponse;
import static com.bravo.johny.utils.JWTUtils.generateJWTAccessToken;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/refreshToken")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try {
                var refresh_token = authorizationHeader.substring("Bearer ".length());
                var algorithm = Algorithm.HMAC256("secret".getBytes());
                var verifier = JWT.require(algorithm).build();
                var decodedJWT = verifier.verify(refresh_token);
                var username = decodedJWT.getSubject();
                var user = userService.getUserDetails(username);

                String accessToken = generateJWTAccessToken(request, user);

                var tokens = new HashMap<String, String>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception){
                returnErrorInResponse(response, exception);
            }

        } else {
            throw new RuntimeException("Refresh Token in missing !!");
        }
    }

}
