package com.telus.workforcemgmt.async;



public class AsyncRequestDecorator {

    private AsyncRequest request;
    private CloudTaskDestination destination;

    public AsyncRequest getRequest() {
	return request;
    }

    public void setRequest(AsyncRequest request) {
	this.request = request;
    }

	public CloudTaskDestination getDestination() {
		return destination;
	}

	public void setDestination(CloudTaskDestination destination) {
		this.destination = destination;
	}

  
}
