package com.application.festival.yabam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class YabamApplication {

	public static void main(String[] args) {
		SpringApplication.run(YabamApplication.class, args);
	}

}
