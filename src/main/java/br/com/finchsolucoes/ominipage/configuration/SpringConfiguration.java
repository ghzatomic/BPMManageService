package br.com.finchsolucoes.ominipage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfiguration {

	/*@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
	    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

	    factory.addConnectorCustomizers(connector -> 
	            ((AbstractProtocol) connector.getProtocolHandler()).setConnectionTimeout(9999999));

	    // configure some more properties

	    return factory;
	}*/
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(99999999);
        factory.setConnectTimeout(99999999);
        return factory;
    }
}
