package org.cf.cloud.servicebroker.memsql.fixture;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstance;

/**
 * Created by miyer on 4/21/16.
 */


public class ServiceInstanceFixture {
    public static ServiceInstance getServiceInstance() {
        return new ServiceInstance("service-instance-id", "service-definition-id", "plan-id",
                "org-guid", "space-guid", "http://dashboard.example.com");
    }
}
