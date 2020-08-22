package org.geetjwan.async;

import java.net.URL;
import java.util.Date;


public class AsyncRequest {

	private Integer id;

	private int attempts;

	private String source;

	private String target;
	
	private URL targetUrl;

	private String referenceId;

	private String referenceType;

	private String syncId;

	private String status;
	
	private String requestMessage;

	private String processMessage;

	private Date updateTimestamp;

	private Date createTimeStamp;

	private String createUser;

	private String operationName;

	private String transactionId;
	
	public AsyncRequest() {}
	
	public AsyncRequest(Integer id, int attempts, String source, String target, URL targetUrl, String referenceId,
			String referenceType, String syncId, String status, String operationName, String transactionId, Date createTimeStamp) {
		super();
		this.id = id;
		this.attempts = attempts;
		this.source = source;
		this.target = target;
		this.targetUrl = targetUrl;
		this.referenceId = referenceId;
		this.referenceType = referenceType;
		this.syncId = syncId;
		this.status = status;
		this.operationName = operationName;
		this.transactionId = transactionId;
		this.createTimeStamp = createTimeStamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public URL getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(URL targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getProcessMessage() {
		return processMessage;
	}

	public void setProcessMessage(String processMessage) {
		this.processMessage = processMessage;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSyncId() {
		return syncId;
	}

	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}

	@Override
	public String toString() {
		return "AsyncRequest [id=" + id + ", attempts=" + attempts + ", source=" + source + ", target=" + target
				+ ", targetUrl=" + targetUrl + ", referenceId=" + referenceId + ", referenceType=" + referenceType
				+ ", syncId=" + syncId + ", status=" + status + ", requestMessage=" + requestMessage
				+ ", processMessage=" + processMessage + ", updateTimestamp=" + updateTimestamp + ", createTimeStamp="
				+ createTimeStamp + ", createUser=" + createUser + ", operationName=" + operationName
				+ ", transactionId=" + transactionId + "]";
	}

	
}
