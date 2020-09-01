package com.telus.workforcemgmt.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RestfulAsyncRequestCallBackImpl implements AsyncRequestCallBack {

	Logger logger = LoggerFactory.getLogger(RestfulAsyncRequestCallBackImpl.class);
	
	@Override
	public void process(AsyncRequest request) throws AsyncWarnException {
		logger.info("Processed: " + request.toString());
		if ("SUCCESS".equals(request.getOperationName())) {
			logger.info("SUCCESS");
		} else {
			logger.error("FAILED");
			throw new RuntimeException("ResfulAsyncREquestCallBack failed");
		}
		/*
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(req.getRequestMessage(), headers);
	    restTemplate.postForLocation(req.getTargetUrl().toString(), request);
	    */
	}
}
