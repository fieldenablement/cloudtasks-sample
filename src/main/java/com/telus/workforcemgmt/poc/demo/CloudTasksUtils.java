package com.telus.workforcemgmt.poc.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Clock;
import java.time.Instant;

import com.google.cloud.tasks.v2.CloudTasksClient;
import com.google.cloud.tasks.v2.HttpMethod;
import com.google.cloud.tasks.v2.QueueName;
import com.google.cloud.tasks.v2.Task;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.google.cloud.tasks.v2.HttpRequest;

public class CloudTasksUtils {

	public static Task createTask(String projectId, 
			String locationId, 
			String queueId, String payload, 
			String targetUrl, 
			int delayPeriodInSeconds)
			throws IOException {

		// Instantiates a client.
		try (CloudTasksClient client = CloudTasksClient.create()) {
			// Construct the fully qualified queue name.
			String queuePath = QueueName.of(projectId, locationId, queueId).toString();

			// Construct the task body.
			Task.Builder taskBuilder =
					Task.newBuilder()
					.setHttpRequest(
							HttpRequest.newBuilder()
							.setBody(ByteString.copyFrom(payload, Charset.defaultCharset()))
							.setUrl(targetUrl)
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
