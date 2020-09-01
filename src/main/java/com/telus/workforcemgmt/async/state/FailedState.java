package com.telus.workforcemgmt.async.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telus.workforcemgmt.async.AsyncConfigurable;
import com.telus.workforcemgmt.async.AsyncRequest;
import com.telus.workforcemgmt.async.AsyncRequestDecorator;

public class FailedState implements AsyncState {

	Logger logger = LoggerFactory.getLogger(FailedState.class);

	AsyncConfigurable config;
    
    public FailedState(AsyncConfigurable configurable) {
    	config = configurable;
	}


	public boolean process(StateContext context, AsyncRequestDecorator req) {
	AsyncRequest request = req.getRequest();
	if (request.getAttempts() > config.getMaxRetryForFailed()) {
		logger.debug("Status is changed to WAIT :" + req.getRequest().getId());
	    request.setStatus(ASYNCSTSATE.WAIT.toString());
	    req.setRequest(request);
	    context.getPostProcessor().retry(req, config.getDelayForResendingWait());
	    return false;
	} else {
	    return true;
	}
    }

    public boolean isCapableOf(AsyncRequest request) {
	return (ASYNCSTSATE.FAILED.toString().equals(request.getStatus()));
    }
    

}

