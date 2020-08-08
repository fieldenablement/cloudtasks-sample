package com.telus.workforcemgmt.poc.demo;

import lombok.Data;

@Data
public class TaskRequest {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "TaskRequest [content=" + content + "]";
	}
	
	
}
