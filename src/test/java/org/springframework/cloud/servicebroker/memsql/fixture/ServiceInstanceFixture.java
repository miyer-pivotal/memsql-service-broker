package org.springframework.cloud.servicebroker.memsql.fixture;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstance;

public class ServiceInstanceFixture {
	public static ServiceInstance getServiceInstance() {
		return new ServiceInstance("service-instance-id", "service-definition-id", "plan-id",
				"org-guid", "space-guid", "http://dashboard.example.com");
	}
}
