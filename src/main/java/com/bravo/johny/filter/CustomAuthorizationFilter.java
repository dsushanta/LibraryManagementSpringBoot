package com.bravo.johny.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bravo.johny.utils.CommonUtils.returnErrorInResponse;
import static com.bravo.johny.utils.JWTUtils.verifyJWTToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/auth") || request.getServletPath().equals("/refreshToken")){
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
                try {
                    verifyJWTToken(authorizationHeader);
                    filterChain.doFilter(request, response);
                } catch (Exception exception){
                    returnErrorInResponse(response, exception);
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
