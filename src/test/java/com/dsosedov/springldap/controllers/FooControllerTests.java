package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.models.AuthenticationResponse;
import com.dsosedov.springldap.services.FooService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest(properties = {"spring.ldap.embedded.port=8392"})
@AutoConfigureMockMvc
public class FooControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FooService fooService;

    @BeforeEach
    void setUp() {
        when(fooService.foo()).thenReturn(new String[]{"a", "b", "c"});
    }

    @Test
    void getFoosUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/foo"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getFoosSuccessfullyAsJohn() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponse response = mapper.readValue(mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johnd\",\"password\":\"johnd1\"}"))
                .andReturn()
                .getResponse().getContentAsString(), AuthenticationResponse.class);
        mockMvc.perform(
                get("/api/v1/foo")
                        .header("Authorization", "Bearer " + response.getJwt()))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"a\",\"b\",\"c\"]"));
    }

    @Test
    void getFoosSuccessfullyAsJane() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponse response = mapper.readValue(mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"janed\",\"password\":\"janed1\"}"))
                .andReturn()
                .getResponse().getContentAsString(), AuthenticationResponse.class);
        mockMvc.perform(
                get("/api/v1/foo")
                        .header("Authorization", "Bearer " + response.getJwt()))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"a\",\"b\",\"c\"]"));
    }

}
