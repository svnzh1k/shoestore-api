package com.example.shoestoreapi.configuration;

import com.example.shoestoreapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws BadCredentialsException{
        UserDetails user = userService.loadUserByUsername(authentication.getName());
        if (user.getUsername() == null){
            throw new BadCredentialsException("Bad cred");
        }
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (user.getUsername().equals(username) && passwordEncoder.matches(password , user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        }else{
            throw new BadCredentialsException("Incorrect email or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
