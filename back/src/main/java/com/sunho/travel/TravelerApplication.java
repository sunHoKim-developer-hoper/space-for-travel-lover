package com.sunho.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.sunho.travel.util.DotenvLoader;

@SpringBootApplication
@EnableCaching
public class TravelerApplication {

	public static void main(String[] args) {
		DotenvLoader.loadEnv();
		SpringApplication.run(TravelerApplication.class, args);
	}
}
