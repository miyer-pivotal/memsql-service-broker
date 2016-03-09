package org.springframework.cloud.servicebroker.mongodb.repository;

import org.springframework.cloud.servicebroker.mongodb.model.ServiceInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for ServiceInstance objects
 *
 */
public interface MemSQLServiceInstanceRepository extends MongoRepository<ServiceInstance, String> {

}