package org.springframework.cloud.servicebroker.memsql.exception;

import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;


/**
 * Exception thrown when issues with the underlying mongo service occur.
 * NOTE: com.memsql.MongoException is a runtime exception and therefore we
 * want to have to handle the issue.
 *
 */
public class MemSQLServiceException extends ServiceBrokerException {

	private static final long serialVersionUID = 8667141725171626000L;

	public MemSQLServiceException(String message) {
		super(message);
	}
	
}
