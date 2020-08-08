package com.telus.workforcemgmt.poc.demo;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.tasks.v2.Task;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class CloudTaskHandler {

	Logger logger = LoggerFactory.getLogger(CloudTaskHandler.class);
	
	private static final String URL = "http://34.125.225.87/taskHandler";
	private static final String PROJECT_ID ="level-amphora-275105";
	private static final String LOCATION_ID ="us-west4";
	private static final String QUEUE_ID ="test";
	
	@PostMapping(value = "/taskHandler")
	public ResponseEntity<?> handler(UriComponentsBuilder builder, @RequestBody TaskRequest req)  {
		logger.info("entering taskHandler: " + req.toString());  
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = "/createTask")
	public ResponseEntity<?> createTask(UriComponentsBuilder builder, @RequestBody TaskRequest req)  {
		logger.info("entering createTask:"  + req.toString());  
		try {
			ObjectMapper mapper = new ObjectMapper();
		    String jsonString = mapper.writeValueAsString(req);
			Task task = CloudTasksUtils.createTask(PROJECT_ID, LOCATION_ID, QUEUE_ID, jsonString, URL, 60);
			return new ResponseEntity(task, HttpStatus.ACCEPTED);
		} catch (IOException e) {
			logger.error("Error ", e); 
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/task/{id}")
	  public TaskRequest one(@PathVariable Long id) {
		logger.info("entering task id");
		TaskRequest task = new TaskRequest();
		task.setContent("version 5 " + id);
		createTask(null, task);
	    return task;
	  }
}
