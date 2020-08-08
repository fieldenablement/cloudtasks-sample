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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.tasks.v2.Task;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CloudTaskHandler {

	Logger logger = LoggerFactory.getLogger(CloudTaskHandler.class);
	
	
	private static final String URL = "http://34.125.14.42/taskHandler";
	private static final String PROJECT_ID ="level-amphora-275105";
	private static final String LOCATION_ID ="us-west4";
	private static final String QUEUE_ID ="test";
	
	@PostMapping(value = "/taskHandler")
	public ResponseEntity<?> handler(UriComponentsBuilder builder, @RequestBody TaskRequest req)  {
	
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = "/task")
	public ResponseEntity<?> createTask(UriComponentsBuilder builder, @RequestBody TaskRequest req)  {
		 ObjectMapper mapper = new ObjectMapper();
	      String jsonString;
		try {
			jsonString = mapper.writeValueAsString(req);
			Task task = CloudTasksUtils.createTask(PROJECT_ID, LOCATION_ID, QUEUE_ID, jsonString, URL, 60);
		} catch (IOException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/task/{id}")
	  public TaskRequest one(@PathVariable Long id) {
		logger.info("entering task id");
	    return new TaskRequest("test version 2: " + id);
	  }
}
