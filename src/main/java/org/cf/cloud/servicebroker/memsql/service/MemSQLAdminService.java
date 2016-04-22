package org.cf.cloud.servicebroker.memsql.service;

import org.cf.cloud.servicebroker.memsql.exception.MemSQLServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import org.cf.cloud.servicebroker.memsql.database.Db;

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
	
	public String createDatabase(String databaseName) throws MemSQLServiceException {
		try {
			Connection connection = client.getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE DATABASE " + databaseName);
			return databaseName;
		} catch (SQLException e) {
			try {
				deleteDatabase(databaseName);
				return null;
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

	private MemSQLServiceException handleException(Exception e) {
		logger.warn(e.getLocalizedMessage(), e);
		return new MemSQLServiceException(e.getLocalizedMessage());
	}

}
