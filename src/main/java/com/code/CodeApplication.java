package com.code;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author majian
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.code"})
@MapperScan(basePackages={"com.code.dao"})
public class CodeApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(CodeApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CodeApplication.class);
	}
}
