package org.cf.cloud.servicebroker.memsql.exception;

import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;


/**
 * Exception thrown when issues with the underlying memsql service occur.
 *
 */
public class MemSQLServiceException extends ServiceBrokerException {

	private static final long serialVersionUID = 8667141725171626000L;

	public MemSQLServiceException(String message) {
		super(message);
	}
	
}
