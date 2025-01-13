package com.ohgiraffers.springdatajpa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ohgiraffers.springdatajpa")
@EntityScan(basePackages = "com.ohgiraffers.springdatajpa")
public class Chap06SpringDataJpaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap06SpringDataJpaCrudApplication.class, args);
	}

}
