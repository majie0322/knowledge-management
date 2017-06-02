package com.management.conf;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ResourceBundle;

@org.springframework.context.annotation.Configuration
@EnableNeo4jRepositories(basePackages = "com.huizhi.bd.knowledge.neo4j.repository")
@EnableTransactionManagement
public class MyConfiguration extends Neo4jConfiguration {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("neo4j");
	
	@Bean
	public Configuration getConfiguration() {
	   Configuration config = new Configuration();
	   config
	       .driverConfiguration()
	       .setDriverClassName(rb.getString("Driver"))
	       .setURI(rb.getString("Url"));
	   return config;
	}

    @Bean
    public SessionFactory getSessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory(getConfiguration(), "com.huizhi.bd.knowledge.neo4j.domain");
    }


}
