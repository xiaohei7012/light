package com.light.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsConfig {
	@Value("${server.port}")
	Integer httpsport;
	
	
}
