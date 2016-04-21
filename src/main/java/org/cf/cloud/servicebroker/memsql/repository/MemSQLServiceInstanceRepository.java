package org.cf.cloud.servicebroker.memsql.repository;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ServiceInstance objects
 *
 */
public interface MemSQLServiceInstanceRepository extends CrudRepository<ServiceInstance, String> {

}