package com.dsosedov.springldap.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private final String jwt;

    @JsonCreator
    public AuthenticationResponse(@JsonProperty("jwt") String jwt) {
        this.jwt = jwt;
    }

}
