package com.dsosedov.springldap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.ldap.embedded.port=8390"})
class SpringLdapApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void applicationContextLoads() {
        SpringLdapApplication.main(new String[]{});
    }

}
