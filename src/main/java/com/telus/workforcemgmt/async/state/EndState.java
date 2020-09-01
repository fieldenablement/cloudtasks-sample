package com.telus.workforcemgmt.async.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.telus.workforcemgmt.async.AsyncConfigurable;
import com.telus.workforcemgmt.async.AsyncRequest;
import com.telus.workforcemgmt.async.AsyncRequestDecorator;
import com.telus.workforcemgmt.async.CloudTaskHandler;

public class EndState implements AsyncState {

	Logger logger = LoggerFactory.getLogger(CloudTaskHandler.class);

    AsyncConfigurable config;
    
    public EndState(AsyncConfigurable configurable) {
    	config = configurable;
	}

	public boolean process(StateContext context, AsyncRequestDecorator request) {
		logger.debug("END state: " + request.getRequest().getId());
	return false;
    }

    public boolean isCapableOf(AsyncRequest request) {
    	return (ASYNCSTSATE.COMPLETED.toString().equals(request.getStatus()))
		|| (ASYNCSTSATE.WARN.toString().equals(request.getStatus()))
		|| (ASYNCSTSATE.STOP.toString().equals(request.getStatus()))
		|| (ASYNCSTSATE.ABORTED.toString().equals(request.getStatus()));
    }


}
