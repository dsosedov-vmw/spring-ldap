package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.models.AuthenticationResponse;
import com.dsosedov.springldap.services.BarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.ldap.embedded.port=8393"})
@AutoConfigureMockMvc
public class BarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarService barService;

    @Test
    void getBarsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/bar/0"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getBarsUnauthorizedAsJohn() throws Exception {
        when(barService.bar(0)).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponse response = mapper.readValue(mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johnd\",\"password\":\"johnd1\"}"))
                .andReturn()
                .getResponse().getContentAsString(), AuthenticationResponse.class);
        mockMvc.perform(
                get("/api/v1/bar/0")
                        .header("Authorization", "Bearer " + response.getJwt()))
                .andExpect(status().isForbidden());
    }

    @Test
    void getEvenSuccessfullyAsJane() throws Exception {
        when(barService.bar(0)).thenReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponse response = mapper.readValue(mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"janed\",\"password\":\"janed1\"}"))
                .andReturn()
                .getResponse().getContentAsString(), AuthenticationResponse.class);
        mockMvc.perform(
                get("/api/v1/bar/0")
                        .header("Authorization", "Bearer " + response.getJwt()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"even\":true}"));
    }

    @Test
    void getOddSuccessfullyAsJane() throws Exception {
        when(barService.bar(0)).thenReturn(false);
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponse response = mapper.readValue(mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"janed\",\"password\":\"janed1\"}"))
                .andReturn()
                .getResponse().getContentAsString(), AuthenticationResponse.class);
        mockMvc.perform(
                get("/api/v1/bar/0")
                        .header("Authorization", "Bearer " + response.getJwt()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"even\":false}"));
    }

}
