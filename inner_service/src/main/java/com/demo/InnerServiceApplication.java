package com.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class InnerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnerServiceApplication.class, args);
	}
	
	 @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
	                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
	                .select()
	                .apis(requestHandler -> {
	                    String packageName = requestHandler.getHandlerMethod().getMethod()
	                            .getDeclaringClass().getPackage().getName();
	                    return packageName.startsWith("com.demo") && packageName.contains("web");
	                })
	                .paths(PathSelectors.any())
	                .build();
	    }
}
