package org.cf.cloud.servicebroker.memsql.service;

import org.cf.cloud.servicebroker.memsql.exception.MemSQLServiceException;
import org.cf.cloud.servicebroker.memsql.model.ServiceInstance;
import org.cf.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;
import org.cf.cloud.servicebroker.memsql.database.Db;

/**
 * MemSQL impl to manage service instances.  Creating a service does the following:
 * creates a new database,
 * saves the ServiceInstance info to the MySQL repository.
 */
@Service
public class MemSQLServiceInstanceService implements ServiceInstanceService {

	//@Autowired
	MemSQLAdminService adminService;
	
	//@Autowired
	MemSQLServiceInstanceRepository serviceInstanceRepository;
	

	@Autowired
	public MemSQLServiceInstanceService(MemSQLAdminService memsql, MemSQLServiceInstanceRepository repository) {
		this.adminService = memsql;
		this.serviceInstanceRepository = repository;
	}

	
	@Override
	public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
		ServiceInstance instance = serviceInstanceRepository.findOne(request.getServiceInstanceId());
		if (instance != null) {
			System.out.println("null instance");
			throw new ServiceInstanceExistsException(request.getServiceInstanceId(), request.getServiceDefinitionId());
		}

		instance = new ServiceInstance(request);

		if (adminService.databaseExists(instance.getServiceInstanceId())) {
			// ensure the instance is empty
			System.out.println("database exists");

			adminService.deleteDatabase(instance.getServiceInstanceId());
		}

		//Db db;
		String databaseName;
		databaseName = adminService.createDatabase(instance.getServiceInstanceId());

		if (databaseName == null) {
			throw new ServiceBrokerException("Failed to create new DB instance: " + instance.getServiceInstanceId());
		}
		serviceInstanceRepository.save(instance);

		return new CreateServiceInstanceResponse();

	}

	@Override
	public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest request) {
		return new GetLastServiceOperationResponse().withOperationState(OperationState.SUCCEEDED);
	}

	public ServiceInstance getServiceInstance(String id) {
		return serviceInstanceRepository.findOne(id);
	}

	@Override
	public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) throws MemSQLServiceException {
		String instanceId = request.getServiceInstanceId();
		ServiceInstance instance = serviceInstanceRepository.findOne(instanceId);
		if (instance == null) {
			throw new ServiceInstanceDoesNotExistException(instanceId);
		}

		adminService.deleteDatabase(instanceId);
		serviceInstanceRepository.delete(instanceId);
		return new DeleteServiceInstanceResponse();
	}

	@Override
	public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
		String instanceId = request.getServiceInstanceId();
		ServiceInstance instance = serviceInstanceRepository.findOne(instanceId);
		if (instance == null) {
			throw new ServiceInstanceDoesNotExistException(instanceId);
		}

		serviceInstanceRepository.delete(instanceId);
		ServiceInstance updatedInstance = new ServiceInstance(request);
		serviceInstanceRepository.save(updatedInstance);
		return new UpdateServiceInstanceResponse();
	}

}