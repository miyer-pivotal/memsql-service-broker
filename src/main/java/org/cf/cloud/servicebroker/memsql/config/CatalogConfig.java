package org.cf.cloud.servicebroker.memsql.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfig {
	
	@Bean
	public Catalog catalog() {
		return new Catalog(Collections.singletonList(
				new ServiceDefinition(
						"memsql-service-broker",
						"MemSQLDB",
						"A MemSQL service broker implementation",
						true,
						true,
						Collections.singletonList(
								new Plan("memsql-plan",
										"Default MemSQL Plan",
										"This is a default memsql plan.  All services are created equally.",
										getPlanMetadata(),true)),
						Arrays.asList("memsql", "document"),
						getServiceDefinitionMetadata(),
						null,
						null)));
	}
	
/* Used by Pivotal CF console */

	private Map<String, Object> getServiceDefinitionMetadata() {
		Map<String, Object> sdMetadata = new HashMap<>();
		sdMetadata.put("displayName", "MemSQL");
		sdMetadata.put("imageUrl", "https://upload.wikimedia.org/wikipedia/commons/4/43/MemSQL_Logo.png");
		sdMetadata.put("longDescription", "MemSQL Service");
		sdMetadata.put("providerDisplayName", "Pivotal");
		sdMetadata.put("documentationUrl", "https://github.com/spring-cloud-samples/cloudfoundry-memsql-service-broker");
		sdMetadata.put("supportUrl", "https://github.com/spring-cloud-samples/cloudfoundry-memsql-service-broker");
		return sdMetadata;
	}
	
	private Map<String,Object> getPlanMetadata() {
		Map<String,Object> planMetadata = new HashMap<>();
		planMetadata.put("costs", getCosts());
		planMetadata.put("bullets", getBullets());
		return planMetadata;
	}

	private List<Map<String,Object>> getCosts() {
		Map<String,Object> costsMap = new HashMap<>();
		
		Map<String,Object> amount = new HashMap<>();
		amount.put("usd", 0.0);
	
		costsMap.put("amount", amount);
		costsMap.put("unit", "MONTHLY");
		
		return Collections.singletonList(costsMap);
	}
	
	private List<String> getBullets() {
		return Arrays.asList("Shared MemSQL server",
				"100 MB Storage (not enforced)", 
				"40 concurrent connections (not enforced)");
	}
	
}