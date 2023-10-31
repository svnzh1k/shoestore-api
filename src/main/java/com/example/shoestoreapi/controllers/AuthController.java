package com.example.shoestoreapi.controllers;

import com.example.shoestoreapi.configuration.CustomAuthenticationProvider;
import com.example.shoestoreapi.dto.UserDTO;
import com.example.shoestoreapi.jwt.JwtService;
import com.example.shoestoreapi.models.Role;
import com.example.shoestoreapi.models.User;
import com.example.shoestoreapi.services.UserService;
import com.example.shoestoreapi.util.IncorrectJSONException;
import com.example.shoestoreapi.util.UserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @PostMapping( "/signup")
    public HttpStatus register(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws UserAlreadyExistsException, IncorrectJSONException {
        if (bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder str = new StringBuilder();
            for (FieldError error : errors){
                str.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(";\n");
            }
            throw new IncorrectJSONException(str.toString());
        }
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

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> login(@RequestBody @Valid UserDTO userDTO) throws BadCredentialsException {
        Optional<User> user = userService.getUser(userDTO.getUsername());
        if (user.isEmpty()){
            throw new UsernameNotFoundException("there is no user with that email");
        }
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        System.out.println("token has been generated");
        HashMap <String, String> token = new HashMap<>();
        token.put("token", jwtService.generateToken(userDTO.getUsername()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
    }
}
