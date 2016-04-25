package org.springframework.cloud.servicebroker.memsql.service;

import org.cf.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceRepository;
import org.cf.cloud.servicebroker.memsql.service.MemSQLAdminService;
import org.cf.cloud.servicebroker.memsql.service.MemSQLClient;
import org.cf.cloud.servicebroker.memsql.service.MemSQLServiceInstanceService;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by miyer on 4/21/16.
 */
public class MemSQLServiceInstanceServiceUnitTest {

    private static final String SVC_DEF_ID = "serviceDefinitionId";
    private static final String SVC_PLAN_ID = "servicePlanId";

    @Autowired
    private MemSQLClient client = new MemSQLClient("52.87.206.146", 3306, "test", "root", "pivotal");



    private MemSQLAdminService memsql = new MemSQLAdminService(client);


    private MemSQLServiceInstanceRepository repository;

    private ServiceDefinition serviceDefinition;

    private MemSQLServiceInstanceService service;

    protected String databaseName;

    @Test
    public void newServiceInstanceCreatedSuccessfully() throws Exception {

        /*when(repository.findOne(any(String.class))).thenReturn(null);
        when(memsql.databaseExists(any(String.class))).thenReturn(false);
        when(memsql.createDatabase(any(String.class))).thenReturn(databaseName);
        */

        String dbName = "ani_test_database";



/*
        CreateServiceInstanceResponse response = service.createServiceInstance(buildCreateRequest());





        assertNotNull(response);
        assertNull(response.getDashboardUrl());
        assertFalse(response.isAsync());

        verify(repository).save(isA(ServiceInstance.class));*/
    }


}
