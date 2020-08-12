package org.geetjwan.async;

//import com.telus.framework.exception.BaseRuntimeException;

/**
 * Thrown when a JobNotification is unable to be sent to the host system.
 * 
 * @author t814752
 * 
 */
//public class AsyncRequestProcessException extends BaseRuntimeException
public class AsyncRequestProcessException extends RuntimeException
{

    public AsyncRequestProcessException(String message) {
	super(message);
    }

    public AsyncRequestProcessException(String message, Throwable cause) {
	super(message, cause);
    }

    public AsyncRequestProcessException(Throwable cause) {
	super(cause);
    }

}
