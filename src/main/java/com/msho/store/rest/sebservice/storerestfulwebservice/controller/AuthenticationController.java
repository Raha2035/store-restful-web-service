package com.msho.store.rest.sebservice.storerestfulwebservice.controller;

import com.msho.store.rest.sebservice.storerestfulwebservice.model.AuthenticationResponse;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.User;
import com.msho.store.rest.sebservice.storerestfulwebservice.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
* REST controller for managing user authentication.
* */
@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    /*
    * Constructor for AuthenticationController.
    *
    * @param authService the authentication service
    * */
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    /*
    * Register a new user.
    *
    * @param request the user registration request
    * @return the authentication response
    * */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    /*
    * Authenticate a user.
    *
    * @param request the user login request
    * @return the authentication response
    * */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    /*
    * Refresh the authentication token.
    *
    * @param request the HTTP servlet request
    * @param response the HTTP servlet response
    * @return the
    * */
    @PostMapping("/refresh_token")
    public ResponseEntity<Object> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }
}
