package org.geetjwan.async;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AsyncRequestDao {

	private static final String SELECT_BY_ID = "SELECT * FROM TASK_QUEUE WHERE ID = ?";
	private static final String UPDATE_WITHOUT_PROCESSMESSAGE = "UPDATE TASK_QUEUE SET ATTEMPTS=:attempts, SET STATUS=:status where ID=:id";
	private static final String UPDATE_WITH_PROCESSMESSAGE = "UPDATE TASK_QUEUE SET ATTEMPTS=:attempts, SET STATUS=:status, set PROCESS_MESSAGE=:processMessage where ID=:id";
	private static final String QUERY_PENDING = "SELECT ID, ATTEMPTS, SOURCE, TARGET, TARGET_URL, REFERENCE_ID, REFERENCE_TYPE, SYNC_ID, STATUS, OPERATION_NAME, TRANSACTION_ID FROM TASK_QUEUE WHERE SYNC_ID = :synId AND STATUS in ('NEW', 'ONHOLD', 'FAILED', 'WAIT', BLOCKED')";

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	

	@Autowired
	public void setDataSource(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(AsyncRequestRowMapper.TABLE_NAME).usingGeneratedKeyColumns(AsyncRequestRowMapper.ID);
	}
	
	public AsyncRequest getAsyncRequest(final int id) {
		return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[] { id }, new AsyncRequestRowMapper());
	}

	public int save(final AsyncRequest req) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AsyncRequestRowMapper.ATTEMPTS, req.getAttempts());
		parameters.put(AsyncRequestRowMapper.CREATE_TIMESTAMP, new Date());
		parameters.put(AsyncRequestRowMapper.CREATE_USER, req.getCreateUser());
		parameters.put(AsyncRequestRowMapper.REFERENCE_ID, req.getReferenceId());
		parameters.put(AsyncRequestRowMapper.REFERENCE_TYPE, req.getReferenceType());
		parameters.put(AsyncRequestRowMapper.OPERATION_NAME, req.getOperationName());
		parameters.put(AsyncRequestRowMapper.REQUEST_MESSAGE, req.getRequestMessage());
		parameters.put(AsyncRequestRowMapper.SOURCE, req.getSource());
		parameters.put(AsyncRequestRowMapper.STATUS, req.getStatus());
		parameters.put(AsyncRequestRowMapper.TARGET, req.getTarget());
		parameters.put(AsyncRequestRowMapper.TARGET_URL, req.getTargetUrl());
		parameters.put(AsyncRequestRowMapper.TRANSACTION_ID, req.getTransactionId());
		parameters.put(AsyncRequestRowMapper.SYNC_ID, req.getSyncId());
		return simpleJdbcInsert.executeAndReturnKey(parameters).intValue();
	}


	public void update(final AsyncRequest req) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(req);
		if (req.getProcessMessage() == null) {
			namedParameterJdbcTemplate.update(UPDATE_WITHOUT_PROCESSMESSAGE, namedParameters); 
		} else {
			namedParameterJdbcTemplate.update(UPDATE_WITH_PROCESSMESSAGE, namedParameters);
		}
	}
	
	public List<AsyncRequest> getNotCompletedAndNotStoppedRequests(AsyncRequest request) {
		SqlParameterSource parameters = new MapSqlParameterSource("synId", request.getSyncId());
		return namedParameterJdbcTemplate.query(QUERY_PENDING, parameters, 
				(rs, rowNum) -> new AsyncRequest(
						rs.getInt(AsyncRequestRowMapper.ID), 
						rs.getInt(AsyncRequestRowMapper.ATTEMPTS),
						rs.getString(AsyncRequestRowMapper.SOURCE),
						rs.getString(AsyncRequestRowMapper.TARGET), 
						rs.getURL(AsyncRequestRowMapper.TARGET_URL),
						rs.getString(AsyncRequestRowMapper.REFERENCE_ID),
						rs.getString(AsyncRequestRowMapper.REFERENCE_TYPE),
						rs.getString(AsyncRequestRowMapper.SYNC_ID),
						rs.getString(AsyncRequestRowMapper.STATUS),
						rs.getString(AsyncRequestRowMapper.OPERATION_NAME),
						rs.getString(AsyncRequestRowMapper.TRANSACTION_ID))
				);
	}
}
