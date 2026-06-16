package core.westpac.prime.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for OpenAPI/Swagger
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Prime Summation API")
                        .description("REST API for calculating the sum of prime numbers")
                        .version("1.0.0")
                );
    }
}