package com.tibianos.tibianosfanpage.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibianos.tibianosfanpage.SpringAppContext;
import com.tibianos.tibianosfanpage.models.request.UserLoginRequestModel;
import com.tibianos.tibianosfanpage.services.UserServiceInterface;
import com.tibianos.tibianosfanpage.shared.dto.UserDto;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequestModel.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(),
                    userModel.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();

        String token = Jwts.builder().setSubject(username)
                        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();


        UserServiceInterface userService = (UserServiceInterface) SpringAppContext.getBean("userService");
        UserDto useDto = userService.getUser(username);


        response.addHeader("UserId",useDto.getUserId());
        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX + token);

    }

}
