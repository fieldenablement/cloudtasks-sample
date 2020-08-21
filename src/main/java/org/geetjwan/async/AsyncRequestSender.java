package org.geetjwan.async;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AsyncRequestSender {

	@Autowired
	private AsyncRequestDao dao;
	
	@Transactional
	public int send(CloudTaskDestination destination, AsyncRequest request, int delayTime) {
		try {
			int id = dao.save(request);
			request.setId(id);
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(request);
			CloudTasksUtils.createTask(destination, jsonString, delayTime);
			return id;
		} catch (IOException e) {
			throw new AsyncRequestProcessException(e);
		}
	}
}
