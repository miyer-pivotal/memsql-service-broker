package org.springframework.cloud.servicebroker.memsql.repository;

import org.springframework.cloud.servicebroker.memsql.model.ServiceInstance;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for ServiceInstance objects
 *
 */
public interface MemSQLServiceInstanceRepository extends CrudRepository<ServiceInstance, String> {

}