package org.springframework.cloud.servicebroker.memsql.fixture;

import java.util.Collections;
import java.util.Map;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstanceBinding;

public class ServiceInstanceBindingFixture {
	public static ServiceInstanceBinding getServiceInstanceBinding() {
		Map<String, Object> credentials = Collections.singletonMap("url", (Object) "jdbc:mysql://52.87.206.146:3306");
		return new ServiceInstanceBinding("binding-id", "service-instance-id", credentials, null, "app-guid");
	}
}
