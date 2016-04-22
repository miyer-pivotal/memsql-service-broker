package org.cf.cloud.servicebroker.memsql.service;
import java.sql.*;

/**
 * Created by mallika on 3/9/16.
 */
public class MemSQLClient {

    private String url;
    private String username;
    private String password;

    private Connection connection = null;
    private String connectionString;

    public MemSQLClient(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
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
    public String getConnectionString() throws SQLException {

        //connection = getConnection();
        connectionString = "jdbc:mysql://52.87.206.146:3306/test?user=root&password=pivotal";
        return connectionString;

    }
}
