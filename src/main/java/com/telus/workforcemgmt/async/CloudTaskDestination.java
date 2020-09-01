package com.telus.workforcemgmt.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CloudTaskDestination  {

	@Autowired
	private Environment env;
	
	private static final String URL = "http://34.125.225.87/taskHandler";
	private static final String PROJECT_ID ="level-amphora-275105";
	private static final String LOCATION_ID ="us-west4";
	private static final String QUEUE_ID ="test";
	
	private String projectId = PROJECT_ID;
	private String locationId = LOCATION_ID; 
	private String queueId = QUEUE_ID; 
	private String targetUrl = URL;
	private String serviceAccountEmail;
	
	
	public String getProjectId() {
		return env.getProperty("SPRING_CLOUD_GCP_LOGGING_PROJECT_ID");
		//return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getLocationId() {
		return env.getProperty("LOCATION_ID");
		//return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getQueueId() {
		return env.getProperty("QUEUE_ID");
		//return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	public String getTargetUrl() {
		return env.getProperty("TARGET_URL");
		//return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public String getServiceAccountEmail() {
		return env.getProperty("SERVICE_ACCOUNT_EMAIL");
	}
	public void setServiceAccountEmail(String serviceAccountEmail) {
		this.serviceAccountEmail = serviceAccountEmail;
	}
	
	
}
