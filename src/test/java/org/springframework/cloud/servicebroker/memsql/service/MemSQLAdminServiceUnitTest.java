package org.springframework.cloud.servicebroker.memsql.service;


import org.cf.cloud.servicebroker.memsql.exception.MemSQLServiceException;
import org.cf.cloud.servicebroker.memsql.lib.PasswordGenerator;
import org.cf.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceRepository;
import org.cf.cloud.servicebroker.memsql.service.MemSQLAdminService;
import org.cf.cloud.servicebroker.memsql.service.MemSQLClient;
import org.cf.cloud.servicebroker.memsql.service.MemSQLServiceInstanceService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;

import java.sql.SQLException;


public class MemSQLAdminServiceUnitTest {

	private static final String DB_NAME = "testDatabase123";
	private static final String MEMSQL_USER_NAME = "mallikaiyer123";
	PasswordGenerator pgen = new PasswordGenerator();

	public final String MEMSQL_PASSWORD = pgen.generateRandomString();

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
	public void cleanup() throws SQLException {
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
