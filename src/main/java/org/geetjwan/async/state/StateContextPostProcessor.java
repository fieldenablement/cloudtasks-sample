package org.geetjwan.async.state;

import java.io.PrintWriter;
import java.io.StringWriter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geetjwan.async.AsyncConfigurable;
import org.geetjwan.async.AsyncRequest;
import org.geetjwan.async.AsyncRequestDao;
import org.geetjwan.async.AsyncRequestDecorator;
import org.geetjwan.async.AsyncRequestSender;
import org.geetjwan.async.AsyncWarnException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static org.geetjwan.async.state.AsyncState.*;


@Component
public class StateContextPostProcessor {

    private static Log log = LogFactory.getLog(StateContextPostProcessor.class);
    @Autowired
    private AsyncConfigurable configurable;
    @Autowired
    private AsyncRequestSender redelivery;
	@Autowired
	private AsyncRequestDao dao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleFailuerResponse(AsyncRequestDecorator req, Throwable t) {
        AsyncRequest request = req.getRequest();
        log.debug(" Failed in processing: reference Id = '" + request.getReferenceId()
                + "'");
        StringBuffer err = new StringBuffer(t.getClass().getSimpleName()
                + " : ");
        if (t.getMessage() != null) {
            err.append(t.getMessage());
        }
        log.error(err.toString());
        String errorTrace = getErrorStackTrace(t);
        log.error(errorTrace);
        request.setAttempts(request.getAttempts() + 1);
        request.setStatus(ASYNCSTSATE.FAILED.toString());
        request.setProcessMessage(errorTrace);
        log.debug(" Failed in processing, resend to queue : referenceId = '"
                + request.getReferenceId() + "'");
        redelivery.send(req.getDestination(), request, configurable.getDelayForResendingFailed());
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleCompletedResponse(AsyncRequestDecorator req) {
        AsyncRequest request = req.getRequest();
        request.setStatus(ASYNCSTSATE.COMPLETED.toString());
        log.debug(" Completed processing : referenceId = '"
                + request.getReferenceId() + "'");
        save(request);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleWarnResponse(AsyncRequestDecorator req,
                                   AsyncWarnException ce) {
        AsyncRequest request = req.getRequest();
        log.warn(ce.getMessage());
        request.setStatus(ASYNCSTSATE.WARN.toString());
        request.setProcessMessage(ce.getMessage());
        log.debug(" Completed with WARN : referenceId = '" + request.getReferenceId()
                + "'");
        save(request);
    }

    public void save(AsyncRequest request) {
        try {
            log.info("saving request in StateContextPostProcessor");
            dao.update(request);
            log.debug(" saved asyncRequest : referenceId = '"
                    + request.getReferenceId() + "'");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(getErrorStackTrace(e));
        }
    }

    public void retry(AsyncRequestDecorator request, int delayTime) {
        redelivery.send(request.getDestination(), request.getRequest(), delayTime);
    }


    public String getErrorStackTrace(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        StringBuffer error = stringWriter.getBuffer();
        return error.toString();
    }

}
