package com.telus.workforcemgmt.async;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Clock;
import java.time.Instant;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.google.cloud.tasks.v2.CloudTasksClient;
import com.google.cloud.tasks.v2.HttpMethod;
import com.google.cloud.tasks.v2.QueueName;
import com.google.cloud.tasks.v2.Task;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.google.cloud.tasks.v2.HttpRequest;
import com.google.cloud.tasks.v2.OidcToken;

public class CloudTasksUtils {

	public static Task createTask(CloudTaskDestination dest, String payload, int delayperiod) throws IOException {
		return createTask(dest.getProjectId(), dest.getLocationId(), 
				dest.getQueueId(), payload, dest.getTargetUrl(), delayperiod, dest.getServiceAccountEmail());
	}
	
	public static Task createTask(String projectId, 
			String locationId, 
			String queueId, String payload, 
			String targetUrl, 
			int delayPeriodInSeconds, 
			String serviceAccountEmail)
			throws IOException {

		// Instantiates a client.
		try (CloudTasksClient client = CloudTasksClient.create()) {
			// Construct the fully qualified queue name.
			String queuePath = QueueName.of(projectId, locationId, queueId).toString();

			OidcToken.Builder oidcTokenBuilder =
			          OidcToken.newBuilder().setServiceAccountEmail(serviceAccountEmail);
			// Construct the task body.
			Task.Builder taskBuilder =
					Task.newBuilder()
					.setHttpRequest(
							HttpRequest.newBuilder()
							.putHeaders(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
							.setBody(ByteString.copyFrom(payload, Charset.defaultCharset()))
							.setUrl(targetUrl)
							//.setOidcToken(oidcTokenBuilder)
							.setHttpMethod(HttpMethod.POST)
							.build()).setScheduleTime(Timestamp.newBuilder()
									.setSeconds(Instant.now(Clock.systemUTC())
											.plusSeconds(delayPeriodInSeconds)
											.getEpochSecond()));
			// Send create task request.
			return client.createTask(queuePath, taskBuilder.build());
		}
	}
	
}
