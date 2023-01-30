package com.cloudshop.profilebs;

import com.cloudshop.exceptionhandler.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.cloudshop"})
@Import(GlobalExceptionHandler.class)
public class ProfileBsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileBsApplication.class, args);
	}

}
