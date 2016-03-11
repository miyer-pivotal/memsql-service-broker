package org.springframework.cloud.servicebroker.memsql.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceRepository;
import org.springframework.cloud.servicebroker.memsql.exception.MemSQLServiceException;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;

public class MemSQLAdminServiceUnitTest {

	private static final String DB_NAME = "testDB1";
	private static final String MEMSQL_USER_NAME = "mallika";
	public static final String MEMSQL_PASSWORD = "testPassword";

	@Autowired
	private MemSQLClient client = new MemSQLClient("jdbc:mysql://52.87.166.40:3306", "root", "relevant-grizzled-fireboat");

	@Mock
	private MemSQLAdminService memsql = new MemSQLAdminService(client);

	@Mock
	private MemSQLServiceInstanceRepository repository;

	@Mock
	private ServiceDefinition serviceDefinition;

	private MemSQLServiceInstanceService service;

	@After
	public void cleanup() {
		try {
			memsql.deleteDatabase(DB_NAME);
		} catch (MemSQLServiceException ignore) {}
	}

	@Test
	public void createDatabaseSuccessfully() {
		memsql.createDatabase(DB_NAME);
		Assert.assertTrue(memsql.databaseExists(DB_NAME));
	}

	@Test
	public void deleteDatabaseSuccessfully() {
		memsql.createDatabase(DB_NAME);
		Assert.assertTrue(memsql.databaseExists(DB_NAME));
		memsql.deleteDatabase(DB_NAME);
		Assert.assertFalse(memsql.databaseExists(DB_NAME));
	}

	@Test
	public void databaseDoesNotExist() {
		Assert.assertFalse(memsql.databaseExists("Non-existent-database"));
	}


	@Test
	public void createUserSuccessfully() {

		try{
			memsql.createUser(DB_NAME,MEMSQL_USER_NAME,MEMSQL_PASSWORD);
			Assert.assertTrue(memsql.userExists(MEMSQL_USER_NAME));

		} catch (SQLException ignore) {}
	}

	@Test
	public void deleteUserSuccessfully(){


		try{
			memsql.deleteUser(DB_NAME,MEMSQL_USER_NAME);
			Assert.assertFalse(memsql.userExists(MEMSQL_USER_NAME));

		} catch (MemSQLServiceException ignore){}

	}
}
