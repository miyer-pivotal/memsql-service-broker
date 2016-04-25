package org.cf.cloud.servicebroker.memsql.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by mallika on 3/9/16.
 */
public class MemSQLClient {

	private String username;
	
	private String password;
    
    private String host;
    
    private int port = 3306;
    
    private String databaseName;
    
    private String url;
    private Connection connection = null;

    public MemSQLClient(String host, int port, String database, String username, String password) {
    	this.host = host;
    	this.port = port;
    	this.databaseName = database;
    	this.username = username;
    	this.password = password;
        this.url = getConnectionString();
        System.out.println("URL to host server: " + this.url);
    }

    public String getUrl() { return url; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
    
    public String getConnectionString() {
    	System.out.println("Connection details, host: " + this.host + ", port: " + this.port);
        return getConnectionString(host, port, databaseName); 
    }
    
    public static String getConnectionString(String host, int port, String databaseName) {

        return String.format("jdbc:mysql://%s:%d/%s", host, port, databaseName); 
    }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
