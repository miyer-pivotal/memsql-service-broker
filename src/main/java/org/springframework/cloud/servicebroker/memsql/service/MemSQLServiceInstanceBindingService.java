package org.springframework.cloud.servicebroker.memsql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.memsql.lib.PasswordGenerator;
import org.springframework.cloud.servicebroker.memsql.model.ServiceInstanceBinding;
import org.springframework.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceBindingRepository;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
* MemSQL impl to bind services.  Binding a service does the following:
 * creates a new user in the database (currently uses a default pwd of "password"),
 * saves the ServiceInstanceBinding info to the Mongo repository - TBD save to Redis or MySQL instead
 */
@Service
public class MemSQLServiceInstanceBindingService implements ServiceInstanceBindingService {

	private MemSQLAdminService memsql;

	private MemSQLServiceInstanceBindingRepository bindingRepository;

	@Autowired
	public MemSQLServiceInstanceBindingService(MemSQLAdminService memsql,
											   MemSQLServiceInstanceBindingRepository bindingRepository) {
		this.memsql = memsql;
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
		//String password = "password";

		/*
			random password generator
		 */
		PasswordGenerator msr = new PasswordGenerator();
		String password = msr.generateRandomString();

		//PasswordGenerator passgen = new PasswordGenerator();
		//String password = passgen.generatePassword();

		
		// check if user already exists in the DB

		boolean userExists = memsql.userExists(username);
		if(userExists){
			System.out.println("User already exists. A duplicate user cannot be created");
		}
		else try {
			memsql.createUser(database, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return null;
	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
		String bindingId = request.getBindingId();
		ServiceInstanceBinding binding = getServiceInstanceBinding(bindingId);

		if (binding == null) {
			throw new ServiceInstanceBindingDoesNotExistException(bindingId);
		}

		/*mongo.deleteUser(binding.getServiceInstanceId(), bindingId);
		bindingRepository.delete(bindingId);*/
	}

	protected ServiceInstanceBinding getServiceInstanceBinding(String id) {
		return bindingRepository.findOne(id);
	}

}
