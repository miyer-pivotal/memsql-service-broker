package org.springframework.cloud.servicebroker.memsql.service;

import org.cf.cloud.servicebroker.memsql.exception.MemSQLServiceException;
import org.cf.cloud.servicebroker.memsql.lib.PasswordGenerator;
import org.cf.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceBindingRepository;
import org.cf.cloud.servicebroker.memsql.repository.MemSQLServiceInstanceRepository;
import org.cf.cloud.servicebroker.memsql.service.MemSQLAdminService;
import org.cf.cloud.servicebroker.memsql.service.MemSQLClient;
import org.cf.cloud.servicebroker.memsql.service.MemSQLServiceInstanceService;
import org.cf.cloud.servicebroker.memsql.model.ServiceInstance;
import org.cf.cloud.servicebroker.memsql.model.ServiceInstanceBinding;
import org.cf.cloud.servicebroker.memsql.service.MemSQLServiceInstanceBindingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.ServiceBindingResource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.cf.cloud.servicebroker.memsql.fixture.ServiceInstanceFixture;
import org.cf.cloud.servicebroker.memsql.fixture.ServiceInstanceBindingFixture;
/**
 * Created by miyer on 4/21/16.
 */
public class MemSQLServiceInstanceBindingServiceUnitTest {
    private static final String DB_NAME = "ani_test_database";
    private static final String MEMSQL_USER_NAME = "anirudhajadhav";
    PasswordGenerator pgen = new PasswordGenerator();

    public final String MEMSQL_PASSWORD = pgen.generateRandomString();


    @Autowired
    private MemSQLClient client = new MemSQLClient("jdbc:mysql://52.87.206.146:3306", "root", "pivotal");

    @Mock
    private MemSQLAdminService memsql = new MemSQLAdminService(client);

    @Mock
    private MemSQLServiceInstanceBindingRepository repository;


    private MemSQLServiceInstanceBindingService service;

    private ServiceInstance instance;
    private ServiceInstanceBinding instanceBinding;

    @Before
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        service = new MemSQLServiceInstanceBindingService(memsql,repository);
        memsql.createDatabase(DB_NAME);
        memsql.createUser(DB_NAME,MEMSQL_USER_NAME,MEMSQL_PASSWORD);
        instance = ServiceInstanceFixture.getServiceInstance();
        instanceBinding = ServiceInstanceBindingFixture.getServiceInstanceBinding();
        System.out.println(instanceBinding.getAppGuid() + " **app guid");
    }

    @After
    public void cleanup() throws SQLException {
        try {
            memsql.deleteDatabase(DB_NAME);
        } catch (MemSQLServiceException ignore) {}
    }



    @Test
    public void createServiceInstanceBindingResponseTest() throws Exception {



        when(repository.findOne(any(String.class))).thenReturn(null);

        CreateServiceInstanceAppBindingResponse response =
                (CreateServiceInstanceAppBindingResponse) service.createServiceInstanceBinding(buildCreateRequest());

        assertNotNull(response);
        assertNotNull(response.getCredentials());
        assertNull(response.getSyslogDrainUrl());

        verify(repository).save(isA(ServiceInstanceBinding.class));

    }
//
//    @Test(expected = ServiceInstanceBindingExistsException.class)
//    public void serviceInstanceCreationFailsWithExistingInstance() throws Exception {
//
//        when(repository.findOne(any(String.class)))
//                .thenReturn(ServiceInstanceBindingFixture.getServiceInstanceBinding());
//
//        service.createServiceInstanceBinding(buildCreateRequest());
//    }
//
//    @Test(expected = ServiceBrokerException.class)
//    public void serviceInstanceBindingCreationFailsWithUserCreationFailure() throws Exception {
//        when(repository.findOne(any(String.class))).thenReturn(null);
//
//
//        doThrow(new MemSQLServiceException("fail")).when(memsql).createUser(any(String.class), any(String.class), any(String.class));
//
//        service.createServiceInstanceBinding(buildCreateRequest());
//    }
//
//    @Test
//    public void successfullyRetrieveServiceInstanceBinding() {
//        ServiceInstanceBinding binding = ServiceInstanceBindingFixture.getServiceInstanceBinding();
//        when(repository.findOne(any(String.class))).thenReturn(binding);
//
//        assertEquals(binding.getId(), service.getServiceInstanceBinding(binding.getId()).getId());
//    }
//
//    @Test
//    public void serviceInstanceBindingDeletedSuccessfully() throws Exception {
//        ServiceInstanceBinding binding = ServiceInstanceBindingFixture.getServiceInstanceBinding();
//        when(repository.findOne(any(String.class))).thenReturn(binding);
//
//        service.deleteServiceInstanceBinding(buildDeleteRequest());
//
//        verify(memsql).deleteUser(binding.getServiceInstanceId(), binding.getId());
//        verify(repository).delete(binding.getId());
//    }
//
//    @Test(expected = ServiceInstanceBindingDoesNotExistException.class)
//    public void unknownServiceInstanceDeleteCallSuccessful() throws Exception {
//        ServiceInstanceBinding binding = ServiceInstanceBindingFixture.getServiceInstanceBinding();
//
//        when(repository.findOne(any(String.class))).thenReturn(null);
//
//        service.deleteServiceInstanceBinding(buildDeleteRequest());
//
//        verify(memsql, never()).deleteUser(binding.getServiceInstanceId(), binding.getId());
//        verify(repository, never()).delete(binding.getId());
//    }

    private CreateServiceInstanceBindingRequest buildCreateRequest() {
        Map<String, Object> bindResource =
                Collections.singletonMap(ServiceBindingResource.BIND_RESOURCE_KEY_APP.toString(), (Object) "app_guid");
        return new CreateServiceInstanceBindingRequest(instance.getServiceDefinitionId(), instance.getPlanId(),
                "app_guid", bindResource)
                .withServiceInstanceId(instance.getServiceInstanceId())
                .withBindingId(instanceBinding.getId());
    }

    private DeleteServiceInstanceBindingRequest buildDeleteRequest() {
        return new DeleteServiceInstanceBindingRequest(instance.getServiceInstanceId(), instanceBinding.getId(),
                instance.getServiceDefinitionId(), instance.getPlanId(), null);
    }

}


