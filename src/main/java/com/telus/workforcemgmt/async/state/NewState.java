package com.telus.workforcemgmt.async.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telus.workforcemgmt.async.AsyncConfigurable;
import com.telus.workforcemgmt.async.AsyncRequest;
import com.telus.workforcemgmt.async.AsyncRequestDecorator;


public class NewState implements AsyncState {


	Logger logger = LoggerFactory.getLogger(NewState.class);

	AsyncConfigurable config;
    
    
    public NewState(AsyncConfigurable configurable) {
    	config = configurable;
	}

	public boolean process(StateContext context, AsyncRequestDecorator req) {
	
	AsyncRequest request = req.getRequest();
	int count = context.getPrecedingMessageCount(request);
	if (count == 0) {
		logger.debug("Processing request immediately: " + request.getId());
	    return true;
	} else {
	    request.setAttempts(request.getAttempts() + 1);
	    logger.debug("Status is changed to ONHOLD: " + request.getId());
	    request.setStatus(ASYNCSTSATE.ONHOLD.toString());
	    req.setRequest(request);
	    context.getPostProcessor().retry(req, config.getDelayForResendingOnHeld());
	    return false;
	}
    }

    public boolean isCapableOf(AsyncRequest request) {
	return (ASYNCSTSATE.NEW.toString().equals(request.getStatus()));
    }
}
