


package org.springframework.cloud.servicebroker.memsql.service;



import com.mongodb.ServerAddress;
import org.springframework.cloud.servicebroker.memsql.exception.MemSQLServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.*;

/**
 * Utility class for manipulating a MemSQL database.
 *
 *
 */



@Service
public class MemSQLAdminService {

	public static final String ADMIN_DB = "admin";
	
	private Logger logger = LoggerFactory.getLogger(MemSQLAdminService.class);
	
	private MemSQLClient client;
	
	@Autowired
	public MemSQLAdminService(MemSQLClient client) {
		this.client = client;
	}
	
	public boolean databaseExists(String databaseName) throws MemSQLServiceException {
		try {
			Connection connection = client.getConnection();
			ResultSet res = connection.getMetaData().getCatalogs();

			while (res.next()){

				String dbName = res.getString(1);
				if(dbName.equals(databaseName)){
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			throw handleException(e);
		}
	}


	public boolean userExists(String userName) throws MemSQLServiceException{

		try {
			Connection connection = client.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("SELECT DISTINCT GRANTEE FROM information_schema.USER_PRIVILEGES WHERE GRANTEE LIKE '\\'"+userName+"\\'%'");
			while(res.next()){

				String uNameFull = res.getString(1);
				String[] uNameList = uNameFull.split("@");

				String uName = uNameList[0];

				userName = "'"+userName+"'";
				if (uName.equals(userName)){
					return true;
				}

			}
			return false;
		} catch (SQLException e) {
			throw handleException(e);
		}
	}
	
	public void deleteDatabase(String databaseName) throws MemSQLServiceException {
		try {
			Connection connection = client.getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("DROP DATABASE " + databaseName);
		} catch (SQLException e) {
			throw handleException(e);
		}
	}
	
	public void createDatabase(String databaseName) throws MemSQLServiceException {
		try {
			Connection connection = client.getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE DATABASE " + databaseName);
		} catch (SQLException e) {
			try {
				deleteDatabase(databaseName);
			} catch (MemSQLServiceException ignore) {}
			throw handleException(e);
		}
	}


	public void createUser(String database, String username, String password) throws MemSQLServiceException, SQLException {
		try {
			Connection connection = client.getConnection();
			Statement stmt = connection.createStatement();
			//stmt.executeUpdate("CREATE USER IF NOT EXISTS '"+username+"'@'%' IDENTIFIED BY '"+password+"'");
			stmt.executeUpdate("GRANT all ON *.* TO '"+username+"'@'%' IDENTIFIED BY '"+password+"'");


		}catch (SQLException e) {
		try {
			deleteUser(database,username);
		} catch (MemSQLServiceException ignore) {}
		throw handleException(e);
	}
}


	public void deleteUser(String database, String username) throws MemSQLServiceException {
		try {
			Connection connection = client.getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("DROP USER "+username);

			//System.out.println("***************after "+username+"**************");

		}catch (SQLException e) {
			throw handleException(e);
		}

	}
/*
	public String getConnectionString(String database, String username, String password) {
		return new StringBuilder()
				.append("memsql://")
				.append(username)
				.append(":")
				.append(password)
				.append("@")
				.append(getServerAddresses())
				.append("/")
				.append(database)
				.toString();
	}
	
	public String getServerAddresses() {
		StringBuilder builder = new StringBuilder();
		for (ServerAddress address : client.getAllAddress()) {
			builder.append(address.getHost())
					.append(":")
					.append(address.getPort())
					.append(",");
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length()-1);
		}
		return builder.toString();
	}
*/
	private MemSQLServiceException handleException(Exception e) {
		logger.warn(e.getLocalizedMessage(), e);
		return new MemSQLServiceException(e.getLocalizedMessage());
	}

}
