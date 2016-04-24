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
	//protected Map<String,String> credentialsMap = new HashMap<>();

	protected Map<String,String> credentials = new HashMap<String,String>();

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
		return convertToObjectMap(this.credentials);
	}

	private static Map<String, Object> convertToObjectMap(Map<String, String> srcMap) {
		HashMap<String, Object> targetMap = new HashMap<String, Object>();
		for(String key: srcMap.keySet()) {
			String val = srcMap.get(key);
			Object nativeVal = val;
			if (val == null) {
				continue;
			}

			try {
				nativeVal = Double.valueOf(val);
				Double double1 = (Double)nativeVal;
				if (double1.doubleValue() == double1.intValue()) {
					nativeVal = new Integer(double1.intValue());
				}
			} catch(NumberFormatException ipe) {
				String lowerVal = val.trim().toLowerCase();
				if (lowerVal.equals("true") || lowerVal.equals("false")) {
					nativeVal = Boolean.valueOf(val);
				}
			}
			targetMap.put(key, nativeVal);
		}
		return targetMap;
	}


	private synchronized void setCredentials(Map credentials) {
		if (credentials == null) {
			this.credentials = new HashMap<String, String>();
		} else {
			for(Object key: credentials.keySet()) {
				this.credentials.put("" + key, "" + credentials.get(key));
			}
		}
	}


	public String getSyslogDrainUrl() {
		return syslogDrainUrl;
	}

	public String getAppGuid() {
		return appGuid;
	}

}