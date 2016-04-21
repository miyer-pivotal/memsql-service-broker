package org.cf.cloud.servicebroker.memsql.repository;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TestBean extends CrudRepository<ServiceInstance, String> {

}
