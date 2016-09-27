package br.com.finchsolucoes.bpm.converter.destination;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DestinationFactory {

	@Bean
	public DestinationJsonConverter createDestinationJsonConverter(){
		return new DestinationJsonConverter();
	}
	
}
