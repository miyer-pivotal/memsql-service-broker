package org.cf.cloud.servicebroker.memsql.repository;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstanceBinding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Repository for ServiceInstanceBinding objects
 *
 *
 */
@Component
public interface MemSQLServiceInstanceBindingRepository extends CrudRepository<ServiceInstanceBinding, String> {

}
