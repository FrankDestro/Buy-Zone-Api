package com.sysout.buy_zone_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BuyZoneApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuyZoneApiApplication.class, args);
	}

}
