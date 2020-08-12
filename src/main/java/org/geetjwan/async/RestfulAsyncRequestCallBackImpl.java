package org.geetjwan.async;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestfulAsyncRequestCallBackImpl implements AsyncRequestCallBack {

	@Override
	public void process(AsyncRequest req) throws AsyncWarnException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(req.getRequestMessage(), headers);
	    restTemplate.postForLocation(req.getTargetUrl().toString(), request);
	}
}
