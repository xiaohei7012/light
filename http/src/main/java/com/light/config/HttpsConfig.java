package com.light.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsConfig {
	@Value("${server.port}")
	Integer httpsport;
	
	@Value("${server.httpport}")
	Integer httpport;
	
//	private Connector createHttpConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		connector.setSecure(true);
//		connector.setPort(httpport);
//		connector.setRedirectPort(httpsport);
//		return connector;
//	}
//	
//	@Bean
//	public EmbeddedServletContainerFactory  servletContainer() {
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//			protected void postProcessContext(Context context) {
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				 SecurityCollection collection = new SecurityCollection();
//				 collection.addPattern("/*");
//				 securityConstraint.addCollection(collection);
//				 context.addConstraint(securityConstraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(createHttpConnector());
//		return tomcat;
//	}	
}
