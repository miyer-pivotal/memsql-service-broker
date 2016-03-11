package org.springframework.cloud.servicebroker.memsql.repository;

import org.springframework.cloud.servicebroker.memsql.model.ServiceInstanceBinding;
//import org.springframework.data.memsql.repository.MongoRepository;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository for ServiceInstanceBinding objects
 *
 *
 */
public interface MemSQLServiceInstanceBindingRepository extends CrudRepository<ServiceInstanceBinding, String> {

}
