package org.geetjwan.async;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.geetjwan.async.state.StateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class CloudTaskHandler {

	Logger logger = LoggerFactory.getLogger(CloudTaskHandler.class);

	@Autowired
	private AsyncRequestDao asyncRequestDao;
	@Autowired
	private AsyncRequestSender asyncRequestSender;
	@Autowired
	private CloudTaskDestination cloudTaskDestination;
	@Autowired
	private StateContext stateContext;
	@Autowired
	private AsyncRequestCallBack callBack;

	@PostMapping(value = "/taskHandler")
	public ResponseEntity<?> handler(UriComponentsBuilder builder, @RequestBody AsyncRequest request)  {
		logger.info("entering taskHandler: " + request.toString());  
		if (request.getId() == null) {
			logger.error("Request ID is empty");
		} else {
			stateContext.process(cloudTaskDestination, request, callBack);
		} 
		HttpHeaders headers = new HttpHeaders();
		UriComponents uriComponents = builder.path("/task/{id}").buildAndExpand(request.getId());
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/createTask")
	public ResponseEntity<?> createTask(UriComponentsBuilder builder, @RequestBody AsyncRequest request)  {
		logger.info("entering createTask:"  + request.toString()); 
		try {
			int id = asyncRequestSender.send(cloudTaskDestination, request, 0);
			HttpHeaders headers = new HttpHeaders();
			UriComponents uriComponents = builder.path("/task/{id}").buildAndExpand(id);
			headers.setLocation(uriComponents.toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.error("Error ", e); 
			MultiValueMap<String, String> headers =  new LinkedMultiValueMap<>();
			headers.add("service exception", e.getMessage());
			return new ResponseEntity<>(ExceptionUtils.getStackTrace(e), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/task/{id}")
	public AsyncRequest one(@PathVariable Integer id) {
		logger.info("entering task id");
		return asyncRequestDao.getAsyncRequest(id);
	}
}
