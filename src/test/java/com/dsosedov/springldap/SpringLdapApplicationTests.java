package com.dsosedov.springldap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void getFoosSuccessfully() throws Exception {
        mockMvc.perform(get("/api/v1/foo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"foo\",\"bar\",\"baz\"]"));
    }

}
