package com.dsosedov.springldap;

import com.dsosedov.springldap.models.AuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringLdapApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void applicationContextLoads() {
        SpringLdapApplication.main(new String[0]);
    }

    @Test
    void authenticateFails() throws Exception {
        mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"foo\",\"password\":\"bar\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatePasses() throws Exception {
        mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ben\",\"password\":\"benspassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getFoosUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/foo"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getFoosSuccessfully() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponse response = mapper.readValue(mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ben\",\"password\":\"benspassword\"}"))
                .andReturn()
                .getResponse().getContentAsString(), AuthenticationResponse.class);
        mockMvc.perform(
                get("/api/v1/foo")
                        .header("Authorization", "Bearer "+response.getJwt()))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"foo\",\"bar\",\"baz\"]"));
    }

}
