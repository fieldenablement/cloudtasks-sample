package org.geetjwan.async.state;

import org.geetjwan.async.AsyncRequest;
import org.geetjwan.async.AsyncRequestDecorator;

public interface AsyncState {

    public enum ASYNCSTSATE {
	NEW, ONHOLD, FAILED, WAIT, BLOCKED, ABORTED, COMPLETED, WARN, STOP
    }

    public boolean process(StateContext context, AsyncRequestDecorator request);

    public boolean isCapableOf(AsyncRequest request);

}
