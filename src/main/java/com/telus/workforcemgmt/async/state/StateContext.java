package com.telus.workforcemgmt.async.state;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.telus.workforcemgmt.async.AsyncConfigurable;
import com.telus.workforcemgmt.async.AsyncRequest;
import com.telus.workforcemgmt.async.AsyncRequestCallBack;
import com.telus.workforcemgmt.async.AsyncRequestDao;
import com.telus.workforcemgmt.async.AsyncRequestDecorator;
import com.telus.workforcemgmt.async.AsyncWarnException;
import com.telus.workforcemgmt.async.CloudTaskDestination;

@Component
public class StateContext {

	private static Log log = LogFactory.getLog(StateContext.class);

	private List<AsyncState> asyncStates;

	@Autowired
	private AsyncRequestDao dao;
	@Autowired
	private StateContextPostProcessor postProcessor;
	@Autowired
	private AsyncConfigurable configurable;

	@Transactional(propagation = Propagation.REQUIRED)
	public void process(CloudTaskDestination queue, AsyncRequest request, AsyncRequestCallBack callBack) {
		log.info("in StateContext.process() #2");
		int msgId = request.getId();
		request = dao.getAsyncRequest(msgId);
		if (request == null) {
			log.debug("Not found in database : Message Id '" + msgId + "'");
			return;
		} else {
			log.debug("found in database : reference id '" + request.getReferenceId() + "'");
		}

		AsyncRequestDecorator dec = new AsyncRequestDecorator();
		dec.setRequest(request);
		dec.setDestination(queue);

		if (isExcutable(dec)) {
			try {
				log.info("in StateContext executing callBack.process");
				callBack.process(dec.getRequest());
				postProcessor.handleCompletedResponse(dec);
			} catch (AsyncWarnException ce) {
				postProcessor.handleWarnResponse(dec, ce);
			} catch (Exception t) {
				postProcessor.handleFailuerResponse(dec, t);
			}
		}
	}

	public boolean isExcutable(AsyncRequestDecorator req) {
		for (AsyncState s : getAsyncStates()) {
			if (s.isCapableOf(req.getRequest())) {
				return s.process(this, req);
			}
		}
		log.error("No State is found from request: " + req.getRequest().getId());
		return false;
	}

	public int getPrecedingMessageCount(AsyncRequest request) {
		List<AsyncRequest> requests = dao.getNotCompletedAndNotStoppedRequests(request);
		if (log.isDebugEnabled()) {
			int i = (requests == null) ? 0 : requests.size();
			log.debug("found " + i + " existing message with same referenceId = '" + request.getReferenceId() + "'");
		}

		Date timestamp = request.getCreateTimeStamp();

		if (requests == null || requests.size() == 0)
			return 0;

		int count = 0;
		for (AsyncRequest req : requests) {
			if (timestamp == null || req.getCreateTimeStamp().before(timestamp)) {
				log.debug("Found a message created earlier with same referenceId = '" + request.getReferenceId() + "'");
				count++;
			}
		}
		return count;
	}

	public void setAsyncStates(List<AsyncState> asyncStates) {
		this.asyncStates = asyncStates;
	}

	private List<AsyncState> getAsyncStates() {
		if (asyncStates == null) {
			asyncStates = new LinkedList<AsyncState>();
			asyncStates.add(new WaitingState(configurable));
			asyncStates.add(new NewState(configurable));
			asyncStates.add(new OnHoldState(configurable));
			asyncStates.add(new EndState(configurable));
			asyncStates.add(new FailedState(configurable));
		}
		return asyncStates;
	}

	public StateContextPostProcessor getPostProcessor() {
		return postProcessor;
	}

}
