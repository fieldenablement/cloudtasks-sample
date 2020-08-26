package org.geetjwan.async;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AsyncRequestRowMapper implements RowMapper<AsyncRequest>{

	public static final String TABLE_NAME = "TASK_QUEUE";
	public static final String ID = "id";
	public static final String ATTEMPTS = "ATTEMPTS";
	public static final String CREATE_TIMESTAMP = "CREATE_TIMESTAMP";
	public static final String CREATE_USER = "CREATE_USER";
	public static final String REFERENCE_ID = "REFERENCE_ID";
	public static final String REFERENCE_TYPE = "REFERENCE_TYPE";
	public static final String OPERATION_NAME = "OPERATION_NAME";
	public static final String SYNC_ID = "SYNC_ID";
	public static final String PROCESS_MESSAGE = "PROCESS_MESSAGE";
	public static final String REQUEST_MESSAGE = "REQUEST_MESSAGE";
	public static final String SOURCE = "SOURCE";
	public static final String STATUS = "STATUS";
	public static final String TARGET = "TARGET";
	public static final String TARGET_URL = "TARGET_URL";
	public static final String TRANSACTION_ID = "TRANSACTION_ID";
	public static final String UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";
	
	
	
	@Override
	public AsyncRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		AsyncRequest asyncRequest = new AsyncRequest();
		 
		asyncRequest.setId(rs.getInt(ID));
		asyncRequest.setAttempts(rs.getInt(ATTEMPTS));
		asyncRequest.setCreateTimeStamp(rs.getDate(CREATE_TIMESTAMP));
		asyncRequest.setCreateUser(rs.getString(CREATE_USER));
		asyncRequest.setReferenceId(rs.getString(REFERENCE_ID));
		asyncRequest.setReferenceType(rs.getString(REFERENCE_TYPE));
		asyncRequest.setOperationName(rs.getString(OPERATION_NAME));
		asyncRequest.setProcessMessage(rs.getString(PROCESS_MESSAGE));
		asyncRequest.setRequestMessage(rs.getString(REQUEST_MESSAGE));
		asyncRequest.setSource(rs.getString(SOURCE));
		asyncRequest.setStatus(rs.getString(STATUS));
		asyncRequest.setTarget(rs.getString(TARGET));
		asyncRequest.setTargetUrl(rs.getString(TARGET_URL));
		asyncRequest.setSyncId(rs.getString(SYNC_ID));
		asyncRequest.setTransactionId(rs.getString(TRANSACTION_ID));
		asyncRequest.setUpdateTimestamp(rs.getDate(UPDATE_TIMESTAMP));
		
		return asyncRequest;
	}

}
