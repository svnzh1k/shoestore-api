package com.example.shoestoreapi.controllers;

import com.example.shoestoreapi.configuration.CustomAuthenticationProvider;
import com.example.shoestoreapi.dto.UserDTO;
import com.example.shoestoreapi.jwt.JwtService;
import com.example.shoestoreapi.models.Role;
import com.example.shoestoreapi.models.User;
import com.example.shoestoreapi.services.UserService;
import com.example.shoestoreapi.util.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, CustomAuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping( "/register")
    public HttpStatus register(@RequestBody UserDTO userDTO) throws UserAlreadyExistsException {
        Optional <User> useropt = userService.getUser(userDTO.getUsername());
        if (useropt.isPresent()){
            throw new UserAlreadyExistsException("a user with that email already exists");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.USER);
        userService.save(user);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO){
        Optional<User> user = userService.getUser(userDTO.getUsername());
        if (user.isEmpty()){
            System.out.println("exception");
            throw new UsernameNotFoundException("no such user");
        }
        //System.out.println(user.getUsername() + " hello");
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );
        if (authentication.isAuthenticated()){
            System.out.println("if rabotaet");
            return jwtService.generateToken(userDTO.getUsername());
        }
        return "no such user";
    }
}
