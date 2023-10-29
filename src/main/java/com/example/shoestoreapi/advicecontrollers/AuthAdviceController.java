package com.example.shoestoreapi.advicecontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthAdviceController {

    @ExceptionHandler
    public HttpStatus handleUserNotFound(UsernameNotFoundException e){
        return HttpStatus.NOT_FOUND;
    }


}
