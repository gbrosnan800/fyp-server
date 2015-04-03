package com.gbrosnan.fyp.objects;

public class StatusMessage {
	
	private String status;
	
	private StatusMessage() {}
	
	private StatusMessage(String status) {
		setStatus(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
