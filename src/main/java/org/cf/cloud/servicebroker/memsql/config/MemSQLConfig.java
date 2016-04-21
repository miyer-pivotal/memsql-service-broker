package org.cf.cloud.servicebroker.memsql.config;

import org.cf.cloud.servicebroker.memsql.service.MemSQLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.UnknownHostException;


@Configuration


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


}
