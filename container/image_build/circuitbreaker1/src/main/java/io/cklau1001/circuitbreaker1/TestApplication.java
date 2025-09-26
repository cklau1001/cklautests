package io.cklau1001.circuitbreaker1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class TestApplication {

	public static void main(String[] args) throws Exception {

		ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);

	}

}
