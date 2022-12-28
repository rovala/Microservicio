package com.usuario.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//activacion de la comunicacion entre servicios usando RESTTEMPLATE
@Configuration
public class RestTemplateConfig
{
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}
