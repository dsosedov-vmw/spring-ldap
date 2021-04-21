package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.components.JwtUtil;
import com.dsosedov.springldap.models.AuthenticationRequest;
import com.dsosedov.springldap.models.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/authenticate")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Operation(description = "Authenticates users with a username and a password and returns a JWT token")
    @PostMapping
    public AuthenticationResponse post(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        String jwt = JwtUtil.encode(request.getUsername());
        return new AuthenticationResponse(jwt);
    }

}
