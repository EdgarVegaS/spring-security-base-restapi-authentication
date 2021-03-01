package com.tibianos.tibianosfanpage.security;

import com.tibianos.tibianosfanpage.services.UserServiceInterface;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserServiceInterface userService;
    private final BCryptPasswordEncoder bCrypt;

    public WebSecurity(UserServiceInterface userService, BCryptPasswordEncoder bCrypt) {
        this.userService = userService;
        this.bCrypt = bCrypt;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll()
        .antMatchers(HttpMethod.GET,"/posts/last").permitAll()
        .antMatchers(HttpMethod.GET,"/char/{id}").permitAll()
        .antMatchers(HttpMethod.GET,"/guilds/{world}").permitAll()
        .antMatchers(HttpMethod.GET,"/guild/{name}").permitAll()
        .antMatchers(HttpMethod.GET,"/hs/{mundo}/{categoria}/{voc}").permitAll()
        .antMatchers(HttpMethod.GET,"/news").permitAll()
        .antMatchers(HttpMethod.GET,"/worlds").permitAll()
        .antMatchers(HttpMethod.GET,"/posts/{id}").permitAll().anyRequest()
        .authenticated().and().addFilter(getAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(bCrypt);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());

        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

}
