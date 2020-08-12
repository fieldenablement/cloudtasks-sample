package org.geetjwan.async.state;

import org.geetjwan.async.AsyncConfigurable;
import org.geetjwan.async.AsyncRequest;
import org.geetjwan.async.AsyncRequestDecorator;
import org.geetjwan.async.CloudTaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
