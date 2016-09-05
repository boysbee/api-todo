package com.exam.todo.api.rest.docs;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * Created by boysbee on 9/5/2016 AD.
 */
@Configuration
@EnableSwagger
@ComponentScan("com.exam.todo.api.rest")
public class SwaggerConfig {
    public static final String DEFAULT_INCLUDE_PATTERNS = "/todo/api/tasks.*";

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(DEFAULT_INCLUDE_PATTERNS);
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "TODO REST APIs",
                "The APIs here demonstrate creating a 'production-ready' service built with Spring Boot and other helpful libraries",
                "http://opensource.org/licenses/MIT",
                "gogorenger@gmail.com",
                "MIT",
                "http://opensource.org/licenses/MIT"
        );
        return apiInfo;
    }
}
