package org.cf.cloud.servicebroker.memsql.service;

import org.cf.cloud.servicebroker.memsql.exception.MemSQLServiceException;
import org.cf.cloud.servicebroker.memsql.model.ServiceInstance;
import org.cf.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

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
	
	/*
	@Autowired
	public MemSQLServiceInstanceService(MemSQLAdminService memsql, MemSQLServiceInstanceRepository repository) {
		this.memsql = memsql;
		this.repository = repository;
	}
	*/
	
	@Override
	public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
		ServiceInstance instance = serviceInstanceRepository.findOne(request.getServiceInstanceId());
		if (instance != null) {
			throw new ServiceInstanceExistsException(request.getServiceInstanceId(), request.getServiceDefinitionId());
		}

		instance = new ServiceInstance(request);

		if (adminService.databaseExists(instance.getServiceInstanceId())) {
			// ensure the instance is empty
			adminService.deleteDatabase(instance.getServiceInstanceId());
		}

		return null;
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