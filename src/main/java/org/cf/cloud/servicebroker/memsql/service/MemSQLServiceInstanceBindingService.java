package org.cf.cloud.servicebroker.memsql.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import org.cf.cloud.servicebroker.memsql.lib.PasswordGenerator;
import org.cf.cloud.servicebroker.memsql.model.ServiceInstanceBinding;
import org.cf.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceBindingRepository;
import org.cf.cloud.servicebroker.memsql.repository.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

/**
* MemSQL impl to bind services.  Binding a service does the following:
 * creates a new user in the database - autogenerates the password,
 * saves the ServiceInstanceBinding info to the MemSQL repository
 */
@Service
public class MemSQLServiceInstanceBindingService implements ServiceInstanceBindingService {

	@Autowired
	private MemSQLClient memSQLClient = new MemSQLClient("jdbc:mysql://52.87.206.146:3306", "root", "pivotal");

	@Autowired
	MemSQLAdminService adminService;

	@Autowired
	MemSQLServiceInstanceBindingRepository bindingRepository;

		
//was commented out
	@Autowired
	public MemSQLServiceInstanceBindingService(MemSQLAdminService memsql,
											   MemSQLServiceInstanceBindingRepository bindingRepository) {
		this.adminService = memsql;
		this.bindingRepository = bindingRepository;
	}

	
	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {

		String bindingId = request.getBindingId();
		String serviceInstanceId = request.getServiceInstanceId();

		ServiceInstanceBinding binding = bindingRepository.findOne(bindingId);
		if (binding != null) {
			throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
		}

		String database = serviceInstanceId;
		String username = bindingId;

		/*
			random password generator
		 */
		PasswordGenerator msr = new PasswordGenerator();
		String password = msr.generateRandomString();


		// check if user already exists in the DB

		boolean userExists = adminService.userExists(username);
		if(userExists){
			System.out.println("User already exists. A duplicate user cannot be created");
		}
		else try {
			adminService.createUser(database, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Map<String, Object> credentials =
				null;
		try {
			credentials = Collections.singletonMap("uri", memSQLClient.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}


		binding = new ServiceInstanceBinding(bindingId, serviceInstanceId, credentials, null, request.getBoundAppGuid());
		bindingRepository.save(binding);

		return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);

	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
		String bindingId = request.getBindingId();
		ServiceInstanceBinding binding = getServiceInstanceBinding(bindingId);

		if (binding == null) {
			throw new ServiceInstanceBindingDoesNotExistException(bindingId);
		}

		adminService.deleteUser(binding.getServiceInstanceId(), bindingId);
		bindingRepository.delete(bindingId);

	}

	public ServiceInstanceBinding getServiceInstanceBinding(String id) {
		return bindingRepository.findOne(id);
	}

}
