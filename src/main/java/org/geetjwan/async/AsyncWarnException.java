package org.geetjwan.async;

/**
 * Thrown when a JobNotification is unable to be sent to the host system. 
 * @author t814752
 *
 */
public class AsyncWarnException extends Exception {

	public AsyncWarnException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AsyncWarnException(String message) {
		super(message);
	}
}
