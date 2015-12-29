package br.com.finchsolucoes.ominipage.configuration;

import org.apache.coyote.AbstractProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

	@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

	    factory.addConnectorCustomizers(connector -> 
	            ((AbstractProtocol) connector.getProtocolHandler()).setConnectionTimeout(9999999));

	    // configure some more properties

	    return factory;
	}
	
}
