package com.dsosedov.springldap.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        Components components = new Components();

        SecurityScheme securityScheme = new SecurityScheme();

        securityScheme.setOpenIdConnectUrl("http://localhost:8081/auth/realms/springldap/.well-known/openid-configuration");
        securityScheme.setName("KeyCloak");
        securityScheme.setType(SecurityScheme.Type.OPENIDCONNECT);

        components.addSecuritySchemes("KeyCloak", securityScheme);

        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("Spring LDAP API")
                        .description("A sample LDAP consuming application.")
                        .version("v1"));
    }

}
