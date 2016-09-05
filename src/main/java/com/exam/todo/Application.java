package com.exam.todo;

import com.mangofactory.swagger.plugin.EnableSwagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by boysbee on 9/5/2016 AD.
 */
@EnableAutoConfiguration  // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "com.exam.todo")
@EnableJpaRepositories("com.exam.todo.dao.jpa") // To segregate MongoDB and JPA repositories. Otherwise not needed.
@EnableSwagger // auto generation of API docs
public class Application {
    private static final Class<Application> applicationClass = Application.class;
    private static final Logger log = LoggerFactory.getLogger(applicationClass);

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
