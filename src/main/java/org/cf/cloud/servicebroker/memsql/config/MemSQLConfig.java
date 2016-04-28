package org.cf.cloud.servicebroker.memsql.config;

import org.cf.cloud.servicebroker.memsql.service.MemSQLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.UnknownHostException;
import java.sql.Connection;


@Configuration
public class MemSQLConfig {

	//@Value("${memsql.user}")
    @Value("${MEMSQL_USERNAME}")
	private String username;
	
	//@Value("${memsql.password}")
    @Value("${MEMSQL_PASSWORD}")
    private String password;
    
    //@Value("${memsql.host}")
    @Value("${MEMSQL_HOST}")
    private String host;
    
    //@Value("${memsql.port}")
    @Value("${MEMSQL_PORT}")
    private int port = 3306;
    
    //@Value("${memsql.database}")
    @Value("${MEMSQL_DATABASE}")
    private String database;
    
	@Bean
	public MemSQLClient memsqlClient() throws UnknownHostException{
		return new MemSQLClient(host, port, database, username, password);
	}
}
