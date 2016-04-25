package org.cf.cloud.servicebroker.memsql.service;

public class DatabaseCredentials {

	private String databaseName;
	private String username;
	private String password;
	private String host;
	private int port;
	
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getUri() {
		return getConnectionString(this.host, this.port, this.databaseName, this.username, this.password);
		//return getConnectionString(this.host, this.port, this.databaseName);
	}
	
	public static String getConnectionString(String host, int port, String databaseName, String user, String password) {
        return String.format("mysql://%s:%s@%s:%d/%s?reconnect=true", user, password, host, port, databaseName); 
    }
	
	public static String getConnectionString(String host, int port, String databaseName) {
        return String.format("mysql://%s:%d/%s?reconnect=true", host, port, databaseName); 
    }
	
	public String getJdbcUrl() {
		return getJdbcConnectionString(this.host, this.port, this.databaseName, this.username, this.password);
		//return getJdbcConnectionString(this.host, this.port, this.databaseName);
	}
	
	public static String getJdbcConnectionString(String host, int port, String databaseName, String user, String password) {
        return String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", host, port, databaseName, user, password); 
    }
	
	public static String getJdbcConnectionString(String host, int port, String databaseName) {
        return String.format("jdbc:mysql://%s:%d/%s", host, port, databaseName); 
    }
	
	
	
	
}
