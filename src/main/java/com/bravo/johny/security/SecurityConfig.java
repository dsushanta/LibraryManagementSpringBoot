package com.bravo.johny.security;

import com.bravo.johny.filter.CustomAuthenticationFilter;
import com.bravo.johny.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        var customAuthenticationFilter =  new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/auth");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/auth/**", "/refreshToken/**").permitAll();
        http.authorizeRequests().antMatchers("/users/**").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(POST,"/books/").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(PUT,"/books/{bookId}/").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(DELETE,"/books/{bookId}/").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(GET,"/books/{bookId}/issuedUsers/").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers(GET,"/books/genres/").hasAnyAuthority("User", "Admin");
        http.authorizeRequests().antMatchers(GET,"/books/").hasAnyAuthority("User", "Admin");
        http.authorizeRequests().antMatchers(GET,"/books/{bookId}/").hasAnyAuthority("User");
        http.authorizeRequests().antMatchers("/books/{bookId}/bookissue/**").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers("/books/{bookId}/copies/**").hasAnyAuthority("Admin");
        http.authorizeRequests().antMatchers("/config/**").hasAnyAuthority("Admin");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
