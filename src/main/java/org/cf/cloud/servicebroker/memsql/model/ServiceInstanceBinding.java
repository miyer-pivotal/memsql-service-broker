package org.cf.cloud.servicebroker.memsql.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;

/**
 * A binding to a service instance
 */
@Entity
@Table(name = "service_instance_binding")
public class ServiceInstanceBinding {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	private String serviceInstanceId;
	private String syslogDrainUrl;
	private String appGuid;

	@ElementCollection(fetch = FetchType.LAZY)
	@MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="binding_creds_attributes", joinColumns=@JoinColumn(name="binding_creds_attrib_id"))
	protected Map<String,Object> credentials = new HashMap<>();

	public ServiceInstanceBinding(String id,
								  String serviceInstanceId,
								  Map<String,Object> credentials,
								  String syslogDrainUrl, String appGuid) {
		this.id = id;
		this.serviceInstanceId = serviceInstanceId;
		setCredentials(credentials);
		this.syslogDrainUrl = syslogDrainUrl;
		this.appGuid = appGuid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public void setSyslogDrainUrl(String syslogDrainUrl) {
		this.syslogDrainUrl = syslogDrainUrl;
	}

	public void setAppGuid(String appGuid) {
		this.appGuid = appGuid;
	}

	public String getId() {
		return id;
	}

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public Map<String, Object> getCredentials() {
		return credentials;
	}

	private void setCredentials(Map<String, Object> credentials) {
		if (credentials == null) {
			this.credentials = new HashMap<>();
		} else {
			this.credentials = credentials;
		}
	}

	public String getSyslogDrainUrl() {
		return syslogDrainUrl;
	}

	public String getAppGuid() {
		return appGuid;
	}

}