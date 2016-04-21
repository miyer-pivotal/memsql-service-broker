package org.springframework.cloud.servicebroker.memsql.fixture;

import java.util.Collections;
import java.util.Map;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstanceBinding;

public class ServiceInstanceBindingFixture {
	public static ServiceInstanceBinding getServiceInstanceBinding() {
		Map<String, String> credentials = Collections.singletonMap("url", "mongo://example.com");
		return new ServiceInstanceBinding("binding-id", "service-instance-id", credentials, null, "app-guid");
	}
}
