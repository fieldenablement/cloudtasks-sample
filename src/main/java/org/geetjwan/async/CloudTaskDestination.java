package org.geetjwan.async;

import org.springframework.stereotype.Component;

@Component
public class CloudTaskDestination  {


	private static final String URL = "http://34.125.225.87/taskHandler";
	private static final String PROJECT_ID ="level-amphora-275105";
	private static final String LOCATION_ID ="us-west4";
	private static final String QUEUE_ID ="test";
	
	private String projectId = PROJECT_ID;
	private String locationId = LOCATION_ID; 
	private String queueId = QUEUE_ID; 
	private String targetUrl = URL;
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
}
