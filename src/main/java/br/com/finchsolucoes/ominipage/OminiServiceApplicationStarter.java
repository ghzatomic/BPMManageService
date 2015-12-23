package br.com.finchsolucoes.ominipage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@EnableEurekaClient
@ComponentScan("br.com.finchsolucoes.ominipage")
@EnableFeignClients
@EnableCircuitBreaker
public class OminiServiceApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(OminiServiceApplicationStarter.class, args);
	}
	
}
