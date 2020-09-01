package com.telus.workforcemgmt.async.state;

import com.telus.workforcemgmt.async.AsyncRequest;
import com.telus.workforcemgmt.async.AsyncRequestDecorator;

public interface AsyncState {

    public enum ASYNCSTSATE {
	NEW, ONHOLD, FAILED, WAIT, BLOCKED, ABORTED, COMPLETED, WARN, STOP
    }

    public boolean process(StateContext context, AsyncRequestDecorator request);

    public boolean isCapableOf(AsyncRequest request);

}
