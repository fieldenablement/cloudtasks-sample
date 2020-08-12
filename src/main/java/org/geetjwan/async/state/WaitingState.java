package org.geetjwan.async.state;

import java.util.Calendar;
import java.util.Date;

import org.geetjwan.async.AsyncConfigurable;
import org.geetjwan.async.AsyncRequest;
import org.geetjwan.async.AsyncRequestDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WaitingState implements AsyncState {

	Logger logger = LoggerFactory.getLogger(WaitingState.class);

	AsyncConfigurable config;
    
    public WaitingState(AsyncConfigurable configurable) {
    	config = configurable;
	}

	public boolean process(StateContext context, AsyncRequestDecorator req) {
	AsyncRequest request = req.getRequest();
	if (isMorethanMaxTime(request.getCreateTimeStamp()) > config.getMaxWaitingTime()) {
		logger.debug("Status is changed to ABORTED: " + req.getRequest().getId());
	    request.setStatus(ASYNCSTSATE.ABORTED.toString());
	    context.getPostProcessor().save(request);
	    return false;
	} else {
		logger.debug("Status is WAITING: " + req.getRequest().getId());
	    context.getPostProcessor().retry(req, config.getDelayForResendingWait());
	    return false;
	}
    }

    private long isMorethanMaxTime(Date timestamp) {
	return Calendar.getInstance().getTime().getTime() - timestamp.getTime();
    }

    public boolean isCapableOf(AsyncRequest request) {
	return (ASYNCSTSATE.WAIT.toString().equals(request.getStatus()) || ASYNCSTSATE.BLOCKED.toString().equals(
		request.getStatus()));
    }
}
