package com.telus.workforcemgmt.poc.demo;


import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private static final String URL = "";
	private static final String PROJECT_ID ="techhub-ui-2df643b4";
	private static final String LOCATION_ID ="northamerica-northeast1";
	private static final String QUEUE_ID ="wfm-click";
	
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
}
