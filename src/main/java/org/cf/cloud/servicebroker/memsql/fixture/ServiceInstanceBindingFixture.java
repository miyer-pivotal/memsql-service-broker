package org.cf.cloud.servicebroker.memsql.fixture;

import java.util.Collections;
import java.util.Map;

import org.cf.cloud.servicebroker.memsql.model.ServiceInstanceBinding;

/**
 * Created by miyer on 4/21/16.
 */

public class ServiceInstanceBindingFixture {
    public static ServiceInstanceBinding getServiceInstanceBinding() {

        Map<String, Object> credentials = Collections.singletonMap("url", (Object)"jdbc:mysql://52.87.206.146:3306");

        System.out.println("In Service Instance Binding");
        return new ServiceInstanceBinding("binding-id", "service-instance-id", credentials, null, "app-guid");
    }
}

