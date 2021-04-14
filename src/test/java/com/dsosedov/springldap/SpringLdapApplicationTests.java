package com.dsosedov.springldap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.ldap.embedded.port=8390",
        "ldap.url=ldap://localhost:8390/dc=dsosedov,dc=com"
})
class SpringLdapApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void applicationContextLoads() {
        SpringLdapApplication.main(new String[]{});
    }

}
