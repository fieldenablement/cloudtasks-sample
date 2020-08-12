package org.geetjwan.async;


public interface AsyncRequestCallBack {
	
	void process(AsyncRequest req) throws AsyncWarnException;

}
