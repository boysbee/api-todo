package com.exam.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.exam.todo")
@EnableJpaRepositories("com.exam.todo.dao.jpa")
@EnableSwagger2
public class Application {
    private static final Class<Application> applicationClass = Application.class;
    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("greetings")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/todo/api/tasks.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TODO REST API")
                .description("TODO REST APIs")
                .contact("gogorenger@gmail.com")
                .license("MIT")
                .licenseUrl("http://opensource.org/licenses/MIT")
                .build();
    }
}
