package com.telus.workforcemgmt.poc.demo;

public class CloudTaskDestination  {

	private String projectId;
	private String locationId; 
	private String queueId; 
	private String targetUrl;
	
	public CloudTaskDestination(String projectId, String locationId, String queueId, String targetUrl) {
		super();
		this.projectId = projectId;
		this.locationId = locationId;
		this.queueId = queueId;
		this.targetUrl = targetUrl;
	}
	
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
