package com.telus.workforcemgmt.async;


public interface AsyncRequestCallBack {
	
	void process(AsyncRequest req) throws AsyncWarnException;

}
