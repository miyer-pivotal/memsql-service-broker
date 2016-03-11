package org.springframework.cloud.servicebroker.memsql.config;

import java.net.UnknownHostException;

import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.cloud.servicebroker.memsql.service.MemSQLClient;


import com.mongodb.MongoClient;

// change this to use the MySQL API - invoke a jdbc call instead of using the Mongo API

@Configuration
@EnableMongoRepositories(basePackages = "org.springframework.cloud.servicebroker.memsql.repository")
public class MemSQLConfig {

	@Value("${security.user.name}")
	private String username;

	@Value("${security.user.password}")
	private String password;

	@Value("${memsql.url}")
	private String url;

	@Bean
	public MemSQLClient memsqlClient() throws UnknownHostException{

		return new MemSQLClient(url, username, password);

	}
/*	public MongoClient mongoClient() throws UnknownHostException {
		return new MongoClient(new ServerAddress(host, port));
	}
*/

}