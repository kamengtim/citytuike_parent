package com.citytuike.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Component
public class SwaggerConfig {
    @Bean
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2).select()./*apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).*/build();
    }
}
